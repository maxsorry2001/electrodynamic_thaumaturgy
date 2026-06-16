package net.Gmaj7.electrodynamic_thaumaturgy.datagen.Builder;

import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagnetoFusionRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MagnetoFusionRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private MagnetoFusionRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result) {
        this.items = items;
        this.result = result;
    }

    public static MagnetoFusionRecipeBuilder result(HolderGetter<Item> items, ItemLike result) {
        return new MagnetoFusionRecipeBuilder(items, new ItemStackTemplate(result.asItem()));
    }

    public static MagnetoFusionRecipeBuilder result(HolderGetter<Item> items, ItemLike result, int count) {
        return new MagnetoFusionRecipeBuilder(items, new ItemStackTemplate(result.asItem(), count));
    }

    public MagnetoFusionRecipeBuilder items(ItemLike... input) {
        if(this.ingredients.size() >= 3 || input.length == 0) return this;
        this.ingredients.add(Ingredient.of(input));
        return this;
    }

    public MagnetoFusionRecipeBuilder tag(TagKey<Item> tagKey){
        if(this.ingredients.size() >= 3) return this;
        this.ingredients.add(Ingredient.of(this.items.getOrThrow(tagKey)));
        return this;
    }

    @Override
    public MagnetoFusionRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public MagnetoFusionRecipeBuilder group(@Nullable String s) {
        this.group = s;
        return this;
    }

    public ItemStackTemplate getResult() {
        return result;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        MagnetoFusionRecipe recipe = new MagnetoFusionRecipe(ingredients, result);
        recipeOutput.accept(resourceKey, recipe, null);
    }
}
