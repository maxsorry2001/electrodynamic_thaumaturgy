package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluidTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityFluidHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.FluidSetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class AnnihilationReactorCoreBE extends AbstractGeneratorBE implements IFluidBlockEntity{
    private final BlockEntityFluidHandler fluidHandler = new BlockEntityFluidHandler(2, 50000) {
    @Override
    public FluidStack getStackInSlot(int slot) {
        return super.getStackInSlot(slot);
    }

    @Override
    protected void onContentsChanged(int index, FluidStack previousContents) {
        setChanged();
        if(!level.isClientSide()){
            PacketDistributor.sendToAllPlayers(new FluidSetPacket(getStackInSlot(0), getBlockPos()));
        }
    }
};
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    public AnnihilationReactorCoreBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.ANNIHILATION_REACTOR_CORE_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {

    }

    @Override
    protected boolean canEnergyMake() {
        return fluidHandler.getStackInSlot(0).is(EtFluidTypes.N_TYPE_ANNIHILATION_FLUID_TYPE.get()) && fluidHandler.getStackInSlot(1).is(EtFluidTypes.S_TYPE_ANNIHILATION_FLUID_TYPE.get());
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        this.energy.setEnergy(i);
    }

    @Override
    public StacksResourceHandler<FluidStack, FluidResource> getFluidHandler() {
        return fluidHandler;
    }

    @Override
    public void setFluid(FluidStack fluidStack, int slot) {
        this.fluidHandler.set(slot, FluidResource.of(fluidStack), fluidStack.amount());
    }
}
