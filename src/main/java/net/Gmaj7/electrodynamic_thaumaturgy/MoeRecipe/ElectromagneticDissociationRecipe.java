package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ElectromagneticDissociationRecipe(Ingredient ingredient, ItemStackTemplate output) implements Recipe<ElectromagneticDissociationRecipeInput> {
    public static final MapCodec<ElectromagneticDissociationRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ElectromagneticDissociationRecipe::ingredient),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(ElectromagneticDissociationRecipe::output)
    ).apply(i, ElectromagneticDissociationRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectromagneticDissociationRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, ElectromagneticDissociationRecipe::ingredient,
                    ItemStackTemplate.STREAM_CODEC, ElectromagneticDissociationRecipe::output,
                    ElectromagneticDissociationRecipe::new);
    @Override
    public boolean matches(ElectromagneticDissociationRecipeInput recipeInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return ingredient.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(ElectromagneticDissociationRecipeInput recipeInput) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "misc";
    }

    @Override
    public RecipeSerializer<? extends Recipe<ElectromagneticDissociationRecipeInput>> getSerializer() {
        return MoeRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<ElectromagneticDissociationRecipeInput>> getType() {
        return MoeRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
