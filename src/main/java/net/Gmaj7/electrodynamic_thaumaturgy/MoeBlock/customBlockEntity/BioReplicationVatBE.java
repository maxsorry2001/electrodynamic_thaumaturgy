package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeEntityCloneBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class BioReplicationVatBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity,MenuProvider {
    private static final int SPAWN_NEED = 16384;
    private int clone = 100;

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

    public BioReplicationVatBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.BIO_REPLICATION_VAT_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, BioReplicationVatBE bioReplicationVatBE){
        if(bioReplicationVatBE.clone > 0) bioReplicationVatBE.clone--;
        if(bioReplicationVatBE.clone <= 0 && bioReplicationVatBE.canSpawn() && !level.isClientSide()){
            ItemStack itemStack = bioReplicationVatBE.getItemHandler().getStackInSlot(0);
            Entity entity =  itemStack.get(MoeDataComponentTypes.ENTITY_TYPE).create(level, EntitySpawnReason.MOB_SUMMONED);
            if(entity instanceof LivingEntity) {
                try (Transaction transaction = Transaction.openRoot()){
                    entity.teleportTo(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                    bioReplicationVatBE.clone = (int)((LivingEntity) entity).getMaxHealth();
                    int i = bioReplicationVatBE.getEnergy().extract(SPAWN_NEED, transaction);
                    if(i > 0) {
                        transaction.commit();
                        level.addFreshEntity(entity);
                    }
                }
            }
        }
    }

    private boolean canSpawn() {
        return this.itemHandler.getStackInSlot(0).is(MoeItems.GENETIC_RECORDER.get())
                && this.itemHandler.getStackInSlot(0).has(MoeDataComponentTypes.ENTITY_TYPE)
                && this.energy.getAmountAsInt() > SPAWN_NEED;
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
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeEntityCloneBlockMenu(i, inventory, this);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
        output.putInt("clone", clone);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
        this.clone = input.getInt("clone").get();
    }
}
