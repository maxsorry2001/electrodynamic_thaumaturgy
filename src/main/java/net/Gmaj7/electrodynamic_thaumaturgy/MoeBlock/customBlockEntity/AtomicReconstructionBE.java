package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeAtomicReconstructionBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class AtomicReconstructionBE extends BlockEntity implements IMoeEnergyBlockEntity, MenuProvider, IMoeDirectionItemBlockEntity {
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {
        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final MoeBlockEntityItemHandler inputItemHandler = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final MoeBlockEntityItemHandler outputItemHandler = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private final MoeBlockEntityItemHandler targetItemHandler = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
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
        EnergyHandler energyStorage = blockEntity.getEnergy();
        if(energyStorage.getAmountAsInt() < progressUse) return;
        blockEntity.progressTick ++;
        if(blockEntity.progressTick > 5 && blockEntity.progress == 0)
            PacketDistributor.sendToAllPlayers(new MoePacket.AtomicPacket(pos, blockEntity.progress));
        if(blockEntity.progressTick != 10) return;
        blockEntity.progressTick = 0;
        if(!blockEntity.canUpProgress()) return;
        boolean lastStep = blockEntity.progress >= maxProgress;
        try (Transaction transaction = Transaction.openRoot()){
            int i = energyStorage.extract(progressUse, transaction);
            if(i != progressUse)  return;
            int j = blockEntity.inputItemHandler.extract(0, blockEntity.inputItemHandler.getResource(0), 1, transaction);
            if(j != 1) return;
            if(lastStep){
                int k = blockEntity.outputItemHandler.insert(ItemResource.of(blockEntity.targetItemHandler.getStackInSlot(0)), 1, transaction);
                if(k != 1) return;
            }
            transaction.commit();
            if(lastStep) blockEntity.progress = 0;
            else blockEntity.progress ++;
        }
        PacketDistributor.sendToAllPlayers(new MoePacket.AtomicPacket(pos, blockEntity.progress));
    }

    private boolean canUpProgress() {
        return !this.inputItemHandler.getStackInSlot(0).isEmpty()
                && this.inputItemHandler.getStackInSlot(0).is(Tags.Items.STONES)
                && !this.targetItemHandler.getStackInSlot(0).isEmpty()
                && (this.targetItemHandler.getStackInSlot(0).is(Tags.Items.INGOTS) || this.targetItemHandler.getStackInSlot(0).is(Tags.Items.GEMS))
                && (this.outputItemHandler.getStackInSlot(0).isEmpty() || this.outputItemHandler.getStackInSlot(0).is(this.targetItemHandler.getStackInSlot(0).getItem()));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        inputItemHandler.serializeWithKey("input_item", output);
        outputItemHandler.serializeWithKey("output_item", output);
        targetItemHandler.serializeWithKey("target_item", output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        inputItemHandler.deserializeWithKey("input_item", input);
        outputItemHandler.deserializeWithKey("output_item", input);
        targetItemHandler.deserializeWithKey("target_item", input);
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
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction){
        StacksResourceHandler<ItemStack, ItemResource> itemHandler;
        if(direction == null) itemHandler = outputItemHandler;
        else
            switch (direction){
                case UP -> itemHandler = inputItemHandler;
                case DOWN -> itemHandler = outputItemHandler;
                default -> itemHandler = targetItemHandler;
            }
        return itemHandler;
    }

    public float getProgressPer(){
        return (float) progress / maxProgress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
