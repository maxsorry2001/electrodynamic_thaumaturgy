package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ThermalGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeThermalGeneratorMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
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
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
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
        protected void onContentsChanged(int slot) {
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
        blockEntity.getEnergy().insert(768, false);
    }

    @Override
    protected boolean canEnergyMake() {
        if(!level.isClientSide()){
            if (burnTime > 0) burnTime--;
            if (burnTime <= 0) {
                if (!itemHandler.getStackInSlot(0).isEmpty() && energy.getAmountAsInt() != energy.getCapacityAsInt()) {
                    int time = itemHandler.getStackInSlot(0).getBurnTime(null) / 4;
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getAmountAsInt());
        tag.putInt("fullBurnTime", fullBurnTime);
        tag.putInt("burnTime", burnTime);
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        fullBurnTime = tag.getInt("fullBurnTime");
        burnTime = tag.getInt("burnTime");
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
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
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandler() {
        return itemHandler;
    }


    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
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
}
