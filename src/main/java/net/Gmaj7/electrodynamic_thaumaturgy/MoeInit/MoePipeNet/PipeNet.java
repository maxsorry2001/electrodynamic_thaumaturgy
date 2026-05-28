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

public class PipeNet {
    private final int netId;
    private Set<BlockPos> posSet;
    private Map<BlockPos, Set<BlockPos>> adj;
    private Map<BlockPos, Set<Direction>> insert;
    private Map<BlockPos, Set<Direction>> extract;

    // 为每个连接的机器存储其对应的缓存
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> extractCaches;
    private Map<BlockPos, Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>>> insertCaches;

    public static final Codec<PipeNet> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("net_id").forGetter(PipeNet::getNetId),
            BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new).fieldOf("poses").forGetter(PipeNet::getPosSet),
            Codec.unboundedMap(Codec.STRING.xmap(PipeNet::keyToPos, PipeNet::posToKey), BlockPos.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("adj").forGetter(PipeNet::getAdj),
            Codec.unboundedMap(Codec.STRING.xmap(PipeNet::keyToPos, PipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("insert").forGetter(PipeNet::getInsert),
            Codec.unboundedMap(Codec.STRING.xmap(PipeNet::keyToPos, PipeNet::posToKey), Direction.CODEC.listOf().xmap(Set::copyOf, ArrayList::new)).fieldOf("extract").forGetter(PipeNet::getExtract)
    ).apply(i, PipeNet::new));

    public PipeNet(int id){
        this.netId = id;
        this.posSet = new HashSet<>();
        this.adj = new HashMap<>();
        this.insert = new HashMap<>();
        this.extract = new HashMap<>();
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
    }

    public PipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Set<Direction>> extract){
        this.netId = id;
        this.posSet = new HashSet<>(posSet);
        this.adj = new HashMap<>();
        this.insert = new HashMap<>();
        this.extract = new HashMap<>();
        this.insertCaches = new HashMap<>();
        this.extractCaches = new HashMap<>();
        for (Map.Entry<BlockPos, Set<BlockPos>> entry : adj.entrySet()) {
            this.adj.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        for (Map.Entry<BlockPos, Set<Direction>> entry : insert.entrySet()) {
            this.insert.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        for (Map.Entry<BlockPos, Set<Direction>> entry : extract.entrySet()) {
            this.extract.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
    }

    public void addPos(BlockPos blockPos, Set<BlockPos> links){
        posSet.add(blockPos);
        Set<BlockPos> neighbors = new HashSet<>(links);  // 拷贝
        adj.put(blockPos, neighbors);
        for (BlockPos linkPos : links) {
            adj.computeIfAbsent(linkPos, k -> new HashSet<>()).add(blockPos);
        }
    }

    public void removePos(BlockPos blockPos){
        posSet.remove(blockPos);
        Set<BlockPos> removedNeighbors = adj.remove(blockPos);
        if (removedNeighbors != null) {
            for (BlockPos neighbor : removedNeighbors) {
                Set<BlockPos> neighborSet = adj.get(neighbor);
                if (neighborSet != null) {
                    neighborSet.remove(blockPos);
                }
            }
        }
        if(insert.containsKey(blockPos)) insert.remove(blockPos);
        if(extract.containsKey(blockPos)) extract.remove(blockPos);
    }

    public Set<BlockPos> getPosSet() {
        return posSet;
    }

    public Set<BlockPos> getPosNeighbors(BlockPos pos) {
        return adj.getOrDefault(pos, Collections.emptySet());
    }

    public int getNetId() {
        return netId;
    }

    public Map<BlockPos, Set<BlockPos>> getAdj() {
        return adj;
    }

    public Map<BlockPos, Set<Direction>> getExtract() {
        return extract;
    }

    public Map<BlockPos, Set<Direction>> getInsert() {
        return insert;
    }

    private static String posToKey(BlockPos pos){
        return pos.getX() + "_" + pos.getY() + "_" + pos.getZ();
    }

    private static BlockPos keyToPos(String key){
        String[] parts = key.split("_");
        return new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public boolean containPos(BlockPos pos){
        return posSet.contains(pos);
    }

    /**
     * 从当前网络中提取出一个连通分量（从 start 开始 BFS）
     * 返回该分量包含的所有节点（Set<BlockPos>）
     */
    public Set<BlockPos> extractComponent(BlockPos start) {
        if (!posSet.contains(start)) return Collections.emptySet();
        Set<BlockPos> component = new HashSet<>();
        Deque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        component.add(start);
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            for (BlockPos neighbor : getPosNeighbors(current)) {
                if (component.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return component;
    }

    public void removeConnection(BlockPos posA, BlockPos posB) {
        if (adj.containsKey(posA)) adj.get(posA).remove(posB);
        if (adj.containsKey(posB)) adj.get(posB).remove(posA);
    }

    public void link2Pos(BlockPos pos, BlockPos neighborPos) {
        adj.get(pos).add(neighborPos);
        adj.get(neighborPos).add(pos);
    }

    public void addInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos) && !insert.get(pos).contains(direction)) insert.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            insert.put(pos, set);
        }
        addInsertCache(level, pos, direction);
    }

    public void removeInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos)){
            if(insert.get(pos).contains(direction)) insert.get(pos).remove(direction);
            if(insert.get(pos).isEmpty()) insert.remove(pos);
        }
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = insertCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) insertCaches.remove(pos);
        }
    }

    public void addExtract(ServerLevel level, BlockPos pos, Direction direction){
        if(extract.containsKey(pos)) extract.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            extract.put(pos, set);
        }
        addExtractCache(level, pos, direction);
    }

    public void removeExtract(ServerLevel level, BlockPos pos, Direction direction){
        if(extract.containsKey(pos)){
            if(extract.get(pos).contains(direction)) extract.get(pos).remove(direction);
            if(extract.get(pos).isEmpty()) extract.remove(pos);
        }
        Map<Direction, BlockCapabilityCache<EnergyHandler, Direction>> map = extractCaches.get(pos);
        if (map != null) {
            map.remove(direction);
            if (map.isEmpty()) extractCaches.remove(pos);
        }
    }

    public void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) {
        BlockPos machinePos = pipePos.relative(pipeSide);
        Direction machineSide = pipeSide.getOpposite();

        // 缓存会保存在 extractCaches 中
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

        // 缓存会保存在 extractCaches 中
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

    public void tick(ServerLevel level) {
        ensureCachesInitialized(level);// 收集所有抽取器、插入器
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
        } // simTx 结束，所有模拟操作被撤销

        int[] insertCap = new int[inserters.size()];
        int totalInsert = 0;
        try (Transaction simTx = Transaction.openRoot()) {
            for (int i = 0; i < inserters.size(); i++) {
                insertCap[i] = inserters.get(i).insert(2048, simTx);
                totalInsert += insertCap[i];
            }
        } // 同样回滚

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

    private void ensureCachesInitialized(ServerLevel level) {
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
