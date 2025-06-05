package net.Gmaj7.magic_of_electromagnetic.MoeGui.menu;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
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

public class MagicLithographyTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    private final ContainerLevelAccess access;
    private final int outNum = 1;
    private final int inNum = 0;
    Runnable slotUpdateListener;

    public MagicLithographyTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MagicLithographyTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(MoeMenuType.MAGIC_LITHOGRAPHY_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, outNum + 1);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.container = new SimpleContainer(outNum + 1){
            @Override
            public void setChanged() {
                super.setChanged();
                MagicLithographyTableMenu.this.slotsChanged(this);
                MagicLithographyTableMenu.this.slotUpdateListener.run();
            }
        };
        this.addSlot(new Slot(this.container, inNum, 20, 17){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(MoeItems.EMPTY_MODULE.get());
            }
        });
        this.addSlot(new Slot(this.container, outNum, 60, 17){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if(!container.getItem(inNum).isEmpty())
                    container.getItem(inNum).shrink(1);
                super.onTake(player, stack);
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
            if (index < outNum + 1) {
                if (!this.moveItemStackTo(itemstack1, outNum + 1, outNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.slots.get(outNum).hasItem() && item instanceof MagicCastItem && !this.moveItemStackTo(itemstack1, outNum, outNum + 1, false)){
                return ItemStack.EMPTY;
            }
            else if (item == MoeItems.EMPTY_MODULE.get()) {
                boolean flag = this.moveItemStackTo(itemstack1, inNum, outNum, false);
                if (flag) return ItemStack.EMPTY;
            }
            else if (index >= outNum + 1 && index < outNum + 28) {
                if (!this.moveItemStackTo(itemstack1, outNum + 28, outNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= outNum + 28 && index < outNum + 37 && !this.moveItemStackTo(itemstack1, outNum + 1, outNum + 28, false)) {
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
        return stillValid(this.access, player, MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get());
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
        ItemStack outSlot = this.slots.get(outNum).getItem();
        this.access.execute((level1, blockPos) -> {
            
        });
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.container){
            ItemStack itemstack = container.getItem(inNum);
            ItemStack out = container.getItem(outNum);
            if(!itemstack.isEmpty() && out.isEmpty()){
                this.access.execute((level1, blockPos) -> {
                    this.getSlot(outNum).set(new ItemStack(MoeItems.MAGMA_LIGHTING_MODULE.get()));
                });
            }
            if(itemstack.isEmpty() && !out.isEmpty()){
                this.access.execute((level1, blockPos) -> {
                    this.getSlot(outNum).set(ItemStack.EMPTY);
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
}
