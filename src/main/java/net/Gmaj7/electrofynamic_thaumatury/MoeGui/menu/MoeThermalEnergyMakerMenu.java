package net.Gmaj7.electrofynamic_thaumatury.MoeGui.menu;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlocks;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.ThermalGeneratorBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.MoeMenuType;
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
import net.neoforged.neoforge.items.SlotItemHandler;

public class MoeThermalEnergyMakerMenu extends AbstractContainerMenu {
    private final Level level;
    private final int inSlot = 0;
    public  final ThermalGeneratorBE blockEntity;

    public MoeThermalEnergyMakerMenu(int containerId, Inventory inventory, FriendlyByteBuf buf){
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public MoeThermalEnergyMakerMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(MoeMenuType.THERMAL_ENERGY_MAKER_MENU.get(), containerId);
        this.blockEntity = (ThermalGeneratorBE) blockEntity;
        this.level = inventory.player.level();

        this.addSlot(new SlotItemHandler(this.blockEntity.getItemHandler(), 0, 80, 44));

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
            if (index < inSlot + 1) {
                if (!this.moveItemStackTo(itemstack1, inSlot + 1, inSlot + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= inSlot + 1 && index < inSlot + 28) {
                if (!this.moveItemStackTo(itemstack1, inSlot + 28, inSlot + 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= inSlot + 28 && index < inSlot + 37 && !this.moveItemStackTo(itemstack1, inSlot + 1, inSlot + 28, false)) {
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
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, MoeBlocks.THERMAL_GENERATOR_BLOCK.get());
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
