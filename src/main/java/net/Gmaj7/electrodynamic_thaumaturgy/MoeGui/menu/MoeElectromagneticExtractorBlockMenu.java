package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticExtractorBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MoeElectromagneticExtractorBlockMenu extends AbstractContainerMenu {
    private final Level level;
    private final int slotNum = 26;
    public  final ElectromagneticExtractorBE blockEntity;
    public boolean quick = false;

    public MoeElectromagneticExtractorBlockMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public MoeElectromagneticExtractorBlockMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(MoeMenuType.GEOLOGICAL_METAL_EXCAVATOR_MENU.get(), containerId);
        this.blockEntity = (ElectromagneticExtractorBE) blockEntity;
        this.level = inventory.player.level();

        addMachineSlot(((ElectromagneticExtractorBE) blockEntity).getItemHandler());

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
            if (index < slotNum + 1) {
                if (!this.moveItemStackTo(itemstack1, slotNum + 1, slotNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= slotNum + 1 && index < slotNum + 37) {
                if (!this.moveItemStackTo(itemstack1, 0, slotNum + 1, false)) {
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get());
    }

    private void addMachineSlot(IItemHandler itemHandler){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++)
                this.addSlot(new SlotItemHandler(itemHandler, j + i * 9, 8 + j * 18, 25 + i * 18));
        }
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

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if(id == 0)
            blockEntity.addWidth(quick);
        if(id == 1)
            blockEntity.reduceWidth(quick);
        if(id == 2)
            blockEntity.addDepth(quick);
        if(id == 3)
            blockEntity.reduceDepth(quick);
        return false;
    }
}
