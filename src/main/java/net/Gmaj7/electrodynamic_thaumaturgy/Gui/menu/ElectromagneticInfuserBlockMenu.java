package net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.ElectromagneticInfuserBE;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
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

public class ElectromagneticInfuserBlockMenu extends AbstractContainerMenu {
    private final Level level;
    private final int inNum = 0;
    private final int outNum = 1;
    private ContainerData data;
    public  final ElectromagneticInfuserBE blockEntity;
    public ElectromagneticInfuserBlockMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public ElectromagneticInfuserBlockMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(EtMenuTypes.ELECTROMAGNETIC_INFUSER_BLOCK_MENU.get(), containerId);
        this.blockEntity = (ElectromagneticInfuserBE) blockEntity;
        this.level = inventory.player.level();
        this.data = ((ElectromagneticInfuserBE) blockEntity).getData();

        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerInput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerInput().set(slot, resource, amount), 0, 40, 33));
        this.addSlot(new ResourceHandlerSlot(this.blockEntity.getItemHandlerOutput(), (slot, resource, amount) -> this.blockEntity.getItemHandlerOutput().set(slot, resource, amount), 0, 102, 33));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get());
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

    public int getItemSet(){
        return data.get(0);
    }
}
