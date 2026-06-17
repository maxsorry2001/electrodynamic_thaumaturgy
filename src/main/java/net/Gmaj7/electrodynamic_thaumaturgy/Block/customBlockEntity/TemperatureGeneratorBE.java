package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlock.TemperatureGenerator;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtTags;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class TemperatureGeneratorBE extends AbstractGeneratorBE {
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(energy, getBlockPos()));
            }
        }
    };
    public TemperatureGeneratorBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.TEMPERATURE_GENERATOR_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        try (Transaction transaction = Transaction.openRoot()){
            int i = blockEntity.getEnergy().insert(512, transaction);
            if(i > 0) transaction.commit();
        }
    }

    protected boolean canEnergyMake() {
        BlockState blockStateUp = level.getBlockState(getBlockPos().above());
        BlockState blockStateDown = level.getBlockState(getBlockPos().below());
        BlockState blockState = level.getBlockState(getBlockPos());
        boolean upHot = isHot(blockStateUp), downHot = isHot(blockStateDown), upCold = isCold(blockStateUp), downCold = isCold(blockStateDown);
        TemperatureGenerator.WorkType workType;
        if(upHot){
            if(!downCold) workType = TemperatureGenerator.WorkType.HOT;
            else workType = TemperatureGenerator.WorkType.WORK_B;
        }
        else if(upCold){
            if(!downHot) workType = TemperatureGenerator.WorkType.COLD;
            else workType = TemperatureGenerator.WorkType.WORK_A;
        }
        else {
            if(downCold) workType = TemperatureGenerator.WorkType.COLD;
            else if (downHot) workType = TemperatureGenerator.WorkType.HOT;
            else workType = TemperatureGenerator.WorkType.NORMAL;
        }
        if(blockState.getValue(TemperatureGenerator.WORK_TYPE) != workType)
            level.setBlockAndUpdate(getBlockPos(), blockState.setValue(TemperatureGenerator.WORK_TYPE, workType));
        return (upCold && downHot) || (upHot && downCold);
    }
    private boolean isHot(BlockState blockState) {
        return blockState.is(EtTags.etBlockTags.HOT_BLOCK) || (blockState.getBlock() instanceof AbstractFurnaceBlock && blockState.getValue(AbstractFurnaceBlock.LIT));
    }

    private boolean isCold(BlockState blockState) {
        return blockState.is(EtTags.etBlockTags.COLD_BLOCK);
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }
}
