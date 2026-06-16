package net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet;

import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.PipeNetSynPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.resource.Resource;

import java.util.*;

public abstract class PipeNet implements MenuProvider {
    protected final PipeNetType netType;
    protected final int netId;
    protected Set<BlockPos> posSet;
    protected LinkedHashMap<BlockPos, Set<BlockPos>> adj;
    protected LinkedHashMap<BlockPos, Set<Direction>> insert;
    protected LinkedHashMap<BlockPos, Map<Direction, TransferMode>> extract;
    protected Map<BlockPos, LinkedHashMap<BlockPos, Integer>> distances;
    protected int tickCounter;
    protected Map<BlockPos, Integer> pollingIndexes;
    protected List<ServerPlayer> lookingPlayer;

    public PipeNet(int id, PipeNetType netType){
        this.netId = id;
        this.netType = netType;
        this.posSet = new LinkedHashSet<>();
        this.adj = new LinkedHashMap<>();
        this.insert = new LinkedHashMap<>();
        this.extract = new LinkedHashMap<>();
        this.distances = new HashMap<>();
        this.tickCounter = 0;
        this.pollingIndexes = new HashMap<>();
        this.lookingPlayer = new ArrayList<>();
    }

    public PipeNet(int id, Set<BlockPos> posSet, Map<BlockPos, Set<BlockPos>> adj, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, TransferMode>> extract, int tickCounter, PipeNetType netType){
        this.netId = id;
        this.netType = netType;
        this.posSet = new HashSet<>(posSet);
        this.adj = new LinkedHashMap<>();
        this.insert = new LinkedHashMap<>();
        this.extract = new LinkedHashMap<>();
        this.distances = new HashMap<>();
        this.pollingIndexes = new HashMap<>();
        this.tickCounter = tickCounter;
        for (Map.Entry<BlockPos, Set<BlockPos>> entry : adj.entrySet()) {
            this.adj.put(entry.getKey(), new LinkedHashSet<>(entry.getValue()));
        }
        for (Map.Entry<BlockPos, Set<Direction>> entry : insert.entrySet()) {
            this.insert.put(entry.getKey(), new LinkedHashSet<>(entry.getValue()));
        }
        for (Map.Entry<BlockPos, Map<Direction, TransferMode>> entry : extract.entrySet()) {
            Map<Direction, TransferMode> map = new LinkedHashMap<>();
            map.putAll(entry.getValue());
            this.extract.put(entry.getKey(), map);
            this.pollingIndexes.put(entry.getKey(), 0);
        }
        this.lookingPlayer = new ArrayList<>();
    }

    public void addPos(BlockPos blockPos, Set<BlockPos> links){
        posSet.add(blockPos);
        LinkedHashSet<BlockPos> neighbors = new LinkedHashSet<>(links);  // 拷贝
        adj.put(blockPos, neighbors);
        for (BlockPos linkPos : links) {
            adj.computeIfAbsent(linkPos, k -> new LinkedHashSet<>()).add(blockPos);
        }
        checkChange();
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
        removePosCache(blockPos);
        checkChange();
    }

    public Set<BlockPos> getPosSet() {
        return posSet;
    }

    public Set<BlockPos> getPosNeighbors(BlockPos pos) {
        return adj.getOrDefault(pos, new LinkedHashSet<>());
    }

    public int getNetId() {
        return netId;
    }

    public Map<BlockPos, Set<BlockPos>> getAdj() {
        return adj;
    }

    public LinkedHashMap<BlockPos, Map<Direction, TransferMode>> getExtract() {
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
        checkChange();
    }

    public void link2Pos(BlockPos pos, BlockPos neighborPos) {
        adj.get(pos).add(neighborPos);
        adj.get(neighborPos).add(pos);
        checkChange();
    }

