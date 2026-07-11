package net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.AtomicReconstructionRecipe;
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
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class AtomicReconstructionRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final ItemStackTemplate result;
    private final int energyUse;
    private final int tickUse;
    private final Ingredient ingredient;
    private final int itemCost;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;


    private AtomicReconstructionRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result, Ingredient ingredient, int itemCost, int energyUse, int tickUse) {
        this.items = items;
        this.result = result;
        this.ingredient = ingredient;
        this.itemCost = itemCost;
        this.energyUse = energyUse;
        this.tickUse = tickUse;
    }

    public static AtomicReconstructionRecipeBuilder creat(HolderGetter<Item> items, Item output, int outputCount, ItemLike input, int itemCost, int energyUse, int tickUse){
        return new AtomicReconstructionRecipeBuilder(items, new ItemStackTemplate(output, outputCount), Ingredient.of(input), itemCost, energyUse, tickUse);
    }

    public static AtomicReconstructionRecipeBuilder creat(HolderGetter<Item> items, Item output, ItemLike input, int itemCost, int energyUse, int tickUse){
        return new AtomicReconstructionRecipeBuilder(items, new ItemStackTemplate(output), Ingredient.of(input), itemCost, energyUse, tickUse);
    }

    public static AtomicReconstructionRecipeBuilder creat(HolderGetter<Item> items, Item output, int outputCount, TagKey<Item> input, int itemCost, int energyUse, int tickUse){
        return new AtomicReconstructionRecipeBuilder(items, new ItemStackTemplate(output, outputCount), Ingredient.of(items.getOrThrow(input)), itemCost, energyUse, tickUse);
    }

    @Override
    public AtomicReconstructionRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public AtomicReconstructionRecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        AtomicReconstructionRecipe recipe = new AtomicReconstructionRecipe(ingredient, itemCost, energyUse, tickUse, result);
        recipeOutput.accept(resourceKey, recipe, null);
    }

    public void saveWithAddition(RecipeOutput recipeOutput, String nameAddition){
        AtomicReconstructionRecipe recipe = new AtomicReconstructionRecipe(ingredient, itemCost, energyUse, tickUse, result);
        recipeOutput.accept(ResourceKey.create(Registries.RECIPE, Identifier.parse( result.item().toString() + "_reconstruction_" + nameAddition)), recipe, null);
    }

    public void saveWithName(RecipeOutput recipeOutput, String name){
        AtomicReconstructionRecipe recipe = new AtomicReconstructionRecipe(ingredient, itemCost, energyUse, tickUse, result);
        recipeOutput.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name)), recipe, null);
    }
}
