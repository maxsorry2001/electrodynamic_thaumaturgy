package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeElectromagneticDriverBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.MoeMagicTypeModuleItem;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ElectromagneticDriverBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    protected Entity owner;
    protected UUID ownerUUID;
    protected int cooldown = 0;
    public static ItemStack magicItem = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
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

    public ElectromagneticDriverBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ELECTROMAGNETIC_DRIVER_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticDriverBE electromagneticDriverBE){
        if(electromagneticDriverBE.canCast() && !level.isClientSide()) {
            electromagneticDriverBE.cast();
        }
        else if (electromagneticDriverBE.cooldown > 0) electromagneticDriverBE.cooldown--;
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if(this.ownerUUID != null)
            output.store("owner", UUIDUtil.CODEC, ownerUUID);
        output.putInt("cooldown", cooldown);
        energy.serialize(output);
        itemHandler.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read("owner", UUIDUtil.CODEC).ifPresent(
                uuid -> this.ownerUUID = uuid
        );
        this.cooldown = input.getInt("cooldown").get();
        energy.deserialize(input);
        itemHandler.deserialize(input);
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
    public MoeBlockEntityItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
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
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeElectromagneticDriverBlockMenu(i, inventory, this);
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void extract(int energy, Transaction transaction){
        this.energy.extract(energy * 64, transaction);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
}
