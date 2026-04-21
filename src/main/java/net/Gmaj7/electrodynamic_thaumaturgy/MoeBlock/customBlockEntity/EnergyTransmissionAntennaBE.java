package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import com.mojang.serialization.Codec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.EnergyTransmissionAtennaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EnergyTransmissionAntennaBE extends BlockEntity {
    private static final int maxSend = 65535;
    private List<BlockPos> receivePos = new ArrayList<>();
    private Codec<List<BlockPos>> codec = Codec.list(BlockPos.CODEC);

    public EnergyTransmissionAntennaBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_TRANSMISSION_ANTENNA_BE.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, EnergyTransmissionAntennaBE energyTransmissionAntennaBE){
        EnergyHandler energyStorage = energyTransmissionAntennaBE.getLinkStorage();
        if(energyStorage != null){
            if(state.getValue(EnergyTransmissionAtennaBlock.SEND) && !energyTransmissionAntennaBE.getReceivePos().isEmpty()){
                try (Transaction transaction = Transaction.openRoot()){
                    Iterator<BlockPos> iterator = energyTransmissionAntennaBE.getReceivePos().iterator();
                    boolean commit = true;
                    while (iterator.hasNext()) {
                        BlockPos target = iterator.next();
                        if (target == energyTransmissionAntennaBE.getBlockPos()) continue;
                        BlockEntity blockEntity = level.getBlockEntity(target);
                        if (blockEntity instanceof EnergyTransmissionAntennaBE && !level.getBlockState(target).getValue(EnergyTransmissionAtennaBlock.SEND)) {
                            EnergyHandler targetStorage = ((EnergyTransmissionAntennaBE) blockEntity).getLinkStorage();
                            if (targetStorage != null) {
                                if (energyStorage.getAmountAsInt() > 0) {
                                    int trans = Math.min(Math.min(energyStorage.getAmountAsInt(), targetStorage.getCapacityAsInt() - targetStorage.getAmountAsInt()), maxSend);
                                    if(trans <= 0) break;
                                    int insert = targetStorage.insert(trans, transaction);
                                    if(insert > 0) {
                                        int extract = energyStorage.extract(insert, transaction);
                                        if(insert != extract) {
                                            commit = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        } else iterator.remove();
                    }
                    if(commit) transaction.commit();
                }
            }
        }
    }

    public EnergyHandler getLinkStorage(){
        BlockPos blockPos = null;
        Direction direction = this.getBlockState().getValue(BlockStateProperties.FACING);
        switch (direction){
            case UP -> blockPos = getBlockPos().below();
            case DOWN -> blockPos = getBlockPos().above();
            case EAST -> blockPos = getBlockPos().west();
            case WEST -> blockPos = getBlockPos().east();
            case NORTH -> blockPos = getBlockPos().south();
            case SOUTH -> blockPos = getBlockPos().north();
        }
        return level.getCapability(Capabilities.Energy.BLOCK, blockPos, direction);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("receive_pos", codec, receivePos);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        receivePos = input.read("receive_pos", codec).orElse(new ArrayList<>());
    }

    public List<BlockPos> getReceivePos() {
        return this.receivePos;
    }
}
