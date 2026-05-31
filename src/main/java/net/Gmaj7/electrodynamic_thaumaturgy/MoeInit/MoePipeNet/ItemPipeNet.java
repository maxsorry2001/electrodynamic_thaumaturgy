package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.*;

public class ItemPipeNet extends PipeNet{
    public static final Codec<ItemPipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(ItemPipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(ItemPipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(ItemPipeNet::getAdj),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("insert").forGetter(ItemPipeNet::getInsert),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNet::keyToPos, ItemPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("extract").forGetter(ItemPipeNet::getExtract),
            Codec.INT.fieldOf("tick_counter").forGetter(ItemPipeNet::getTickCounter)
    ).apply(i, ItemPipeNet::new));

    // 为每个连接的机器存储其对应的缓存
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> extractCaches;
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> insertCaches;
    public ItemPipeNet(int id) {
        super(id);
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
    }

    public ItemPipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Set<Direction>> extract, int tickCounter) {
        super(id, posSet, adj, insert, extract, tickCounter);
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
    }

    @Override
    public void removeExtractCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> map = extractCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) extractCaches.remove(pos);
        }
    }

    @Override
    public void removeInsertCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> map = insertCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) insertCaches.remove(pos);
        }
    }

    @Override
    public void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        extractCaches.computeIfAbsent(pipePos, k -> new HashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Item.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {}  // 能力失效时的回调
                )
        );
    }

    @Override
    public void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        insertCaches.computeIfAbsent(pipePos, k -> new HashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Item.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {} // 能力失效时的回调
                )
        );
    }

    @Override
    protected void ensureCachesInitialized(ServerLevel level) {
        if (extractCaches.isEmpty() && !extract.isEmpty()) {
            // 根据已有的 extract 映射创建缓存
            for (Map.Entry<BlockPos, Set<Direction>> entry : extract.entrySet()) {
                BlockPos pipePos = entry.getKey();
                for (Direction dir : entry.getValue()) {
                    addExtractCache(level, pipePos, dir);
                }
            }
        }
        if(insertCaches.isEmpty() && ! insert.isEmpty()){
            // 根据已有的 insert 映射创建缓存
            for (Map.Entry<BlockPos, Set<Direction>> entry : insert.entrySet()) {
                BlockPos pipePos = entry.getKey();
                for (Direction dir : entry.getValue()) {
                    addInsertCache(level, pipePos, dir);
                }
            }
        }
        checkDistance();
    }

    @Override
    protected void work() {if(insertCaches.isEmpty() || extractCaches.isEmpty()) return;
        List<ResourceHandler<ItemResource>> extractors = new ArrayList<>();
        for (var map : extractCaches.values()) {
            for (var cache : map.values()) {
                ResourceHandler<ItemResource> h = cache.getCapability();
                if (h != null) extractors.add(h);
            }
        }
        List<ResourceHandler<ItemResource>> inserters = new ArrayList<>();
        for (var map : insertCaches.values()) {
            for (var cache : map.values()) {
                ResourceHandler<ItemResource> h = cache.getCapability();
                if (h != null) inserters.add(h);
            }
        }
        if(extractors.isEmpty() || inserters.isEmpty()) return;int total = extractors.size();
        int base = total / 20, remaining = total % 20, processedBefore = tickCounter * base + Math.min(remaining, tickCounter);
        if (processedBefore >= total) return; // 本 tick 无任务
        int count = (tickCounter < remaining) ? base + 1 : base, end = Math.min(processedBefore + count, total);
        for (; processedBefore < end; processedBefore ++){

        }
    }
}
