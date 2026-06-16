package net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.Map;
import java.util.Set;

public class FluidPipeNetSaveData extends PipeNetSaveData<FluidPipeNet>{

    public static final Codec<FluidPipeNetSaveData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("next_id").forGetter(FluidPipeNetSaveData::getNextId),
            Codec.unboundedMap(Codec.STRING.xmap(FluidPipeNetSaveData::loadId, FluidPipeNetSaveData::saveId), FluidPipeNet.CODEC).fieldOf("nets").forGetter(FluidPipeNetSaveData::getPipeNets)
    ).apply(i, FluidPipeNetSaveData::new));

    public static final SavedDataType<FluidPipeNetSaveData> FLUID_PIPE_NETS = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "fluid_pipe_nets"),
            () -> new FluidPipeNetSaveData(),
            CODEC
    );

    protected FluidPipeNetSaveData(int nextId, Map<Integer, FluidPipeNet> pipeNets){
        super(nextId, pipeNets);
    }

    protected FluidPipeNetSaveData(){}

    @Override
    public FluidPipeNet createNet() {
        FluidPipeNet net = new FluidPipeNet(nextId);
        nextId ++;
        pipeNets.put(net.getNetId(), net);
        setDirty();
        return net;
    }

    @Override
    protected FluidPipeNet createNetWith(int newId, Set<BlockPos> comp, Map<BlockPos, Set<BlockPos>> subAdj, Map<BlockPos, Set<Direction>> newInsert, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> newExtract) {
        return new FluidPipeNet(newId, comp, subAdj, newInsert, newExtract, 0);
    }

    public void addFilter(BlockPos pos, Direction direction, ItemStack fluidStack, int slot){
        ((FluidPipeNet)getNetOfPos(pos)).addFilter(pos, direction, fluidStack, slot);
        setDirty();
    }
}
