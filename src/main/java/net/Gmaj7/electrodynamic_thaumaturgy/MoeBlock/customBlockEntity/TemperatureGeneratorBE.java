package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.TemperatureGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class TemperatureGeneratorBE extends AbstractGeneratorBE {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public TemperatureGeneratorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.TEMPERATURE_GENERATOR_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energy.setEnergy(tag.getInt("energy"));
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        blockEntity.getEnergy().receiveEnergy(512, false);
    }

    protected boolean canEnergyMake() {
        BlockState blockStateUp = level.getBlockState(getBlockPos().above());
        BlockState blockStateDown = level.getBlockState(getBlockPos().below());
        BlockState blockState = level.getBlockState(getBlockPos());
        boolean upHot = isHot(blockStateUp), downHot = isHot(blockStateDown), upCold = isCold(blockStateUp), downCold = isCold(blockStateDown);
        TemperatureGeneratorBlock.WorkType workType;
        if(upHot){
            if(!downCold) workType = TemperatureGeneratorBlock.WorkType.HOT;
            else workType = TemperatureGeneratorBlock.WorkType.WORK_B;
        }
        else if(upCold){
            if(!downHot) workType = TemperatureGeneratorBlock.WorkType.COLD;
            else workType = TemperatureGeneratorBlock.WorkType.WORK_A;
        }
        else {
            if(downCold) workType = TemperatureGeneratorBlock.WorkType.COLD;
            else if (downHot) workType = TemperatureGeneratorBlock.WorkType.HOT;
            else workType = TemperatureGeneratorBlock.WorkType.NORMAL;
        }
        if(blockState.getValue(TemperatureGeneratorBlock.WORK_TYPE) != workType)
            level.setBlockAndUpdate(getBlockPos(), blockState.setValue(TemperatureGeneratorBlock.WORK_TYPE, workType));
        return (upCold && downHot) || (upHot && downCold);
    }
    private boolean isHot(BlockState blockState) {
        return blockState.is(MoeTags.moeBlockTags.HOT_BLOCK) || (blockState.getBlock() instanceof AbstractFurnaceBlock && blockState.getValue(AbstractFurnaceBlock.LIT));
    }

    private boolean isCold(BlockState blockState) {
        return blockState.is(MoeTags.moeBlockTags.COLD_BLOCK);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }
}
