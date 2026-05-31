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
import net.neoforged.neoforge.transfer.transaction.Transaction;

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
        if(distances.isEmpty())
            checkDistance();
    }

    @Override
    protected void work() {if(insertCaches.isEmpty() || extractCaches.isEmpty()) return;
        List<PosAndResourceHandler<ItemResource>> extractors = new ArrayList<>();
        for (Map.Entry<BlockPos, Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>>> entry : extractCaches.entrySet()) {
            for (var cache : entry.getValue().values()) {
                ResourceHandler<ItemResource> h = cache.getCapability();
                if (h != null) extractors.add(new PosAndResourceHandler<>(entry.getKey(), h));
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
            ItemResource resource = ItemResource.EMPTY;
            ResourceHandler<ItemResource> insertHandler = null;
            int insertCount = 0;
            var extractSet = extractors.get(processedBefore);
            try (Transaction transaction = Transaction.openRoot()){
                ResourceHandler<ItemResource> extractHandler = extractSet.getResourceHandler();
                // 找到第一个非空槽位
                for (int i = 0; i < extractHandler.size(); i++){
                    resource = extractHandler.getResource(i);
                    if(!resource.isEmpty()) break;
                }
                if(!resource.isEmpty()){
                    // 先模拟提取（最大尝试一组，但实际提取量可能小于maxStackSize）
                    int extracted = extractHandler.extract(resource, resource.getMaxStackSize(), transaction);
                    if (extracted > 0) {
                        // 按距离顺序尝试插入
                        outer:
                        for (int i = 1; i <= distances.get(extractSet.getPos()).size(); i++){
                            BlockPos checkPos = getNearestInsert(extractSet.getPos(), i);
                            Map<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> dirMap = insertCaches.get(checkPos);
                            if (dirMap == null) continue;
                            for (Map.Entry<Direction, BlockCapabilityCache<ResourceHandler<ItemResource>, Direction>> entry : dirMap.entrySet()){
                                ResourceHandler<ItemResource> inserter = entry.getValue().getCapability();
                                if (inserter == null) continue;
                                int inserted = inserter.insert(resource, extracted, transaction);
                                if (inserted == extracted) {
                                    insertHandler = inserter;
                                    insertCount = inserted;
                                    break outer;
                                }
                            }
                        }
                    }
                }
            }
            if (resource.isEmpty() || insertCount == 0) break;
            try (Transaction transaction = Transaction.openRoot()){
                extractSet.getResourceHandler().extract(resource, insertCount, transaction);
                insertHandler.insert(resource, insertCount, transaction);
                transaction.commit();
            }
        }
    }
}
