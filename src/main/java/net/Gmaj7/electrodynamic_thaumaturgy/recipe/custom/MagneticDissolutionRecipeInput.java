package net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record MagneticDissolutionRecipeInput(FluidStack inputFluid, ItemStack inputItem) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return inputItem;
    }

    @Override
    public int size() {
        return 1;
    }
}
