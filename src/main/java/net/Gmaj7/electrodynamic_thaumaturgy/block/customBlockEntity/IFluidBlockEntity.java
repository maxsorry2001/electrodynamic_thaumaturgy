package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public interface IFluidBlockEntity {
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandler();

    default void setFluid(FluidStack fluidStack){
        setFluid(fluidStack, 0);
    }

    void setFluid(FluidStack fluidStack, int slot);
}
