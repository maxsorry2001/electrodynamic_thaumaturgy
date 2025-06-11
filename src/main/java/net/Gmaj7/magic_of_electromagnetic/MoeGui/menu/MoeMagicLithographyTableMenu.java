package net.Gmaj7.magic_of_electromagnetic.MoeGui.menu;

import com.google.common.collect.Lists;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.MoeMenuType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MagicLithographyRecipe;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MagicLithographyRecipeInput;
import net.Gmaj7.magic_of_electromagnetic.MoeRecipe.MoeRecipes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class MoeMagicLithographyTableMenu extends AbstractContainerMenu {
    private final Level level;
    public final Container container;
    final ResultContainer resultContainer;
    private final ContainerLevelAccess access;
    private final int outNum = 1;
    private final int inNum = 0;
    private ItemStack input;
    final Slot inputSlot;
    final Slot resultSlot;
    private List<RecipeHolder<MagicLithographyRecipe>> recipes;
    Runnable slotUpdateListener;
    private final DataSlot selectedRecipeIndex;

    public MoeMagicLithographyTableMenu(int containerId, Inventory inventory){
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MoeMagicLithographyTableMenu(int  containerId, Inventory inventory, final ContainerLevelAccess access){
        super(MoeMenuType.MAGIC_LITHOGRAPHY_TABLE_MENU.get(), containerId);
        this.selectedRecipeIndex = DataSlot.standalone();
        this.access = access;
        checkContainerSize(inventory, 1);
        this.level = inventory.player.level();
        this.slotUpdateListener = () -> {};
        this.recipes = Lists.newArrayList();
        this.input = ItemStack.EMPTY;
        this.container = new SimpleContainer(outNum + 1){
            @Override
            public void setChanged() {
                super.setChanged();
                MoeMagicLithographyTableMenu.this.slotsChanged(this);
                MoeMagicLithographyTableMenu.this.slotUpdateListener.run();
            }
        };
        this.resultContainer = new ResultContainer();
        this.inputSlot = this.addSlot(new Slot(this.container, inNum, 20, 33){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(MoeItems.EMPTY_PRIMARY_MODULE.get());
            }
        });
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            public boolean mayPlace(ItemStack p_40362_) {
                return false;
            }

            public void onTake(Player p_150672_, ItemStack p_150673_) {
                p_150673_.onCraftedBy(p_150672_.level(), p_150672_, p_150673_.getCount());
                MoeMagicLithographyTableMenu.this.resultContainer.awardUsedRecipes(p_150672_, this.getRelevantItems());
                ItemStack itemstack = MoeMagicLithographyTableMenu.this.inputSlot.remove(1);
                if (!itemstack.isEmpty()) {
                    MoeMagicLithographyTableMenu.this.setupResultSlot();
                }
                super.onTake(p_150672_, p_150673_);
            }

            private List<ItemStack> getRelevantItems() {
                return List.of(MoeMagicLithographyTableMenu.this.inputSlot.getItem());
            }
        });
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addDataSlot(selectedRecipeIndex);
    }

    public List<RecipeHolder<MagicLithographyRecipe>> getRecipes() {
        return this.recipes;
    }

    public int getSelectedRecipeIndex(){
        return this.selectedRecipeIndex.get();
    }

    public boolean hasInputItem() {
        return this.inputSlot.hasItem() && !this.recipes.isEmpty();
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
            else if (item == MoeItems.EMPTY_PRIMARY_MODULE.get()) {
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
            Inventory inventory = player.getInventory();
            if (inventory.player instanceof ServerPlayer) {
                inventory.placeItemBackInInventory(container.removeItemNoUpdate(inNum));
            }
        }));
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (this.isValidRecipeIndex(id)) {
            this.selectedRecipeIndex.set(id);
            this.setupResultSlot();
        }
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        ItemStack itemstack = this.inputSlot.getItem();
        if (!itemstack.is(this.input.getItem())) {
            this.input = itemstack.copy();
            this.setupRecipeList(container, itemstack);
        }
    }

    private boolean isValidRecipeIndex(int recipeIndex) {
        return recipeIndex >= 0 && recipeIndex < this.recipes.size();
    }

    private static MagicLithographyRecipeInput createRecipeInput(Container container) {
        return new MagicLithographyRecipeInput(container.getItem(0));
    }

    void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            RecipeHolder<MagicLithographyRecipe> recipeholder = (RecipeHolder)this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = ((MagicLithographyRecipe)recipeholder.value()).assemble(createRecipeInput(this.container), this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultContainer.setRecipeUsed(recipeholder);
                this.resultSlot.set(itemstack);
            } else {
                this.resultSlot.set(ItemStack.EMPTY);
            }
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    private List<RecipeHolder<MagicLithographyRecipe>> getRecipe() {
        return this.level.getRecipeManager().getRecipesFor(MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get(), new MagicLithographyRecipeInput(container.getItem(inNum)), level);
    }

    private void setupRecipeList(Container container, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.level.getRecipeManager().getRecipesFor(MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get(), createRecipeInput(container), this.level);
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

    public Slot getInputSlot() {
        return inputSlot;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public void registerUpdateListener(Runnable listener) {
        this.slotUpdateListener = listener;
    }
}
