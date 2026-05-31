package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;

import java.util.*;

public abstract class PipeNet {
    protected final int netId;
    protected Set<BlockPos> posSet;
    protected Map<BlockPos, Set<BlockPos>> adj;
    protected Map<BlockPos, Set<Direction>> insert;
    protected Map<BlockPos, Set<Direction>> extract;
    protected Map<BlockPos, Map<BlockPos, Integer>> distances;
    protected int tickCounter;

    public PipeNet(int id){
        this.netId = id;
        this.posSet = new HashSet<>();
        this.adj = new HashMap<>();
        this.insert = new HashMap<>();
        this.extract = new HashMap<>();
        this.distances = new HashMap<>();
        this.tickCounter = 0;
    }

    public PipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Set<Direction>> extract, int tickCounter){
        this.netId = id;
        this.posSet = new HashSet<>(posSet);
        this.adj = new HashMap<>();
        this.insert = new HashMap<>();
        this.extract = new HashMap<>();
        this.distances = new HashMap<>();
        this.tickCounter = tickCounter;
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
        checkDistance();
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
        checkDistance();
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

    public int getTickCounter() {
        return tickCounter;
    }

    public Map<BlockPos, Set<Direction>> getInsert() {
        return insert;
    }

    protected static String posToKey(BlockPos pos){
        return pos.getX() + "_" + pos.getY() + "_" + pos.getZ();
    }

    protected static BlockPos keyToPos(String key){
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
        checkDistance();
    }

    public void link2Pos(BlockPos pos, BlockPos neighborPos) {
        adj.get(pos).add(neighborPos);
        adj.get(neighborPos).add(pos);
        checkDistance();
    }

    public void addInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos) && !insert.get(pos).contains(direction)) insert.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            insert.put(pos, set);
        }
        addInsertCache(level, pos, direction);
        checkDistance();
    }

    public void removeInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos)){
            if(insert.get(pos).contains(direction)) insert.get(pos).remove(direction);
            if(insert.get(pos).isEmpty()) insert.remove(pos);
        }
        removeInsertCache(pos, direction);
        checkDistance();
    }

    public void addExtract(ServerLevel level, BlockPos pos, Direction direction){
        if(extract.containsKey(pos)) extract.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            extract.put(pos, set);
        }
        addExtractCache(level, pos, direction);
        checkDistance();
    }

    public void removeExtract(ServerLevel level, BlockPos pos, Direction direction){
        if(extract.containsKey(pos)){
            if(extract.get(pos).contains(direction)) extract.get(pos).remove(direction);
            if(extract.get(pos).isEmpty()) extract.remove(pos);
        }
        removeExtractCache(pos, direction);
        checkDistance();
    }

    protected Map<BlockPos, Integer> bfsDistances(BlockPos startPos){
        Map<BlockPos, Integer> dis = new HashMap<>(), ioDis = new HashMap<>();
        Deque<BlockPos> que = new ArrayDeque<>();
        dis.put(startPos, 0);
        que.add(startPos);
        if(insert.containsKey(startPos))
            ioDis.put(startPos, 0);
        while (!que.isEmpty()){
            BlockPos cur = que.poll();
            int curDis = dis.get(cur);
            for (BlockPos neighbor : adj.getOrDefault(cur, Collections.emptySet())){
                if(!dis.containsKey(neighbor)){
                    dis.put(neighbor, curDis + 1);
                    que.add(neighbor);
                    if(insert.containsKey(neighbor))
                        ioDis.put(neighbor, curDis + 1);
                }
            }
        }
        return ioDis;
    }

    protected void checkDistance(){
        if(insert.isEmpty() || extract.isEmpty()){
            distances.clear();
            return;
        }
        for (BlockPos start : extract.keySet())
            distances.put(start, bfsDistances(start));
    }

    protected abstract void removeInsertCache(BlockPos pos, Direction direction);

    protected abstract void removeExtractCache(BlockPos pos, Direction direction);

    public abstract void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) ;

    public abstract void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide) ;

    public void tick(ServerLevel level) {
        ensureCachesInitialized(level);
        work();
        tickCounter = (tickCounter + 1) % 20;
    }

    protected abstract void work();

    protected abstract void ensureCachesInitialized(ServerLevel level) ;

    protected BlockPos getNearestInsert(BlockPos extractPos){
        if(!extract.containsKey(extractPos) || !distances.containsKey(extractPos)) return null;
        Map<BlockPos, Integer> dis = distances.get(extractPos);
        BlockPos blockPos = extractPos;
        int nearest = -1;
        for (Map.Entry<BlockPos, Integer> entry : dis.entrySet()){
            if(nearest == -1){
                nearest = entry.getValue();
                blockPos = entry.getKey();
            }
            else if(entry.getValue() < nearest){
                nearest = entry.getValue();
                blockPos = entry.getKey();
            }
        }
        return blockPos;
    }
}
