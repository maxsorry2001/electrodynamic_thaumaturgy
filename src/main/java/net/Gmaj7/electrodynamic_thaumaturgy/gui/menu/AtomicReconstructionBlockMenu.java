package net.Gmaj7.electrodynamic_thaumaturgy.gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AtomicReconstructionBE;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.EtMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class AtomicReconstructionBlockMenu extends AbstractContainerMenu {
    private final Level level;
    private ContainerData data;
    public final AtomicReconstructionBE blockEntity;
    private static final int inputSlot = 0;
    private static final int outputSlot = 1;


    public AtomicReconstructionBlockMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public AtomicReconstructionBlockMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(EtMenuTypes.ATOMIC_RECONSTRUCTION_BLOCK_MENU.get(), containerId);
        this.blockEntity = (AtomicReconstructionBE) blockEntity;
        this.level = inventory.player.level();
        this.data = ((AtomicReconstructionBE) blockEntity).getData();

        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerInput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerInput().set(slot, resource, amount), 0, 52, 32));
        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerOutput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerOutput().set(slot, resource, amount), 0, 108, 32));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        addDataSlots(((AtomicReconstructionBE) blockEntity).getData());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index < outputSlot + 1) {
                if (!this.moveItemStackTo(itemstack1, outputSlot + 1, outputSlot + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index < outputSlot + 28) {
                if (!this.moveItemStackTo(itemstack1, inputSlot, inputSlot + 1, false)) {
                    if(!this.moveItemStackTo(itemstack1, outputSlot + 28, outputSlot + 37, false))
                        return ItemStack.EMPTY;
                }
            } else if (index < outputSlot + 37) {
                if (!this.moveItemStackTo(itemstack1, inputSlot, inputSlot + 1, false)) {
                    if(!this.moveItemStackTo(itemstack1, outputSlot + 1, outputSlot + 28, false))
                        return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }



    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get());
    }

    private void addPlayerInventory(Inventory inventory){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++)
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        }
    }

    private void addPlayerHotbar(Inventory inventory){
        for (int i = 0; i < 9; i++)
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
    }
}
