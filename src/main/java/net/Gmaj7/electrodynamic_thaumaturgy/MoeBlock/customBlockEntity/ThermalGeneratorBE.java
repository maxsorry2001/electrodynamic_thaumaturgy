package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ThermalGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeThermalGeneratorMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class ThermalGeneratorBE extends AbstractGeneratorBE implements IMoeItemBlockEntity, MenuProvider {
    private int burnTime = 0;
    private int fullBurnTime = 0;
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    private final MoeBlockEntityItemHandler itemHandler = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public ThermalGeneratorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.THERMAL_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        try (Transaction transaction = Transaction.openRoot()){
            int i = blockEntity.getEnergy().insert(768, transaction);
            if(i > 0) transaction.commit();
        }
    }

    @Override
    protected boolean canEnergyMake() {
        if(!level.isClientSide()){
            if (burnTime > 0) burnTime--;
            if (burnTime <= 0) {
                if (!itemHandler.getStackInSlot(0).isEmpty() && energy.getAmountAsInt() != energy.getCapacityAsInt()) {
                    int time = itemHandler.getStackInSlot(0).getBurnTime(null, level.fuelValues()) / 4;
                    if (time > 0) {
                        burnTime = time;
                        fullBurnTime = time;
                        if (itemHandler.getStackInSlot(0).getItem() instanceof BucketItem)
                            itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
                        else
                            itemHandler.getStackInSlot(0).shrink(1);
                    } else {
                        fullBurnTime = 0;
                    }
                } else fullBurnTime = 0;
            }
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ThermalGeneratorBlock.LIT, burnTime > 0));
            PacketDistributor.sendToAllPlayers(new MoePacket.ThermalSetPacket(burnTime, fullBurnTime, getBlockPos()));
        }
        return burnTime > 0;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
        output.putInt("full_burn_time", fullBurnTime);
        output.putInt("burn_time", burnTime);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
        burnTime = input.getInt("burn_time").get();
        fullBurnTime = input.getInt("full_burn_time").get();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeThermalGeneratorMenu(i, inventory, this);
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    public MoeBlockEntityItemHandler getItemHandler() {
        return itemHandler;
    }


    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.thermal_generator_block");
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public int getFullBurnTime() {
        return fullBurnTime;
    }

    public void setFullBurnTime(int fullBurnTime) {
        this.fullBurnTime = fullBurnTime;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
}
