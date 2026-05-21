package net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticDissociationRecipe;
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

import java.util.LinkedHashMap;
import java.util.Map;

public class ElectromagneticDissociationRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result, Ingredient ingredient) {
        this.items = items;
        this.result = result;
        this.ingredient = ingredient;
    }

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result, TagKey<Item> tagKey) {
        this.items = items;
        this.result = result;
        this.ingredient = Ingredient.of(this.items.getOrThrow(tagKey));
    }

    public static ElectromagneticDissociationRecipeBuilder result(HolderGetter<Item> items, ItemLike result, ItemLike ingredient) {
        return new ElectromagneticDissociationRecipeBuilder(items, new ItemStackTemplate(result.asItem()), Ingredient.of(ingredient));
    }

    public static ElectromagneticDissociationRecipeBuilder result(HolderGetter<Item> items, ItemLike result, int count, ItemLike ingredient) {
        return new ElectromagneticDissociationRecipeBuilder(items, new ItemStackTemplate(result.asItem(), count), Ingredient.of(ingredient));
    }

    public static ElectromagneticDissociationRecipeBuilder result(HolderGetter<Item> items, ItemLike result, TagKey<Item> tagKey) {
        return new ElectromagneticDissociationRecipeBuilder(items, new ItemStackTemplate(result.asItem()), tagKey);
    }

    public static ElectromagneticDissociationRecipeBuilder result(HolderGetter<Item> items, ItemLike result, int count, TagKey<Item> tagKey) {
        return new ElectromagneticDissociationRecipeBuilder(items, new ItemStackTemplate(result.asItem(), count), tagKey);
    }

    @Override
    public ElectromagneticDissociationRecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public ElectromagneticDissociationRecipeBuilder group(@Nullable String s) {
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
        ElectromagneticDissociationRecipe recipe = new ElectromagneticDissociationRecipe(ingredient, result);
        recipeOutput.accept(resourceKey, recipe, null);
    }
}
