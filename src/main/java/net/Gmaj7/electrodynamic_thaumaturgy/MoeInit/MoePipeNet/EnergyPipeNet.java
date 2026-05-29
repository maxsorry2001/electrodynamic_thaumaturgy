package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.*;

public class EnergyPipeNet extends PipeNet{
    public static final Codec<EnergyPipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(EnergyPipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(EnergyPipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(EnergyPipeNet::getAdj),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("insert").forGetter(EnergyPipeNet::getInsert),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNet::keyToPos, EnergyPipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("extract").forGetter(EnergyPipeNet::getExtract)
    ).apply(i, EnergyPipeNet::new));

    // 为每个连接的机器存储其对应的缓存
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> extractCaches;
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> insertCaches;
    public EnergyPipeNet(int id) {
        super(id);
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
    }

    public EnergyPipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Set<Direction>> extract) {
        super(id, posSet, adj, insert, extract);
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
    }

    @Override
    public void work() {
        if(insertCaches.isEmpty() || extractCaches.isEmpty()) return;
        List<EnergyHandler> extractors = new ArrayList<>();
        for (var map : extractCaches.values()) {
            for (var cache : map.values()) {
                EnergyHandler h = cache.getCapability();
                if (h != null) extractors.add(h);
            }
        }
        List<EnergyHandler> inserters = new ArrayList<>();
        for (var map : insertCaches.values()) {
            for (var cache : map.values()) {
                EnergyHandler h = cache.getCapability();
                if (h != null) inserters.add(h);
            }
        }
        if (extractors.isEmpty() || inserters.isEmpty()) return;
        // 1. 模拟阶段：获取每个源的最大可抽取量、每个目标的最大可插入量
        int[] extractCap = new int[extractors.size()];
        int totalExtract = 0;
        try (Transaction simTx = Transaction.openRoot()) {
            for (int i = 0; i < extractors.size(); i++) {
                extractCap[i] = extractors.get(i).extract(2048, simTx);
                totalExtract += extractCap[i];
            }
        }

        int[] insertCap = new int[inserters.size()];
        int totalInsert = 0;
        try (Transaction simTx = Transaction.openRoot()) {
            for (int i = 0; i < inserters.size(); i++) {
                insertCap[i] = inserters.get(i).insert(2048, simTx);
                totalInsert += insertCap[i];
            }
        }

        if (totalExtract == 0 || totalInsert == 0) return;

        // 2. 计算实际传输总量（受限于总可抽和总可插）
        int transfer = Math.min(totalExtract, totalInsert);
        // 3. 计算每个插入器应该分得的能量（平均分配）
        int[] toInsert = new int[inserters.size()];
        int remaining = transfer;
        double avg = (double) remaining / inserters.size();
        // 第一轮：每个分配 min(自身容量, 平均值)
        for (int i = 0; i < inserters.size(); i++) {
            int alloc = (int) Math.min(insertCap[i], avg);
            toInsert[i] = alloc;
            remaining -= alloc;
            insertCap[i] -= alloc;
        }
        // 第二轮：将剩余能量按顺序分配给仍有容量的插入器
        for (int i = 0; i < inserters.size() && remaining > 0; i++) {
            if (insertCap[i] > 0) {
                int add = Math.min(insertCap[i], remaining);
                toInsert[i] += add;
                remaining -= add;
            }
        }

        // 4. 确定从每个抽取器要提取的能量（按顺序填充，确保总提取 = transfer）
        int[] toExtract = new int[extractors.size()];
        remaining = transfer;
        for (int i = 0; i < extractors.size() && remaining > 0; i++) {
            int take = Math.min(extractCap[i], remaining);
            toExtract[i] = take;
            remaining -= take;
        }

        // 5. 执行阶段：使用新事务实际执行抽取和插入
        try (Transaction execTx = Transaction.openRoot()) {
            // 抽取
            for (int i = 0; i < extractors.size(); i++) {
                if (toExtract[i] > 0) {
                    int extracted = extractors.get(i).extract(toExtract[i], execTx);
                    if (extracted != toExtract[i]) {
                        execTx.close();
                        return;
                    }
                }
            }
            // 插入
            for (int i = 0; i < inserters.size(); i++) {
                if (toInsert[i] > 0) {
                    int inserted = inserters.get(i).insert(toInsert[i], execTx);
                    if (inserted != toInsert[i]) {
                        execTx.close();
                        return;
                    }
                }
            }
            execTx.commit();
        }
    }

    @Override
    public void removeExtractCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = extractCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) extractCaches.remove(pos);
        }
    }

    @Override
    public void removeInsertCache(BlockPos pos, Direction direction) {
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = insertCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) insertCaches.remove(pos);
        }
    }

    public void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        extractCaches.computeIfAbsent(pipePos, k -> new HashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Energy.BLOCK,
                        level,
                        machinePos,
                        machineSide,
                        () -> this.posSet.contains(pipePos), // 当管道节点还在网络中时，缓存有效
                        () -> {}  // 能力失效时的回调
                )
        );
    }

    public void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();
        insertCaches.computeIfAbsent(pipePos, k -> new HashMap<>()).put(pipeSide,
                BlockCapabilityCache.create(
                        Capabilities.Energy.BLOCK,
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
    }
}
