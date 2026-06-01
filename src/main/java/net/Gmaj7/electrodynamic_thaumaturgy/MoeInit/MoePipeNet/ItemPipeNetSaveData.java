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

public class ItemPipeNetSaveData extends PipeNetSaveData<ItemPipeNet>{

    public static final Codec<ItemPipeNetSaveData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("next_id").forGetter(ItemPipeNetSaveData::getNextId),
            Codec.unboundedMap(Codec.STRING.xmap(ItemPipeNetSaveData::loadId, ItemPipeNetSaveData::saveId), ItemPipeNet.CODEC).fieldOf("nets").forGetter(ItemPipeNetSaveData::getPipeNets)
    ).apply(i, ItemPipeNetSaveData::new));

    public static final SavedDataType<ItemPipeNetSaveData> ITEM_PIPE_NETS = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item_pipe_nets"),
            () -> new ItemPipeNetSaveData(),
            CODEC
    );

    protected ItemPipeNetSaveData(int nextId, Map<Integer, ItemPipeNet> pipeNets){
        super(nextId, pipeNets);
    }

    protected ItemPipeNetSaveData(){}

    @Override
    public ItemPipeNet createNet() {
        ItemPipeNet net = new ItemPipeNet(nextId);
        nextId ++;
        pipeNets.put(net.getNetId(), net);
        setDirty();
        return net;
    }

    @Override
    protected ItemPipeNet createNetWith(int newId, Set<BlockPos> comp, Map<BlockPos, Set<BlockPos>> subAdj, Map<BlockPos, Set<Direction>> newInsert, Map<BlockPos, Set<Direction>> newExtract) {
        return new ItemPipeNet(newId, comp, subAdj, newInsert, newExtract, 0);
    }
}
