package net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeReciprBuilder;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicEncodeRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MagicEncodeRecipeBuilder implements RecipeBuilder {
    private final ItemStackTemplate result;
    private final Ingredient baseInput;
    private final Ingredient code1Input;
    private final Ingredient code2Input;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private MagicEncodeRecipeBuilder(Ingredient base, Ingredient code1, Ingredient code2, ItemStackTemplate result) {
        this.result = result;
        this.baseInput = base;
        this.code1Input = code1;
        this.code2Input = code2;
    }

    public static MagicEncodeRecipeBuilder magicEncode(ItemLike base, ItemLike code1, ItemLike code2, ItemLike result) {
        return new MagicEncodeRecipeBuilder(Ingredient.of(base), Ingredient.of(code1), Ingredient.of(code2), new ItemStackTemplate(result.asItem()));
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
        MagicEncodeRecipe recipe = new MagicEncodeRecipe(baseInput, code1Input, code2Input, result);
        recipeOutput.accept(resourceKey, recipe, null);
    }
}
