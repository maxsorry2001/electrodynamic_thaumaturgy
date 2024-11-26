package net.Gmaj7.magic_of_electromagnetic.MoeGui;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
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

public class MoeAssemblyTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    private final ContainerLevelAccess access;
    Runnable slotUpdateListener;

    public MoeAssemblyTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MoeAssemblyTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(MoeMenuType.ASSEMBLY_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, 2);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.container = new SimpleContainer(2){
            @Override
            public void setChanged() {
                super.setChanged();
                MoeAssemblyTableMenu.this.slotsChanged(this);
                MoeAssemblyTableMenu.this.slotUpdateListener.run();
            }
        };
        this.addSlot(new Slot(this.container, 0, 20, 30));
        this.addSlot(new Slot(this.container, 1, 70, 30));
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
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            }else if (index == 1){
                if (!this.moveItemStackTo(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.slots.get(0).hasItem() && item instanceof  MagicUseItem && !this.moveItemStackTo(itemstack1, 0, 1, false)){
                return ItemStack.EMPTY;
            }else if (this.slots.get(0).hasItem() && !this.slots.get(1).hasItem() && (item == MoeItems.RAY_MODULE.get() || item == MoeItems.PLASMA_MODULE.get()) && !this.moveItemStackTo(itemstack1, 1, 2, false)){
                return ItemStack.EMPTY;
            }
            else if (index >= 2 && index < 29) {
                if (!this.moveItemStackTo(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)) {
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
        ItemStack itemStack = this.slots.get(0).getItem();
        ItemStack itemStack1 = itemStack.copy();
        this.access.execute((level1, blockPos) -> {
            MoeMagicType newType = MoeMagicType.ERROR;
            ItemStack oldTypeSlot = this.slots.get(1).getItem();
            if(oldTypeSlot.getItem() instanceof MoeMagicTypeModuleItem) newType = ((MoeMagicTypeModuleItem) oldTypeSlot.getItem()).getMagicType();
            else if (oldTypeSlot.isEmpty()) newType = MoeMagicType.EMPTY;
            if(newType != MoeMagicType.ERROR) {
                ItemContainerContents contents = itemStack1.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
                ItemStack newTypeSlot = contents.getStackInSlot(0).copy();
                List<ItemStack> list = new ArrayList<>(contents.stream().toList());
                list.add(0, oldTypeSlot);
                ItemContainerContents itemContainerContents = ItemContainerContents.fromItems(list);
                itemStack1.set(DataComponents.CONTAINER, itemContainerContents);
                this.container.setItem(0, itemStack1);
                this.container.setItem(1, newTypeSlot);
                this.container.setChanged();
            }
        });
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.container){
            ItemStack itemstack = container.getItem(0);
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

    private List<ItemStack> getSlotItems(){
        List<ItemStack> list = new ArrayList<>();
        list.add(0, this.slots.get(1).getItem());
        return list;
    }
}
