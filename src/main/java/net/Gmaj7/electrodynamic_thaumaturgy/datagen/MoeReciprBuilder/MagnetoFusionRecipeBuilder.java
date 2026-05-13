package net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeReciprBuilder;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicEncodeRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagnetoFusionRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
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
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private MagnetoFusionRecipeBuilder(ItemStackTemplate result, List<Ingredient> ingredients) {
        this.result = result;
        this.ingredients = ingredients;
    }

    public static MagnetoFusionRecipeBuilder magnetoFusion(ItemLike result, ItemLike... input) {
        List<Ingredient> list = new ArrayList<>();
        for (int i = 0; i < Math.min(3, input.length); i++){
            list.add(Ingredient.of(input[i]));
        }
        return new MagnetoFusionRecipeBuilder(new ItemStackTemplate(result.asItem()), list);
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        this.criteria.put(s, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
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
