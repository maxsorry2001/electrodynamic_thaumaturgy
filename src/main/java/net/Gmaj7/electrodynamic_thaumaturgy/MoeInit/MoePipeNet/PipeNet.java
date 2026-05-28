package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.*;

public class PipeNet {
    private final int netId;
    private Set<BlockPos> posSet;
    private Map<BlockPos, Set<BlockPos>> adj;
    private Map<BlockPos, Set<Direction>> insert;
    private Map<BlockPos, Set<Direction>> extract;

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
    }

    public PipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Set<Direction>> extract){
        this.netId = id;
        this.posSet = new HashSet<>(posSet);
        this.adj = new HashMap<>();
        this.insert = new HashMap<>();
        this.extract = new HashMap<>();
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

    public void addInsert(BlockPos pos, Direction direction){
        if(insert.containsKey(pos) && !insert.get(pos).contains(direction)) insert.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            insert.put(pos, set);
        }
    }

    public void removeInsert(BlockPos pos, Direction direction){
        if(insert.containsKey(pos)){
            if(insert.get(pos).contains(direction)) insert.get(pos).remove(direction);
            if(insert.get(pos).isEmpty()) insert.remove(pos);
        }
    }

    public void addExtract(BlockPos pos, Direction direction){
        if(extract.containsKey(pos)) extract.get(pos).add(direction);
        else {
            Set<Direction> set = new HashSet<>();
            set.add(direction);
            extract.put(pos, set);
        }
    }

    public void removeExtract(BlockPos pos, Direction direction){
        if(extract.containsKey(pos)){
            if(extract.get(pos).contains(direction)) extract.get(pos).remove(direction);
            if(extract.get(pos).isEmpty()) extract.remove(pos);
        }
    }
}
