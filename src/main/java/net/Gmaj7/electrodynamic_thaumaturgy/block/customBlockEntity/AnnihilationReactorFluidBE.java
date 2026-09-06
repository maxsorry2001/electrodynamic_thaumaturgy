package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class AnnihilationReactorFluidBE extends AnnihilationReactorIoBE implements IFluidBlockEntity {
    private BlockEntityFluidHandler fluidHandler;

    public AnnihilationReactorFluidBE(BlockPos worldPosition, BlockState blockState) {
        super(EtBlockEntities.ANNIHILATION_REACTOR_FLUID_BE.get(), worldPosition, blockState);
    }

    @Override
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandler() {
        if(fluidHandler == null){
            if(corePos != null) {
                BlockEntity blockEntity = level.getBlockEntity(corePos);
                if(blockEntity instanceof AnnihilationReactorCoreBE){
                    fluidHandler = ((BlockEntityFluidHandler) ((AnnihilationReactorCoreBE) blockEntity).getFluidHandler());
                }
                else corePos = null;
            }
        }
        return fluidHandler;
    }

    @Override
    public void setFluid(FluidStack fluidStack, int slot) {
        fluidHandler.set(slot, FluidResource.of(fluidStack), fluidStack.amount());
    }
}
