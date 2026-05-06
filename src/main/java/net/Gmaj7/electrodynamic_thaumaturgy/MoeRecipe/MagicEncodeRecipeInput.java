package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MagicEncodeRecipeInput(ItemStack input, ItemStack code1, ItemStack code2) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        ItemStack itemStack;
        switch (i){
            case 0 -> itemStack = input;
            case 1 -> itemStack = code1;
            case 2 -> itemStack = code2;
            default -> itemStack = ItemStack.EMPTY;
        }
        return itemStack;
    }

    @Override
    public int size() {
        return 3;
    }
}
