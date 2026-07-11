package net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder;

import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.MagneticDissolutionRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MagneticDissolutionRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final Ingredient inputItem;
    private final FluidIngredient inputFluid;
    private final int fluidCost;
    private final FluidStackTemplate output;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public MagneticDissolutionRecipeBuilder(HolderGetter<Item> items, Fluid output, int outputCount, ItemLike inputItem, Fluid inputFluid, int fluidCost) {
        this.items = items;
        this.inputItem = Ingredient.of(inputItem);
        this.inputFluid = FluidIngredient.of(inputFluid);
        this.fluidCost = fluidCost;
        this.output = new FluidStackTemplate(output, outputCount);
    }

    public static MagneticDissolutionRecipeBuilder creat(HolderGetter<Item> items, Fluid output, ItemLike inputItem, Fluid inputFluid){
        return new MagneticDissolutionRecipeBuilder(items, output, 1, inputItem, inputFluid, 1000);
    }

    public static MagneticDissolutionRecipeBuilder creat(HolderGetter<Item> items, Fluid output, int outputCount, ItemLike inputItem, Fluid inputFluid){
        return new MagneticDissolutionRecipeBuilder(items, output, outputCount, inputItem, inputFluid, 1000);
    }

    public static MagneticDissolutionRecipeBuilder creat(HolderGetter<Item> items, Fluid output, ItemLike inputItem, Fluid inputFluid, int fluidCost){
        return new MagneticDissolutionRecipeBuilder(items, output, 1, inputItem, inputFluid, fluidCost);
    }

    public static MagneticDissolutionRecipeBuilder creat(HolderGetter<Item> items, Fluid output, int outputCount, ItemLike inputItem, Fluid inputFluid, int fluidCost){
        return new MagneticDissolutionRecipeBuilder(items, output, outputCount, inputItem, inputFluid, fluidCost);
    }

    @Override
    public MagneticDissolutionRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public MagneticDissolutionRecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, output.typeHolder().unwrapKey().orElseThrow().identifier());
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        MagneticDissolutionRecipe recipe = new MagneticDissolutionRecipe(inputFluid, fluidCost, inputItem, output);
        recipeOutput.accept(resourceKey, recipe, null);
    }
}
