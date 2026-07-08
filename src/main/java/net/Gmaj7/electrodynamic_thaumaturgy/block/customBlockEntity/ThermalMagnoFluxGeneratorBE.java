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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class ThermalMagnoFluxGeneratorBE extends AbstractGeneratorBE implements IFluidBlockEntity{
    private final BlockEntityFluidHandler fluidHandler = new BlockEntityFluidHandler(1, 50000) {
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
    public ThermalMagnoFluxGeneratorBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.THERMAL_MAGNO_FLUX_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        fluidHandler.serialize(output);
        energy.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        fluidHandler.deserialize(input);
        energy.deserialize(input);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        try (Transaction transaction = Transaction.openRoot()){
            int inserted = energy.insert(4096, transaction);
            if(inserted > 0) {
                fluidHandler.extract(0, fluidHandler.getResource(0), 100, transaction);
                transaction.commit();
            }
        }
    }

    @Override
    protected boolean canEnergyMake() {
        return fluidHandler.getStackInSlot(0).is(EtFluidTypes.THERMAL_MAGNO_FLUX_FLUID_TYPE.get()) && fluidHandler.getAmountAsInt(0) >= 100;
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
        fluidHandler.set(slot, FluidResource.of(fluidStack), fluidStack.amount());
    }
}
