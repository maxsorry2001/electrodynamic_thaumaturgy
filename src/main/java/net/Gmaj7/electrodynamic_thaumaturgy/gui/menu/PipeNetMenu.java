package net.Gmaj7.electrodynamic_thaumaturgy.gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.init.pipeNet.PipeNet;
import net.Gmaj7.electrodynamic_thaumaturgy.init.pipeNet.PipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class PipeNetMenu extends AbstractContainerMenu {
    private final Level level;
    private final PipeNet.PipeNetType netType;
    private final int netId;
    protected LinkedHashMap<BlockPos, Set<Direction>> insert;
    protected LinkedHashMap<BlockPos, Map<Direction, PipeNet.TransferMode>> extract;

    public PipeNetMenu(MenuType<?> menuType, int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert, int netId, PipeNet.PipeNetType netType) {
        super(menuType, containerId);
        this.level = inventory.player.level();
        this.insert = new LinkedHashMap<>(insert);
        this.extract = new LinkedHashMap<>(extract);
        this.netId = netId;
        this.netType = netType;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

    }

    public int getNetId() {
        return netId;
    }

    public PipeNet.PipeNetType getNetType() {
        return netType;
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
        // 检查网络是否仍然存在且包含该节点
        if (player.level().isClientSide()) return true; // 客户端不做校验

        PipeNetSaveData<?> saveData = getPipeNetData((ServerPlayer)player ); // 你需要根据实际情况获取
        PipeNet net = saveData.getNet(netId);
        if (net == null) {
            // 网络已不存在或该节点已不在网络中，返回 false，菜单会被服务端自动关闭
            return false;
        }
        return true;
    }

    protected abstract PipeNetSaveData getPipeNetData(ServerPlayer player);

    @Override
    public void slotsChanged(Container container) {

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
    public void removed(Player player) {
        if(player instanceof ServerPlayer && getPipeNetData((ServerPlayer) player).containNet(netId)){
            removeLookingPlayer((ServerPlayer) player);
        }
        super.removed(player);
    }

    protected abstract void removeLookingPlayer(ServerPlayer player);

    public Level getLevel() {
        return level;
    }

    public Map<BlockPos, Map<Direction, PipeNet.TransferMode>> getExtract() {
        return extract;
    }

    public Map<BlockPos, Set<Direction>> getInsert() {
        return insert;
    }

    public void pipeReset(LinkedHashMap<BlockPos, Set<Direction>> insert, LinkedHashMap<BlockPos, Map<Direction, PipeNet.TransferMode>> extract){
        this.insert = insert;
        this.extract = extract;
    }
}
