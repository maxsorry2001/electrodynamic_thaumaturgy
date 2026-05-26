package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.*;

public class PipeNetSaveData extends SavedData {
    private Map<Integer, PipeNet> pipeNets = new HashMap<>();
    private int nextId = 0;

    public static final Codec<PipeNetSaveData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("next_id").forGetter(PipeNetSaveData::getNextId),
            Codec.unboundedMap(Codec.STRING.xmap(PipeNetSaveData::loadId, PipeNetSaveData::saveId), PipeNet.CODEC).fieldOf("nets").forGetter(PipeNetSaveData::getPipeNets)
    ).apply(i, PipeNetSaveData::new));

    public static final SavedDataType<PipeNetSaveData> PIPE_NETS = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "pipe_nets"),
            () -> new PipeNetSaveData(),
            CODEC
    );

    private PipeNetSaveData(int nextId, Map<Integer, PipeNet> pipeNets){
        this.pipeNets = pipeNets;
        this.nextId = nextId;
    }

    public PipeNetSaveData(){
    }

    public PipeNet createNet(){
        PipeNet net = new PipeNet(nextId);
        nextId ++;
        pipeNets.put(net.getNetId(), net);
        setDirty();
        return net;
    }

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

    public Map<Integer, PipeNet> getPipeNets() {
        return pipeNets;
    }

    public int getNetOfPos(BlockPos pos){
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

        for (int id : netLinks) {
            PipeNet net = pipeNets.get(id);
            if (net == null) continue;
            allPos.addAll(net.getPosSet());
            for (Map.Entry<BlockPos, Set<BlockPos>> entry : net.getAdj().entrySet()) {
                allAdj.computeIfAbsent(entry.getKey(), k -> new HashSet<>()).addAll(entry.getValue());
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
        PipeNet newNet = new PipeNet(netLinks.get(0), allPos, allAdj);


        pipeNets.put(netLinks.get(0), newNet);
        setDirty();
    }

    private static String saveId(int i){
        return String.valueOf(i);
    }

    private static int loadId(String i){
        return Integer.parseInt(i);
    }
}
