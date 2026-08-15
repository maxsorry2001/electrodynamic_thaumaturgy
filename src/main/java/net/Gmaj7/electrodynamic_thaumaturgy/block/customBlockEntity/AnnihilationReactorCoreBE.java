package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluidTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluids;
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
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class AnnihilationReactorCoreBE extends AbstractGeneratorBE implements IFluidBlockEntity{
    private final BlockEntityFluidHandler fluidHandler = new BlockEntityFluidHandler(2, 50000) {
        @Override
        public FluidStack getStackInSlot(int slot) {
        return super.getStackInSlot(slot);
    }

        @Override
        public int insert(FluidResource resource, int amount, TransactionContext transaction) {
            if(!resource.is(EtFluidTypes.N_TYPE_ANNIHILATION_FLUID_TYPE.get()) && !resource.is(EtFluidTypes.S_TYPE_ANNIHILATION_FLUID_TYPE.get()))
                return 0;
            TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
            int inserted = 0;
            int index = resource.is(EtFluidTypes.N_TYPE_ANNIHILATION_FLUID_TYPE.get()) ? 0 : 1;
            inserted += insert(index, resource, amount - inserted, transaction);
            if (inserted == amount);
            return inserted;
        }

        @Override
        public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
            if((index == 0 && resource.is(EtFluidTypes.N_TYPE_ANNIHILATION_FLUID_TYPE.get())) || (index == 1 && resource.is(EtFluidTypes.S_TYPE_ANNIHILATION_FLUID_TYPE.get())))
                return super.insert(index, resource, amount, transaction);
            return 0;
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
        try (Transaction transaction = Transaction.openRoot()){
            int amount = energy.insert(16384, transaction);
            if(amount > 0){
                fluidHandler.extract(0, FluidResource.of(EtFluids.N_TYPE_ANNIHILATION_FLUID_SOURCE.get()), 100, transaction);
                fluidHandler.extract(1, FluidResource.of(EtFluids.S_TYPE_ANNIHILATION_FLUID_SOURCE.get()), 100, transaction);
            }
        }
    }

    @Override
    protected boolean canEnergyMake() {
        return fluidHandler.getStackInSlot(0).is(EtFluidTypes.N_TYPE_ANNIHILATION_FLUID_TYPE.get())
                && fluidHandler.getStackInSlot(0).amount() > 100
                && fluidHandler.getStackInSlot(1).is(EtFluidTypes.S_TYPE_ANNIHILATION_FLUID_TYPE.get())
                && fluidHandler.getStackInSlot(1).amount() > 100;
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
