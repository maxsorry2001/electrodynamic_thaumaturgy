package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public interface IDirectionFluidBlockEntity {
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandlerWithDirection(Direction direction);

    void setFluid(FluidStack fluidStack);

    void changeFluidDirectionSet(Direction direction);
}
