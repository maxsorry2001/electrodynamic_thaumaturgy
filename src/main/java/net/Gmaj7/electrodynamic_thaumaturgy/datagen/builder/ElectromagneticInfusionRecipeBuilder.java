package net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder;

import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.ElectromagneticDissociationRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.ElectromagneticInfusionRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ElectromagneticInfusionRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final Ingredient inputItem;
    private final int itemCost;
    private final FluidIngredient inputFluid;
    private final int fluidCost;
    private final ItemStackTemplate output;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ElectromagneticInfusionRecipeBuilder(HolderGetter<Item> items, ItemLike output, int outputCount, ItemLike inputItem, int itemCost, Fluid inputFluid, int fluidCost) {
        this.items = items;
        this.inputItem = Ingredient.of(inputItem);
        this.itemCost = itemCost;
        this.inputFluid = FluidIngredient.of(inputFluid);
        this.fluidCost = fluidCost;
        this.output = new ItemStackTemplate(output.asItem(), outputCount);
    }

    public ElectromagneticInfusionRecipeBuilder(HolderGetter<Item> items, ItemLike output, int outputCount, TagKey<Item> inputItem, int itemCost, Fluid inputFluid, int fluidCost) {
        this.items = items;
        this.inputItem = Ingredient.of(items.getOrThrow(inputItem));
        this.itemCost = itemCost;
        this.inputFluid = FluidIngredient.of(inputFluid);
        this.fluidCost = fluidCost;
        this.output = new ItemStackTemplate(output.asItem(), outputCount);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, ItemLike inputItem, Fluid inputFluid){
        return new ElectromagneticInfusionRecipeBuilder(items, output, 1, inputItem, 1, inputFluid, 1000);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, int outputCount, ItemLike inputItem, Fluid inputFluid){
        return new ElectromagneticInfusionRecipeBuilder(items, output, outputCount, inputItem, 1, inputFluid, 1000);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, ItemLike inputItem, Fluid inputFluid, int fluidCost){
        return new ElectromagneticInfusionRecipeBuilder(items, output, 1, inputItem, 1, inputFluid, fluidCost);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, int outputCount, ItemLike inputItem, Fluid inputFluid, int fluidCost){
        return new ElectromagneticInfusionRecipeBuilder(items, output, outputCount, inputItem, 1, inputFluid, fluidCost);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, int outputCount, ItemLike inputItem, int itemCost, Fluid inputFluid, int fluidCost){
        return new ElectromagneticInfusionRecipeBuilder(items, output, outputCount, inputItem, itemCost, inputFluid, fluidCost);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, int outputCount, ItemLike inputItem, int itemCost, Fluid inputFluid){
        return new ElectromagneticInfusionRecipeBuilder(items, output, outputCount, inputItem, itemCost, inputFluid, 1000);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, ItemLike inputItem, int itemCost, Fluid inputFluid){
        return new ElectromagneticInfusionRecipeBuilder(items, output, 1, inputItem, itemCost, inputFluid, 1000);
    }

    public static ElectromagneticInfusionRecipeBuilder creat(HolderGetter<Item> items, ItemLike output, TagKey<Item> inputItem, int itemCost, Fluid inputFluid){
        return new ElectromagneticInfusionRecipeBuilder(items, output, 1, inputItem, itemCost, inputFluid, 1000);
    }

    @Override
    public ElectromagneticInfusionRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public ElectromagneticInfusionRecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(output);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        ElectromagneticInfusionRecipe recipe = new ElectromagneticInfusionRecipe(inputItem, itemCost, inputFluid, fluidCost, output);
        recipeOutput.accept(resourceKey, recipe, null);
    }

    public void saveWithAddition(RecipeOutput recipeOutput, String nameAddition){
        ElectromagneticInfusionRecipe recipe = new ElectromagneticInfusionRecipe(inputItem, itemCost, inputFluid, fluidCost, output);
        recipeOutput.accept(ResourceKey.create(Registries.RECIPE, Identifier.parse(output.item().getRegisteredName() + "_dissociation_" + nameAddition)), recipe, null);
    }
}
