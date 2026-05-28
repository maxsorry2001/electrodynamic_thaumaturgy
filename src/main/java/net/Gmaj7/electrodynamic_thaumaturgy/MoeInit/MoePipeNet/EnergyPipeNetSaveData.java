package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Map;
import java.util.Set;

public class EnergyPipeNetSaveData extends PipeNetSaveData<EnergyPipeNet>{

    public static final Codec<EnergyPipeNetSaveData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("next_id").forGetter(EnergyPipeNetSaveData::getNextId),
            Codec.unboundedMap(Codec.STRING.xmap(EnergyPipeNetSaveData::loadId, EnergyPipeNetSaveData::saveId), EnergyPipeNet.CODEC).fieldOf("nets").forGetter(EnergyPipeNetSaveData::getPipeNets)
    ).apply(i, EnergyPipeNetSaveData::new));

    public static final SavedDataType<EnergyPipeNetSaveData> ENERGY_PIPE_NETS = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "energy_pipe_nets"),
            () -> new EnergyPipeNetSaveData(),
            CODEC
    );

    protected EnergyPipeNetSaveData(int nextId, Map<Integer, EnergyPipeNet> pipeNets){
        super(nextId, pipeNets);
    }

    protected EnergyPipeNetSaveData(){}

    @Override
    public EnergyPipeNet createNet() {
        EnergyPipeNet net = new EnergyPipeNet(nextId);
        nextId ++;
        pipeNets.put(net.getNetId(), net);
        setDirty();
        return net;
    }

    @Override
    protected EnergyPipeNet createNetWith(int newId, Set<BlockPos> comp, Map<BlockPos, Set<BlockPos>> subAdj, Map<BlockPos, Set<Direction>> newInsert, Map<BlockPos, Set<Direction>> newExtract) {
        return new EnergyPipeNet(newId, comp, subAdj, newInsert, newExtract);
    }
}
