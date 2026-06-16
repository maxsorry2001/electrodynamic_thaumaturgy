package net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record ElectromagneticDissociationRecipe(Ingredient ingredient, List<ItemStackTemplate> outputs, boolean needCatalyst) implements Recipe<ElectromagneticDissociationRecipeInput> {
    public static final MapCodec<ElectromagneticDissociationRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ElectromagneticDissociationRecipe::ingredient),
            Codec.list(ItemStackTemplate.CODEC).fieldOf("outputs").forGetter(ElectromagneticDissociationRecipe::outputs),
            Codec.BOOL.fieldOf("need_catalyst").forGetter(ElectromagneticDissociationRecipe::needCatalyst)
    ).apply(i, ElectromagneticDissociationRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectromagneticDissociationRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, ElectromagneticDissociationRecipe::ingredient,
                    ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()), ElectromagneticDissociationRecipe::outputs,
                    ByteBufCodecs.BOOL, ElectromagneticDissociationRecipe::needCatalyst,
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
        return outputs.get(0).create().copy();
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
        return EtRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<ElectromagneticDissociationRecipeInput>> getType() {
        return EtRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get();
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
