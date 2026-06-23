package net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.EtRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record MagnetoFusionRecipe(List<Ingredient> ingredients, ItemStackTemplate result) implements Recipe<MagnetoFusionRecipeInput> {
    public static final MapCodec<MagnetoFusionRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            com.mojang.serialization.Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, 3)).fieldOf("ingredients").forGetter(MagnetoFusionRecipe::ingredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MagnetoFusionRecipe::result)
    ).apply(i, MagnetoFusionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MagnetoFusionRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), MagnetoFusionRecipe::ingredients,
                    ItemStackTemplate.STREAM_CODEC, MagnetoFusionRecipe::result,
                    MagnetoFusionRecipe::new
            );
    @Override
    public boolean matches(MagnetoFusionRecipeInput input, Level level) {
        if(level.isClientSide() || !(input.size() == ingredients.size())) {
            return false;
        }
        boolean flag = true;
        for(int i = 0; i < ingredients.size(); i++){
            flag = flag && ingredients.get(i).test(input.getItem(i));
        }
        return flag;
    }

    @Override
    public ItemStack assemble(MagnetoFusionRecipeInput magnetoFusionRecipeInput) {
        return this.result.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagnetoFusionRecipeInput>> getSerializer() {
        return EtRecipes.MAGNO_FUSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagnetoFusionRecipeInput>> getType() {
        return EtRecipes.MAGNO_FUSION_TYPE.get();
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
