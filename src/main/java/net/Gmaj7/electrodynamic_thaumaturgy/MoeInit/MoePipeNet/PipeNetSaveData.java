package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public abstract class PipeNetSaveData<T extends PipeNet> extends SavedData {
    protected Map<Integer, T> pipeNets = new HashMap<>();
    protected int nextId = 0;

    protected PipeNetSaveData(int nextId, Map<Integer, T> pipeNets){
        this.pipeNets = new HashMap<>(pipeNets);
        this.nextId = nextId;
    }

    public PipeNetSaveData(){
    }

    public abstract T createNet();

    public PipeNet getNet(int id){
        return pipeNets.containsKey(id) ? pipeNets.get(id) : null;
    }

    public void removeNet(int id){
        pipeNets.remove(id);
        setDirty();
    }

    public int getNextId() {
        return nextId;
    }

    public Map<Integer, T> getPipeNets() {
        return pipeNets;
    }

    public int getNetIdOfPos(BlockPos pos){
        int i = -1;
        for (PipeNet pipeNet : pipeNets.values())
            if(pipeNet.containPos(pos)) {
                i = pipeNet.getNetId();
                break;
            }
        return i;
    }

    public void addPosToNet(int i, BlockPos pos, Set<BlockPos> set) {
        pipeNets.get(i).addPos(pos, set);
        setDirty();
    }

    public void linkNetsOfPos(BlockPos pos, List<Integer> netLinks, Set<BlockPos> links) {
        // 收集所有节点和邻接关系
        Set<BlockPos> allPos = new HashSet<>();
        Map<BlockPos, Set<BlockPos>> allAdj = new HashMap<>();
        Map<BlockPos, Set<Direction>> allInsert = new HashMap<>(), allExtract = new HashMap<>();

        for (int id : netLinks) {
            PipeNet net = pipeNets.get(id);
            if (net == null) continue;
            allPos.addAll(net.getPosSet());
            for (Map.Entry<BlockPos, Set<BlockPos>> entry : net.getAdj().entrySet()) {
                allAdj.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
            }
            for (Map.Entry<BlockPos, Set<Direction>> entry : net.getInsert().entrySet()) {
                allInsert.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
            }
            for (Map.Entry<BlockPos, Set<Direction>> entry : net.getExtract().entrySet()) {
                allExtract.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
            }
        }

        // 添加新管道节点
        allPos.add(pos);
        allAdj.put(pos, new HashSet<>(links));
        for (BlockPos neighbor : links) {
            allAdj.computeIfAbsent(neighbor, k -> new HashSet<>()).add(pos);
        }

        // 删除旧网络
        for (int id : netLinks) {
            pipeNets.remove(id);
        }
        T newNet = createNetWith(netLinks.get(0), allPos, allAdj, allInsert, allExtract);

        pipeNets.put(netLinks.get(0), newNet);
        setDirty();
    }

    /**
     * 处理管道破坏：从网络 netId 中删除 pos，如果导致网络分裂，则将分裂出的子图创建为新网络。
     */
    public void breakPipe(BlockPos pos) {
        int netId = getNetIdOfPos(pos);
        PipeNet net = pipeNets.get(netId);
        if (net == null) return;

        // 1. 删除该节点
        net.removePos(pos);

        // 2. 如果网络没有节点了，直接删除整个网络
        if (net.getPosSet().isEmpty()) {
            pipeNets.remove(netId);
            setDirty();
            return;
        }

        // 3. 检查当前网络是否连通：从任意一个节点出发 BFS，看能否到达所有节点
        BlockPos anyPos = net.getPosSet().iterator().next();
        Set<BlockPos> mainComponent = net.extractComponent(anyPos);

        // 如果主分量包含所有节点，则网络仍然连通，无需拆分
        if (mainComponent.size() == net.getPosSet().size()) {
            setDirty();  // 因为删除了节点，仍需标记保存
            return;
        }
        dealBreak(netId, net);
    }

    public void removeConnection(BlockPos posA, BlockPos posB) {
        int netId = getNetIdOfPos(posA);
        if (netId == -1) return;
        PipeNet net = pipeNets.get(netId);
        if (net == null) return;
        net.removeConnection(posA, posB);
        Set<BlockPos> allPos = net.getPosSet();
        BlockPos anyPos = allPos.iterator().next();
        Set<BlockPos> mainComponent = net.extractComponent(anyPos);

        // 如果主分量包含所有节点，则网络仍然连通，无需拆分
        if (mainComponent.size() == allPos.size()) {
            setDirty();
            return;
        }
        dealBreak(netId, net);
    }

    private void dealBreak(int netId, PipeNet net){
        Set<BlockPos> remaining = new HashSet<>(net.getPosSet());
        List<Set<BlockPos>> components = new ArrayList<>();
        while (!remaining.isEmpty()) {
            BlockPos start = remaining.iterator().next();
            Set<BlockPos> comp = net.extractComponent(start);
            components.add(comp);
            remaining.removeAll(comp);
        }

        Map<BlockPos, Set<Direction>> insert = net.getInsert(), extract = net.getExtract();

        // 4.2 删除原网络
        pipeNets.remove(netId);

        // 4.3 创建新网络：主分量使用原 netId，其他分量使用新 ID
        // 需要根据分量构建新的 PipeNet 实例（包含节点和邻接关系）
        for (int i = 0; i < components.size(); i++) {
            Set<BlockPos> comp = components.get(i);
            Map<BlockPos, Set<Direction>> newInsert = new HashMap<>(), newExtract = new HashMap<>();
            // 构建该分量的邻接表（只包含分量内部的边）
            Map<BlockPos, Set<BlockPos>> subAdj = new HashMap<>();
            for (BlockPos p : comp) {
                if(insert.containsKey(p)) newInsert.put(p, new HashSet<>(insert.get(p)));
                if(extract.containsKey(p)) newExtract.put(p, new HashSet<>(extract.get(p)));
                Set<BlockPos> neighbors = net.getPosNeighbors(p);
                Set<BlockPos> filtered = new HashSet<>();
                for (BlockPos nb : neighbors) {
                    if (comp.contains(nb)) {
                        filtered.add(nb);
                    }
                }
                if (!filtered.isEmpty()) {
                    subAdj.put(p, filtered);
                }
            }
            int newId = (i == 0) ? netId : nextId++;
            T newNet = createNetWith(newId, comp, subAdj, newInsert, newExtract);
            pipeNets.put(newId, newNet);
        }
        setDirty();
    }

    protected abstract T createNetWith(int newId, Set<BlockPos> comp, Map<BlockPos, Set<BlockPos>> subAdj, Map<BlockPos, Set<Direction>> newInsert, Map<BlockPos, Set<Direction>> newExtract);

    protected static String saveId(int i){
        return String.valueOf(i);
    }

    protected static int loadId(String i){
        return Integer.parseInt(i);
    }

    public void link2PosInNet(int netId, BlockPos pos, BlockPos neighborPos) {
        pipeNets.get(netId).link2Pos(pos, neighborPos);
        setDirty();
    }

    public void addInsert(ServerLevel level, BlockPos pos, Direction direction){
        int id = getNetIdOfPos(pos);
        if(id == -1) return;
        pipeNets.get(id).addInsert(level, pos, direction);
        setDirty();
    }

    public void addExtract(ServerLevel level, BlockPos pos, Direction direction){
        int id = getNetIdOfPos(pos);
        if(id == -1) return;
        pipeNets.get(id).addExtract(level, pos, direction);
        setDirty();
    }

    public void removeInsert(ServerLevel level, BlockPos pos, Direction direction){
        int id = getNetIdOfPos(pos);
        if(id == -1) return;
        pipeNets.get(id).removeInsert(level, pos, direction);
        setDirty();
    }

    public void removeExtract(ServerLevel level, BlockPos pos, Direction direction){
        int id = getNetIdOfPos(pos);
        if(id == -1) return;
        pipeNets.get(id).removeExtract(level, pos, direction);
        setDirty();
    }

    public void tick(ServerLevel level){
        for (PipeNet pipeNet : pipeNets.values())
            pipeNet.tick(level);
    }
}
