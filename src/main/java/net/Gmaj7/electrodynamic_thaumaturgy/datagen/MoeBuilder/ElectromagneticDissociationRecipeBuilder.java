package net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticDissociationRecipe;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ElectromagneticDissociationRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final List<ItemStackTemplate> result;
    private final Ingredient ingredient;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final boolean needStar;
    private String group;

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, Ingredient ingredient) {
        this(items, ingredient, false);
    }

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, Ingredient ingredient, boolean needStar) {
        this.items = items;
        this.result = new ArrayList<>();
        this.ingredient = ingredient;
        this.needStar = needStar;
    }

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, TagKey<Item> tagKey) {
        this(items, tagKey, false);
    }

    private ElectromagneticDissociationRecipeBuilder(HolderGetter<Item> items, TagKey<Item> tagKey, boolean needStar) {
        this.items = items;
        this.result = new ArrayList<>();
        this.ingredient = Ingredient.of(this.items.getOrThrow(tagKey));
        this.needStar = needStar;
    }

    public static ElectromagneticDissociationRecipeBuilder creat(HolderGetter<Item> items, ItemLike ingredient){
        return new ElectromagneticDissociationRecipeBuilder(items, Ingredient.of(ingredient));
    }

    public static ElectromagneticDissociationRecipeBuilder creat(HolderGetter<Item> items, TagKey<Item> tagKey){
        return new ElectromagneticDissociationRecipeBuilder(items, tagKey);
    }

    public static ElectromagneticDissociationRecipeBuilder creat(HolderGetter<Item> items, ItemLike ingredient, boolean needStar){
        return new ElectromagneticDissociationRecipeBuilder(items, Ingredient.of(ingredient), needStar);
    }

    public static ElectromagneticDissociationRecipeBuilder creat(HolderGetter<Item> items, TagKey<Item> tagKey, boolean needStar){
        return new ElectromagneticDissociationRecipeBuilder(items, tagKey, needStar);
    }

    public ElectromagneticDissociationRecipeBuilder result(ItemLike result) {
        if(this.result.size() <= 2) this.result.add(new ItemStackTemplate(result.asItem()));
        return this;
    }

    public ElectromagneticDissociationRecipeBuilder result(ItemLike result, int count) {
        if(this.result.size() <= 2) this.result.add(new ItemStackTemplate(result.asItem(), count));
        return this;
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

    public List<ItemStackTemplate> getResult() {
        return result;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(result.get(0));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        ElectromagneticDissociationRecipe recipe = new ElectromagneticDissociationRecipe(ingredient, result, needStar);
        recipeOutput.accept(resourceKey, recipe, null);
    }

    public void saveWithAddition(RecipeOutput recipeOutput, String nameAddition){
        ElectromagneticDissociationRecipe recipe = new ElectromagneticDissociationRecipe(ingredient, result, needStar);
        recipeOutput.accept(ResourceKey.create(Registries.RECIPE, Identifier.parse(result.get(0).item().getRegisteredName() + "_dissociation_" + nameAddition)), recipe, null);
    }

    public void saveWithName(RecipeOutput recipeOutput, String name){
        ElectromagneticDissociationRecipe recipe = new ElectromagneticDissociationRecipe(ingredient, result, needStar);
        recipeOutput.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name)), recipe, null);
    }
}
