package net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtTags;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagicEncodeRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagicEncodeRecipeInput;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class MagicEncodeTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    final ResultContainer resultContainer;
    private final ContainerLevelAccess access;
    private final Player player;
    private final int outNum = 3;
    private final int baseNum = 0;
    private final int code1Num = 1;
    private final int code2Num = 2;
    final Slot baseSlot;
    private final Slot code1Slot;
    private final Slot code2Slot;
    final Slot resultSlot;

    public MagicEncodeTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MagicEncodeTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(EtMenuTypes.MAGIC_ENCODE_TABLE_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(inventory, 1);
        this.level = inventory.player.level();
        this.player = inventory.player;
        this.container = new SimpleContainer(3){
            @Override
            public void setChanged() {
                super.setChanged();
                MagicEncodeTableMenu.this.slotsChanged(this);
            }
        };
        this.resultContainer = new ResultContainer();
        this.baseSlot = this.addSlot(new Slot(this.container, baseNum, 50, 19){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(EtTags.etItemTags.EMPTY_MAGIC_MODULE);
            }
        });
        this.code1Slot = this.addSlot(new Slot(this.container, code1Num, 80, 19) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });
        this.code2Slot = this.addSlot(new Slot(this.container, code2Num, 110, 19) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return true;
            }
        });
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, outNum, 80, 51) {
            public boolean mayPlace(ItemStack p_40362_) {
                return false;
            }

            public void onTake(Player player, ItemStack itemStack) {
                MagicEncodeTableMenu.this.baseSlot.remove(1);
                MagicEncodeTableMenu.this.code1Slot.remove(1);
                MagicEncodeTableMenu.this.code2Slot.remove(1);
                MagicEncodeTableMenu.this.slotsChanged(MagicEncodeTableMenu.this.container);

                super.onTake(player, itemStack);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(MagicEncodeTableMenu.this.baseSlot.getItem());
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
            itemstack = itemstack1.copy();
            if (index == outNum) {
                if (!this.moveItemStackTo(itemstack1, outNum + 1, outNum + 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack, itemstack1);
            }
            else if (index >= baseNum && index <= code2Num) {
                if (!this.moveItemStackTo(itemstack1, outNum + 1, outNum + 37, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (itemstack1.is(EtTags.etItemTags.EMPTY_MAGIC_MODULE)) {
                boolean flag = this.moveItemStackTo(itemstack1, baseNum, outNum, false);
                if (flag) return ItemStack.EMPTY;
            }
            else {
                if (!this.moveItemStackTo(itemstack1, code1Num, code2Num + 1, false)) {
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
        return stillValid(this.access, player, EtBlocks.MAGIC_ENCODE_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute(((level1, blockPos) -> {
            Inventory inventory = player.getInventory();
            if (inventory.player instanceof ServerPlayer) {
                inventory.placeItemBackInInventory(container.removeItemNoUpdate(baseNum));
                inventory.placeItemBackInInventory(container.removeItemNoUpdate(code1Num));
                inventory.placeItemBackInInventory(container.removeItemNoUpdate(code2Num));
            }
        }));
    }

    @Override
    public void slotsChanged(Container container) {
        if (!this.level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) this.level;
            ServerPlayer serverPlayer = (ServerPlayer) this.player;

            ItemStack base = this.baseSlot.getItem();
            ItemStack code1 = this.code1Slot.getItem();
            ItemStack code2 = this.code2Slot.getItem();
            MagicEncodeRecipeInput input = new MagicEncodeRecipeInput(base, code1, code2);

            RecipeHolder<MagicEncodeRecipe> recipe = serverLevel.recipeAccess()
                    .getRecipeFor(EtRecipes.MAGIC_ENCODE_TYPE.get(), input, serverLevel)
                    .orElse(null);

            ItemStack result = ItemStack.EMPTY;
            if (recipe != null && this.resultContainer.setRecipeUsed(serverPlayer, recipe)) {
                result = recipe.value().assemble(input);
                if (!result.isItemEnabled(serverLevel.enabledFeatures())) {
                    result = ItemStack.EMPTY;
                }
            }
            this.resultContainer.setItem(0, result);

            this.resultSlot.set(result);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(
                    this.containerId, this.incrementStateId(), outNum, result));
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

    public Slot getBaseSlot() {
        return baseSlot;
    }

    public Slot getCode1Slot() {
        return code1Slot;
    }

    public Slot getCode2Slot() {
        return code2Slot;
    }

    public Slot getResultSlot() {
        return resultSlot;
    }
}
