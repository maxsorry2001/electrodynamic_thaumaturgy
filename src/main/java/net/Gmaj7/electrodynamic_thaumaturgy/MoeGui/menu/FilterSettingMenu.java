package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class FilterSettingMenu extends AbstractContainerMenu {
    private Level level;
    private Player player;
    private ItemStack filter;

    public FilterSettingMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, inventory, inventory.player.getMainHandItem());
    }

    public FilterSettingMenu(int containerId, Inventory inventory, ItemStack filter) {
        super(MoeMenuType.FILTER_SETTING_MENU.get(), containerId);
        this.player = inventory.player;
        this.level = inventory.player.level();
        this.filter = filter;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

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
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index >= 0 && index < 27) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 27 && index < 36 && !this.moveItemStackTo(itemstack1, 0, 9, false)) {
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
        return true;
    }

    public List<ItemStack> getFilterList(){
        return new ArrayList<>(filter.get(MoeDataComponentTypes.MOE_CONTAINER).allItemsCopyStream().toList());
    }

    public ItemStack getFilter() {
        return filter;
    }
}