    public void addInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos) && !insert.get(pos).contains(direction)) insert.get(pos).add(direction);
        else {
            LinkedHashSet<Direction> set = new LinkedHashSet<>();
            set.add(direction);
            insert.put(pos, set);
        }
        addInsertCache(level, pos, direction);
        checkChange();
    }

    public void removeInsert(ServerLevel level, BlockPos pos, Direction direction){
        if(insert.containsKey(pos)){
            if(insert.get(pos).contains(direction)) insert.get(pos).remove(direction);
            if(insert.get(pos).isEmpty()) insert.remove(pos);
        }
        removeInsertCache(pos, direction);
        checkChange();
    }

    public void addExtract(ServerLevel level, BlockPos pos, Direction direction, TransferMode transferMode){
        if(extract.containsKey(pos)) extract.get(pos).put(direction, transferMode);
        else {
            LinkedHashMap<Direction, TransferMode> map = new LinkedHashMap<>();
            map.put(direction, transferMode);
            extract.put(pos, map);
        }
        addExtractCache(level, pos, direction);
        checkChange();
    }

    public void addExtract(ServerLevel level, BlockPos pos, Direction direction){
        this.addExtract(level, pos, direction, TransferMode.NEAREST);
    }

    public void removeExtract(ServerLevel level, BlockPos pos, Direction direction){
        if(extract.containsKey(pos)){
            if(extract.get(pos).containsKey(direction)) extract.get(pos).remove(direction);
            if(extract.get(pos).isEmpty()) extract.remove(pos);
        }
        removeExtractCache(pos, direction);
        checkChange();
    }

    protected LinkedHashMap<BlockPos, Integer> bfsDistances(BlockPos startPos){
        LinkedHashMap<BlockPos, Integer> dis = new LinkedHashMap<>(), ioDis = new LinkedHashMap<>();
        Deque<BlockPos> que = new ArrayDeque<>();
        dis.put(startPos, 0);
        que.add(startPos);
        if(insert.containsKey(startPos))
            ioDis.put(startPos, 0);
        while (!que.isEmpty()){
            BlockPos cur = que.poll();
            int curDis = dis.get(cur);
            for (BlockPos neighbor : adj.getOrDefault(cur, new LinkedHashSet<>())){
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

    protected void checkChange(){
        if(insert.isEmpty() || extract.isEmpty()){
            distances.clear();
            return;
        }
        pollingIndexes.clear();
        for (BlockPos start : extract.keySet()) {
            distances.put(start, bfsDistances(start));
            pollingIndexes.put(start, 0);
        }
        for (ServerPlayer serverPlayer : lookingPlayer)
            PacketDistributor.sendToPlayer(serverPlayer, new PipeNetSynPacket(netId, netType, insert, extract));
    }

    protected abstract void removeInsertCache(BlockPos pos, Direction direction);

    protected abstract void removeExtractCache(BlockPos pos, Direction direction);

    public abstract void addExtractCache(ServerLevel level, BlockPos pipePos, Direction pipeSide);

    public abstract void addInsertCache(ServerLevel level, BlockPos pipePos, Direction pipeSide);

    public abstract void removePosCache(BlockPos blockPos);

    public void tick(ServerLevel level) {
        ensureCachesInitialized(level);
        work();
        tickCounter = (tickCounter + 1) % 20;
    }

    protected abstract void work();

    protected abstract void ensureCachesInitialized(ServerLevel level) ;

    protected BlockPos getNearestInsert(BlockPos extractPos, int order){
        if(!extract.containsKey(extractPos) || !distances.containsKey(extractPos)) return null;
        Map<BlockPos, Integer> dis = distances.get(extractPos);
        BlockPos blockPos = extractPos;
        for (Map.Entry<BlockPos, Integer> entry : dis.entrySet()){
            order --;
            if(order <= 0) {
                blockPos = entry.getKey();
                break;
            }
        }
        return blockPos;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(netId);
        buffer.writeMap(extract, (buf, pos) -> buf.writeBlockPos(pos),
                (buf, map) -> buf.writeMap(map,
                    (b, dir) -> b.writeEnum(dir),
                    (b, mode) -> b.writeEnum(mode)));
        buffer.writeMap(insert,  (buf, pos) -> buf.writeBlockPos(pos),
                (buf, set) -> {
                    buf.writeVarInt(set.size());
                    for (Direction dir : set) buf.writeEnum(dir);
                });
    }

    public void loopTransferMod(BlockPos pos, Direction direction) {
        TransferMode transferMode = extract.get(pos).get(direction).next();
        extract.get(pos).put(direction, transferMode);
        for (ServerPlayer serverPlayer : lookingPlayer)
            PacketDistributor.sendToPlayer(serverPlayer, new PipeNetSynPacket(netId, netType, insert, extract));
    }

    public List<ServerPlayer> getLookingPlayer() {
        return lookingPlayer;
    }

    public void addLookingPlayer(ServerPlayer player){
        if (!lookingPlayer.contains(player))lookingPlayer.add(player);
    }

    public void removeLookingPlayer(ServerPlayer player){
        if(lookingPlayer.contains(player)) lookingPlayer.remove(player);
    }

    public PipeNetType getNetType() {
        return netType;
    }

    protected static class ResourceExtractSet<T extends Resource>{
        protected BlockPos pos;
        protected Direction direction;
        protected ResourceHandler<T> resourceHandler;

        protected ResourceExtractSet(BlockPos pos, Direction direction, ResourceHandler<T> resourceHandler){
            this.pos = pos;
            this.direction = direction;
            this.resourceHandler = resourceHandler;
        }

        protected BlockPos getPos() {
            return pos;
        }

        protected ResourceHandler<T> getResourceHandler() {
            return resourceHandler;
        }

        public Direction getDirection() {
            return direction;
        }
    }

    public enum TransferMode implements StringRepresentable {
        NEAREST("nearest"),
        FARTHEST("farthest"),
        POLLING("polling");

        public static final StringRepresentable.EnumCodec<TransferMode> CODEC = StringRepresentable.fromEnum(TransferMode::values);

        private final String name;

        TransferMode(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public TransferMode next(){
            return switch (this){
                case NEAREST -> FARTHEST;
                case FARTHEST -> POLLING;
                default -> NEAREST;
            };
        }
    }

    public enum PipeNetType implements StringRepresentable{
        ITEM("item"),
        ENERGY("energy");
        public static final StringRepresentable.EnumCodec<PipeNetType> CODEC = StringRepresentable.fromEnum(PipeNetType::values);

        private final String name;

        PipeNetType(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
