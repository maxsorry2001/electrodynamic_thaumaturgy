package net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AtomicReconstructionRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
