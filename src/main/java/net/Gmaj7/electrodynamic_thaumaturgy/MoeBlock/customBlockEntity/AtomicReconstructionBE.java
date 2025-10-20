package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeAtomicReconstructionBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class AtomicReconstructionBE extends BlockEntity implements IMoeEnergyBlockEntity, MenuProvider, IMoeDirectionItemBlockEntity {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };

    private final ItemStackHandler inputItemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final ItemStackHandler outputItemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final ItemStackHandler targetItemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                progress = 0;
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private int progressTick = 1;
    private int progress = 0;
    private static final int maxProgress = 8;
    private static final int progressUse = 2048;
    public AtomicReconstructionBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ATOMIC_RECONSTRUCTION_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AtomicReconstructionBE blockEntity) {
        if(level.isClientSide()) return;
        IEnergyStorage energyStorage = blockEntity.getEnergy();
        if(energyStorage.getEnergyStored() < progressUse) return;
        blockEntity.progressTick ++;
        if(blockEntity.progressTick != 10) return;
        blockEntity.progressTick = 0;
        if(!blockEntity.canUpProgress()) return;
        blockEntity.progress ++;
        blockEntity.inputItemHandler.getStackInSlot(0).shrink(1);
        blockEntity.getEnergy().extractEnergy(progressUse, false);
        if(blockEntity.progress != maxProgress) return;
        blockEntity.progress = 0;
        blockEntity.outputItemHandler.insertItem(0, new ItemStack(blockEntity.targetItemHandler.getStackInSlot(0).getItem()), false);
    }

    private boolean canUpProgress() {
        return !this.inputItemHandler.getStackInSlot(0).isEmpty()
                && this.inputItemHandler.getStackInSlot(0).is(Tags.Items.STONES)
                && !this.targetItemHandler.getStackInSlot(0).isEmpty()
                && (this.outputItemHandler.getStackInSlot(0).isEmpty() || this.outputItemHandler.getStackInSlot(0).is(this.targetItemHandler.getStackInSlot(0).getItem()));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        inputItemHandler.deserializeNBT(registries, tag.getCompound("input_item_handler"));
        outputItemHandler.deserializeNBT(registries, tag.getCompound("output_item_handler"));
        targetItemHandler.deserializeNBT(registries, tag.getCompound("target_item_handler"));
        progress = tag.getInt("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("input_item_handler", inputItemHandler.serializeNBT(registries));
        tag.put("output_item_handler", outputItemHandler.serializeNBT(registries));
        tag.put("target_item_handler", targetItemHandler.serializeNBT(registries));
        tag.putInt("progress", progress);
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
    public void drops() {
        SimpleContainer container = new SimpleContainer(inputItemHandler.getStackInSlot(0), outputItemHandler.getStackInSlot(0), targetItemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeAtomicReconstructionBlockMenu(i, inventory, this);
    }

    @Override
    public IItemHandler getItemHandlerWithDirection(Direction direction){
        IItemHandler itemHandler;
        if(direction == null) itemHandler = outputItemHandler;
        else
            switch (direction){
                case UP -> itemHandler = inputItemHandler;
                case DOWN -> itemHandler = outputItemHandler;
                default -> itemHandler = targetItemHandler;
            }
        return itemHandler;
    }
}
