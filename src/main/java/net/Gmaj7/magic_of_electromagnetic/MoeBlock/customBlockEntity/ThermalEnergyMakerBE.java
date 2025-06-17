package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock.ThermalEnergyMakerBlock;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeThermalEnergyMakerMenu;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
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
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThermalEnergyMakerBE extends AbstractEnergyMakerBE implements IMoeItemBlockEntity, MenuProvider {
    private int burnTime = 0;
    private int fullBurnTime = 0;
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    private final ItemStackHandler itemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public ThermalEnergyMakerBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.THERMAL_ENERGY_MAKER_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractEnergyMakerBE blockEntity) {
        blockEntity.getEnergy().receiveEnergy(32, false);
    }

    @Override
    protected boolean canEnergyMake() {
        if(!level.isClientSide()){
            if (burnTime > 0) burnTime--;
            if (burnTime <= 0) {
                if (!itemHandler.getStackInSlot(0).isEmpty()) {
                    int time = itemHandler.getStackInSlot(0).getBurnTime(null);
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
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ThermalEnergyMakerBlock.LIT, burnTime > 0));
            PacketDistributor.sendToAllPlayers(new MoePacket.ThermalSetPacket(burnTime, fullBurnTime, getBlockPos()));
        }
        return level.getBlockState(getBlockPos()).getValue(ThermalEnergyMakerBlock.LIT);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
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
        return new MoeThermalEnergyMakerMenu(i, inventory, this);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    public IItemHandler getItemHandler() {
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
        return Component.translatable("block.magic_of_electromagnetic.thermal_energy_maker_block");
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
