package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EnergyTransmissionAntennaBE extends BlockEntity {
    private static final int maxSend = 65535;
    private List<BlockPos> receivePos = new ArrayList<>();

    public EnergyTransmissionAntennaBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_TRANSMISSION_ANTENNA_BE.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, EnergyTransmissionAntennaBE energyTransmissionAntennaBE){
        IEnergyStorage energyStorage = energyTransmissionAntennaBE.getLinkStorage();
        if(energyStorage != null){
            if(state.getValue(EnergyTransmissionAtennaBlock.SEND) && !energyTransmissionAntennaBE.getReceivePos().isEmpty()){
                Iterator<BlockPos> iterator = energyTransmissionAntennaBE.getReceivePos().iterator();
                while (iterator.hasNext()){
                    BlockPos target = iterator.next();
                    BlockEntity blockEntity = level.getBlockEntity(target);
                    if(blockEntity instanceof EnergyTransmissionAntennaBE && !level.getBlockState(target).getValue(EnergyTransmissionAtennaBlock.SEND)) {
                        IEnergyStorage targetStorage = ((EnergyTransmissionAntennaBE) blockEntity).getLinkStorage();
                        if(targetStorage != null) {
                            if (energyStorage == targetStorage) continue;
                            if (energyStorage.getEnergyStored() > 0 && targetStorage.canReceive() && energyStorage.canExtract()) {
                                int trans= Math.min(energyStorage.getEnergyStored(), maxSend);
                                int a = targetStorage.receiveEnergy(trans, true);
                                int b = energyStorage.extractEnergy(trans, true);
                                if(a != 0 && b != 0){
                                    targetStorage.receiveEnergy(trans, false);
                                    energyStorage.extractEnergy(trans, false);
                                }
                            }
                        }
                    }
                    else iterator.remove();
                }
            }
        }
    }

    public IEnergyStorage getLinkStorage(){
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
        return level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPos, direction);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        List<Integer> list = new ArrayList<>();
        for (BlockPos pos : receivePos){
            list.add(pos.getX());
            list.add(pos.getY());
            list.add(pos.getZ());
        }
        tag.putIntArray("receive_pos", list);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        int[] pos = tag.getIntArray("receive_pos");
        for (int i = 0; i < pos.length / 3; i++){
            BlockPos blockPos = new BlockPos(pos[i * 3], pos[i * 3 + 1], pos[i * 3 + 2]);
            receivePos.add(blockPos);
        }
    }

    public List<BlockPos> getReceivePos() {
        return this.receivePos;
    }
}
