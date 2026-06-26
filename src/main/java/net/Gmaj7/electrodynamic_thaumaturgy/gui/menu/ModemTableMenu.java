package net.Gmaj7.electrodynamic_thaumaturgy.gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.EnhancementModulateItem;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.MagicCastItem;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.PulseBow;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ModemTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    private final ContainerLevelAccess access;
    private final int toolSlotNum = 8;
    private final int enhanceStartNum = 0;
    private final int enhanceEndNum = 8;
    Runnable slotUpdateListener;

    public ModemTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ModemTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(EtMenuTypes.MODEM_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, toolSlotNum + 1);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.container = new SimpleContainer(toolSlotNum + 1){
            @Override
            public void setChanged() {
                super.setChanged();
                ModemTableMenu.this.slotsChanged(this);
                ModemTableMenu.this.slotUpdateListener.run();
            }
        };
        for (int i = enhanceStartNum; i < enhanceEndNum; i++) {
            int dx, dy;
            if(i < 4) {
                dx = 18 * i + 52;
                dy = 30;
            }
            else {
                dx = 18 * i - 20;
                dy = 48;
            }this.addSlot(new Slot(this.container, i , dx, dy){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof EnhancementModulateItem;
                }
            });
        }
        this.addSlot(new Slot(this.container, toolSlotNum, 20, 37){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof MagicCastItem || stack.getItem() instanceof PulseBow;
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
            }
            else if (item instanceof EnhancementModulateItem){
                boolean flag = moveModuleItem(enhanceStartNum, enhanceEndNum, itemstack1);
                if (flag) return ItemStack.EMPTY;
            }
            else if (index < toolSlotNum + 28) {
                if (!this.moveItemStackTo(itemstack1, toolSlotNum + 28, toolSlotNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < toolSlotNum + 37 && !this.moveItemStackTo(itemstack1, toolSlotNum + 1, toolSlotNum + 28, false)) {
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
        return stillValid(this.access, player, EtBlocks.ELECTROMAGNETIC_MODEM_TABLE.get());
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
        ItemStack toolSlot = this.slots.get(toolSlotNum).getItem();
        this.access.execute((level1, blockPos) -> {
            if(toolSlot.getItem() instanceof MagicCastItem) {
                List<EnhancementData> list = new ArrayList<>();
                for (int i = enhanceStartNum; i < enhanceEndNum; i++){
                    EnhancementData data = this.slots.get(i).getItem().get(EtDataComponentTypes.ENHANCEMENT_DATA);
                    if(data != null) list.add(data);
                }
                EnhancementData enhancementData = EnhancementData.savvedData.add(list);
                toolSlot.set(EtDataComponentTypes.ENHANCEMENT_DATA, enhancementData);
            }
        });
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.container){
            ItemStack itemstack = container.getItem(toolSlotNum);
            if(!itemstack.isEmpty()){
                this.access.execute((level1, blockPos) -> {

                });
            }
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
