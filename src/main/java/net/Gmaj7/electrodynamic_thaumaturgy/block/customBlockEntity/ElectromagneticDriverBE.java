package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.EtTabs;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.menu.ElectromagneticDriverBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.EtMagicTypeModuleItem;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinitionLoader;
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

public class ElectromagneticDriverBE extends BlockEntity implements IEnergyBlockEntity, IItemBlockEntity, MenuProvider {
    protected Entity owner;
    protected UUID ownerUUID;
    protected int cooldown = 0;
    public static ItemStack magicItem = EtTabs.getDefaultMagicUse(EtItems.ELECTROMAGNETIC_ROD.get());
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final BlockEntityItemHandler itemHandler = new BlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ElectromagneticDriverBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.ELECTROMAGNETIC_DRIVER_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticDriverBE electromagneticDriverBE){
        if(electromagneticDriverBE.canCast() && !level.isClientSide()) {
            electromagneticDriverBE.cast();
        }
        else if (electromagneticDriverBE.cooldown > 0) electromagneticDriverBE.cooldown--;
    }

    protected boolean canCast(){
        MagicDefinition magicDefinition = MagicDefinitionLoader.get(itemHandler.getStackInSlot(0).get(EtDataComponentTypes.MAGIC_DEF_LOCATION));
        return cooldown <= 0 && itemHandler.getStackInSlot(0).getItem() instanceof EtMagicTypeModuleItem
                && !((EtMagicTypeModuleItem) itemHandler.getStackInSlot(0).getItem()).isEmpty()
                && ((EtMagicTypeModuleItem) itemHandler.getStackInSlot(0).getItem()).canBlockCast(this, magicDefinition);
    }

    protected void cast(){
        ((EtMagicTypeModuleItem)itemHandler.getStackInSlot(0).getItem()).blockCast(this, MagicDefinitionLoader.get(itemHandler.getStackInSlot(0).get(EtDataComponentTypes.MAGIC_DEF_LOCATION)));
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
    public BlockEntityItemHandler getItemHandler() {
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
        return new ElectromagneticDriverBlockMenu(i, inventory, this);
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean extract(int energy){
        boolean commit;
        try (Transaction transaction = Transaction.openRoot()){
            int i = this.energy.extract(energy * 64, transaction);
            commit = i == energy * 64;
            if(commit)
                transaction.commit();
        }
        return commit;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
}
