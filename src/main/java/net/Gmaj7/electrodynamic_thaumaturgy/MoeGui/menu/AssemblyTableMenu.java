package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.*;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AssemblyTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    private final ContainerLevelAccess access;
    private final int toolSlotNum = 10;
    private final int typeSlotStartNum = 2;
    private final int lcSlotNum = 1;
    private final int powerSlotNum = 0;
    private final int typeSlotEndNum = 10;
    Runnable slotUpdateListener;

    public AssemblyTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public AssemblyTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, toolSlotNum + 1);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.container = new SimpleContainer(toolSlotNum + 1){
            @Override
            public void setChanged() {
                super.setChanged();
                AssemblyTableMenu.this.slotsChanged(this);
                AssemblyTableMenu.this.slotUpdateListener.run();
            }

            @Override
            public void setItem(int slot, ItemStack itemStack) {
                this.getItems().set(slot, itemStack);
                itemStack.limitSize(this.getMaxStackSize(itemStack));
            }

            @Override
            public ItemStack removeItem(int slot, int count) {
                ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);
                if (!result.isEmpty()) {
                    if(slot == toolSlotNum) slotsChanged(container);
                    else moduleChanged(container);
                }

                return result;
            }
        };
        this.addSlot(new Slot(this.container, powerSlotNum, 54, 52){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !this.container.getSlot(toolSlotNum).get().isEmpty() && stack.getItem() instanceof PowerAmplifierItem;
            }

            @Override
            public void setChanged() {
                moduleChanged(this.container);
            }
        });
        this.addSlot(new Slot(this.container, lcSlotNum, 54, 70){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !this.container.getSlot(toolSlotNum).get().isEmpty() && stack.getItem() instanceof LcOscillatorModuleItem;
            }

            @Override
            public void setChanged() {
                moduleChanged(this.container);
            }
        });
        for (int i = typeSlotStartNum; i < typeSlotEndNum; i++) {
            int dx, dy;
            if(i < 6) {
                dx = 48 * i + 16;
                dy = 26;
            }
            else {
                dx = 48 * i - 56;
                dy = 44;
            }
            this.addSlot(new Slot(this.container, i , dx, dy){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return !this.container.getSlot(toolSlotNum).get().isEmpty() && stack.getItem() instanceof MoeMagicTypeModuleItem;
                }

                @Override
                public void setChanged() {
                    moduleChanged(this.container);
                }
            });
        }
        this.addSlot(new Slot(this.container, toolSlotNum, 20, 10){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof MagicCastItem;
            }
        });
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index < toolSlotNum + 1) {
                if (!this.moveItemStackTo(itemstack1, toolSlotNum + 1, toolSlotNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.slots.get(toolSlotNum).hasItem() && item instanceof MagicCastItem && !this.moveItemStackTo(itemstack1, toolSlotNum, toolSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (!this.slots.get(lcSlotNum).hasItem() && item instanceof LcOscillatorModuleItem && !this.moveItemStackTo(itemstack1, lcSlotNum, lcSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (!this.slots.get(powerSlotNum).hasItem() && item instanceof PowerAmplifierItem && !this.moveItemStackTo(itemstack1, powerSlotNum, powerSlotNum + 1, false)){
                return ItemStack.EMPTY;
            }else if (item instanceof MoeMagicTypeModuleItem){
                boolean flag = moveModuleItem(typeSlotStartNum, typeSlotEndNum, itemstack1);
                if (flag) return ItemStack.EMPTY;
            }
            else if (index >= toolSlotNum + 1 && index < toolSlotNum + 28) {
                if (!this.moveItemStackTo(itemstack1, toolSlotNum + 28, toolSlotNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= toolSlotNum + 28 && index < toolSlotNum + 37 && !this.moveItemStackTo(itemstack1, toolSlotNum + 1, toolSlotNum + 28, false)) {
                return ItemStack.EMPTY;
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
        return stillValid(this.access, player, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level1, blockPos) -> {
            this.clearContainer(player, this.container);
        }));
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {

        return true;
    }

    private void moduleChanged(Container container){
        ItemStack toolSlot = this.slots.get(toolSlotNum).getItem();
        ItemStack toolStack = toolSlot.copy();
        this.access.execute((level1, blockPos) -> {
            if(toolStack.getItem() instanceof MagicCastItem) {
                List<ItemStack> newList = new ArrayList<>();
                for (int i = powerSlotNum; i < typeSlotEndNum; i++){
                    ItemStack itemStack = this.slots.get(i).getItem();
                    if(itemStack.isEmpty()){
                        if(i == powerSlotNum) newList.add(new ItemStack(MoeItems.EMPTY_POWER.get()));
                        else if (i == lcSlotNum) newList.add(new ItemStack(MoeItems.EMPTY_LC.get()));
                        else newList.add(new ItemStack(MoeItems.EMPTY_MAGIC_MODULE.get()));
                    }
                    else newList.add(itemStack);
                }
                toolSlot.set(MoeDataComponentTypes.ROD_SETTING.get(), ItemContainerContents.fromItems(newList));
            }
        });
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.container){
            ItemStack toolStack = container.getItem(toolSlotNum);
            if(!toolStack.isEmpty() && toolStack.getItem() instanceof MagicCastItem){
                this.access.execute((level1, blockPos) -> {
                    ItemContainerContents contents = toolStack.get(MoeDataComponentTypes.ROD_SETTING.get());
                    for (int i = powerSlotNum; i < typeSlotEndNum; i++){
                        ItemStack menuSlot = contents.getStackInSlot(i).copy();
                        if(menuSlot.getItem() instanceof IMoeModuleItem && ((IMoeModuleItem) menuSlot.getItem()).isEmpty())
                            menuSlot = ItemStack.EMPTY;
                        this.container.setItem(i, menuSlot);
                    }
                });
            }
            else
                for (int i = powerSlotNum; i < typeSlotEndNum; i++)
                    this.container.setItem(i, ItemStack.EMPTY);
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

    public Slot getToolSlot(){
        return this.slots.get(toolSlotNum);
    }

    private boolean moveModuleItem(int start, int end, ItemStack itemStack){
        boolean flag = true;
        for (int i = start; i < end; i++){
            if(!this.slots.get(i).hasItem() && this.moveItemStackTo(itemStack, i, i + 1, false)){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
