package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public class AnnihilationReactorEnergyBE extends AnnihilationReactorIoBE implements IEnergyBlockEntity {
    private BlockEntityEnergyHandler energyHandler;

    public AnnihilationReactorEnergyBE(BlockPos worldPosition, BlockState blockState) {
        super(EtBlockEntities.ANNIHILATION_REACTOR_ENERGY_BE.get(), worldPosition, blockState);
    }

    @Override
    public EnergyHandler getEnergy() {
        if(energyHandler == null){
            if(corePos != null) {
                BlockEntity blockEntity = level.getBlockEntity(corePos);
                if(blockEntity instanceof AnnihilationReactorCoreBE){
                    energyHandler = ((BlockEntityEnergyHandler)((AnnihilationReactorCoreBE) blockEntity).getEnergy());
                }
                else corePos = null;
            }
        }
        return energyHandler;
    }

    @Override
    public void setEnergy(int i) {
        if(energyHandler != null) energyHandler.setEnergy(i);
    }
}
