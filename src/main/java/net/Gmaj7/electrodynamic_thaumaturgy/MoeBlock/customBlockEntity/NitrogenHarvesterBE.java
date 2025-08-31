package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeNitrogenHarvesterBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NitrogenHarvesterBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private static int growUse = 8192;
    private static int harvestUse = 16384;
    private int checkTick = 0;
    private static int workTick = 100;
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(27){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public NitrogenHarvesterBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.NITROGEN_HARVESTER_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NitrogenHarvesterBE nitrogenHarvesterBE){
        nitrogenHarvesterBE.checkTick ++;
        if(level.isClientSide() || nitrogenHarvesterBE.checkTick < workTick) return;
        nitrogenHarvesterBE.checkTick = 0;
        nitrogenHarvesterBE.growCrops(pos);
    }

    private void harvestCrops(BlockPos pos) {
        for (int dx = -1; dx < 2; dx++){
            for (int dz = -1; dz < 2; dz++){
                BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                BlockState blockState = this.getLevel().getBlockState(blockPos);
                if(!this.getLevel().isClientSide() && blockState.getBlock() instanceof CropBlock cropBlock && cropBlock.getAge(blockState) == cropBlock.getMaxAge()){
                    List<ItemStack> list = Block.getDrops(blockState, (ServerLevel) this.getLevel(), blockPos, null);
                    this.getLevel().destroyBlock(blockPos, false);
                    this.getLevel().setBlock(blockPos, cropBlock.getStateForAge(0), 2);
                    for (int i = 0; i < list.size(); i++){
                        ItemStack itemStack = list.get(i).copy();
                        itemStack.setCount(Math.min(itemStack.getCount() * 2, itemStack.getMaxStackSize()));
                        for (int j = 0; j < this.itemHandler.getSlots(); j++) {
                            ItemStack result = this.getItemHandler().insertItem(j, itemStack, false);
                            if(!result.isEmpty()) itemStack = result.copy();
                            else break;
                        }
                    }
                }
            }
        }
    }

    private void growCrops(BlockPos pos) {
        for (int dx = -1; dx < 2; dx++){
            for (int dz = -1; dz < 2; dz++){
                BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                BlockState blockState = this.getLevel().getBlockState(blockPos);
                if(blockState.getBlock() instanceof CropBlock cropBlock){
                    if(cropBlock.getAge(blockState) < cropBlock.getMaxAge() && this.getEnergy().getEnergyStored() > growUse) {
                        ((CropBlock) blockState.getBlock()).growCrops(this.getLevel(), blockPos, blockState);
                        this.getEnergy().extractEnergy(growUse, false);
                    }
                    else if(cropBlock.getAge(blockState) == cropBlock.getMaxAge() && this.getEnergy().getEnergyStored() > harvestUse) {
                        this.harvestCrops(blockPos);
                        this.energy.extractEnergy(harvestUse, false);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
        tag.putInt("check_tick", checkTick);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
        checkTick = tag.getInt("check_tick");
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        this.energy.setEnergy(i);
    }

    @Override
    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
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
        return new MoeNitrogenHarvesterBlockMenu(i, inventory, this);
    }
}
