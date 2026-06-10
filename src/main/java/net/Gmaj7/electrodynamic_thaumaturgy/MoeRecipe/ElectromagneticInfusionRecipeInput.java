package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record ElectromagneticInfusionRecipeInput(ItemStack inputItem, FluidStack inputFluid) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        return inputItem;
    }

    @Override
    public int size() {
        return 1;
    }
}
