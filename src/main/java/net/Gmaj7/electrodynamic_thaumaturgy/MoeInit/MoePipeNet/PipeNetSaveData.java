package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;

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

    private static String saveId(int i){
        return String.valueOf(i);
    }

    private static int loadId(String i){
        return Integer.parseInt(i);
    }
}
