package net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record MagnetoFusionRecipeInput(List<ItemStack> items) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return items.get(i);
    }

    @Override
    public int size() {
        return items.size();
    }
}
