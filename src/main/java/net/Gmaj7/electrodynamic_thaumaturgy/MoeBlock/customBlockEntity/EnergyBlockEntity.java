package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeEnergyBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;


public class EnergyBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private final int tickEnergyTranslate = 1024;

    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(16777216) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    private final MoeBlockEntityItemHandler itemHandler = new MoeBlockEntityItemHandler(2){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyBlockEntity energyBlockEntity){
        MoeBlockEntityItemHandler itemHandler = energyBlockEntity.getItemHandler();
        EnergyHandler energyStorage = energyBlockEntity.getEnergy();
        ItemStack inStack = itemHandler.getStackInSlot(1), outStack = itemHandler.getStackInSlot(0);
        EnergyHandler inStorage = inStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(inStack));
        EnergyHandler outStorage = outStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(outStack));
        if(outStorage != null && !outStack.isEmpty()){
            try(Transaction transactionOut = Transaction.openRoot()) {
                int outAmount = Math.min(outStorage.getCapacityAsInt() - outStorage.getAmountAsInt(), Math.min(energyBlockEntity.tickEnergyTranslate, energyStorage.getAmountAsInt()));
                int insertAmount = energyStorage.extract(outAmount, transactionOut);
                if (insertAmount > 0) {
                    int extractAmount = outStorage.insert(insertAmount, transactionOut);
                    if (extractAmount == insertAmount) transactionOut.commit();
                }
            }
        }
        if (inStorage != null){
            try(Transaction transactionIn = Transaction.openRoot()) {
                int inAmount = Math.min(energyStorage.getCapacityAsInt() - energyStorage.getAmountAsInt(), Math.min(energyBlockEntity.tickEnergyTranslate, inStorage.getAmountAsInt()));
                int insertAmount = inStorage.extract(inAmount, transactionIn);
                if (insertAmount > 0) {
                    int extractAmount = energyStorage.insert(insertAmount, transactionIn);
                    if (extractAmount == insertAmount) transactionIn.commit();
                }
            }
        }
    }

    public EnergyHandler getEnergy() {
        return energy;
    }

    public void setEnergy(int i){
        energy.setEnergy(i);
    }

    public void clearContents() {
        for (int i = 0; i < itemHandler.size(); i++){
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
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
