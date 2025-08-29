package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.menu.MoeMagicCastBlockMenu;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MoeMagicTypeModuleItem;
import net.Gmaj7.electrofynamic_thaumatury.MoeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MagicCastMachineBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    protected Entity owner;
    protected UUID ownerUUID;
    protected int cooldown = 0;
    public static ItemStack magicItem = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
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
    public MagicCastMachineBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.MAGIC_CAST_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicCastMachineBE magicCastMachineBE){
        if(magicCastMachineBE.canCast() && !level.isClientSide()) {
            magicCastMachineBE.cast();
        }
        else if (magicCastMachineBE.cooldown > 0) magicCastMachineBE.cooldown--;
    }

    protected boolean canCast(){
        return cooldown <= 0 && itemHandler.getStackInSlot(0).getItem() instanceof MoeMagicTypeModuleItem
                && !((MoeMagicTypeModuleItem) itemHandler.getStackInSlot(0).getItem()).isEmpty()
                && ((MoeMagicTypeModuleItem) itemHandler.getStackInSlot(0).getItem()).canBlockCast(this);
    }

    protected void cast(){
        ((MoeMagicTypeModuleItem)itemHandler.getStackInSlot(0).getItem()).blockCast(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
        tag.putInt("cooldown", cooldown);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner");
            this.owner = null;
        }
        this.cooldown = tag.getInt("cooldown");
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
    }

    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else {
            if (this.ownerUUID != null) {
                Level var2 = this.getLevel();
                if (var2 instanceof ServerLevel serverlevel) {
                    this.owner = serverlevel.getEntity(this.ownerUUID);
                    return this.owner;
                }
            }
            return null;
        }
    }

    public void setOwner(Entity owner) {
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            this.owner = owner;
        }
    }

    @Override
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
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
        return new MoeMagicCastBlockMenu(i, inventory, this);
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void extractEnergy(int energy){
        this.energy.extractEnergy(energy * 64, false);
    }
}
