package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EddyCurrentRemelterBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class EddyCurrentRemelterBlockMenu extends AbstractContainerMenu {
    private final Level level;
    private final int inNum = 0;
    private final int outNum = 1;
    private ContainerData data;
    private ContainerLevelAccess access;
    public  final EddyCurrentRemelterBE blockEntity;
    public EddyCurrentRemelterBlockMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, ContainerLevelAccess.NULL, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public EddyCurrentRemelterBlockMenu(int containerId, ContainerLevelAccess access, Inventory inventory, BlockEntity blockEntity) {
        super(MoeMenuType.EDDY_CURRENT_REMELTER_BLOCK_MENU.get(), containerId);
        this.access = access;
        this.blockEntity = (EddyCurrentRemelterBE) blockEntity;
        this.level = inventory.player.level();
        this.data = ((EddyCurrentRemelterBE)blockEntity).getData();

        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerInput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerInput().set(slot, resource, amount), 0, 41, 34));
        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerOutput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerOutput().set(slot, resource, amount), 0, 121, 34));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        addDataSlots(((EddyCurrentRemelterBE) blockEntity).getData());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == outNum) {
                if (!this.moveItemStackTo(itemstack1, outNum + 1, outNum + 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack, itemstack1);
            }
            else if (index >= inNum && index <= outNum) {
                if (!this.moveItemStackTo(itemstack1, outNum + 1, outNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (!this.moveItemStackTo(itemstack1, inNum, outNum, false)) {
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
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, MoeBlocks.EDDY_CURRENT_REMELTER_MACHINE_BLOCK.get());
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

    public int getProgress(){
        return data.get(0);
    }

    public int getMaxProgress(){
        return data.get(1);
    }

    public int getItemSet(){
        return data.get(2);
    }
}
