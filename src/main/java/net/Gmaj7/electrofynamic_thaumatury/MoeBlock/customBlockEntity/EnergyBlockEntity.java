package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.menu.MoeEnergyBlockMenu;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;


public class EnergyBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private final int tickEnergyTranslate = 1024;

    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(16777216) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public EnergyBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyBlockEntity energyBlockEntity){
        IItemHandler itemHandler = energyBlockEntity.getItemHandler();
        IEnergyStorage energyStorage = energyBlockEntity.getEnergy();
        IEnergyStorage inStorage = itemHandler.getStackInSlot(1).getCapability(Capabilities.EnergyStorage.ITEM);
        IEnergyStorage outStorage = itemHandler.getStackInSlot(0).getCapability(Capabilities.EnergyStorage.ITEM);
        if(outStorage != null){
            int canOut = outStorage.getMaxEnergyStored() - outStorage.getEnergyStored();
            if(canOut < energyBlockEntity.tickEnergyTranslate)
                energyBlockEntity.outEnergy(Math.min(energyStorage.getEnergyStored(), canOut), outStorage, energyStorage);
            else
                energyBlockEntity.outEnergy(Math.min(energyStorage.getEnergyStored(), energyBlockEntity.tickEnergyTranslate), outStorage, energyStorage);
        }
        if (inStorage != null){
            int canIn = energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
            if(canIn < energyBlockEntity.tickEnergyTranslate)
                energyBlockEntity.inEnergy(Math.min(inStorage.getEnergyStored(), canIn), inStorage, energyStorage);
            else
                energyBlockEntity.inEnergy(Math.min(inStorage.getEnergyStored(), energyBlockEntity.tickEnergyTranslate), inStorage, energyStorage);

        }
    }

    private void outEnergy(int i, IEnergyStorage outStorage, IEnergyStorage energyStorage){
        outStorage.receiveEnergy(i, false);
        energyStorage.extractEnergy(i, false);
    }

    private void inEnergy(int i, IEnergyStorage inStorage, IEnergyStorage energyStorage){
        energyStorage.receiveEnergy(i, false);
        inStorage.extractEnergy(i, false);
    }

    public IEnergyStorage getEnergy() {
        return energy;
    }

    public void setEnergy(int i){
        energy.setEnergy(i);
    }

    public void clearContents() {
        for (int i = 0; i < itemHandler.getSlots(); i++){
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
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
        return Component.translatable("block.electrofynamic_thaumatury.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeEnergyBlockMenu(i, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return super.getUpdateTag(registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }
}
