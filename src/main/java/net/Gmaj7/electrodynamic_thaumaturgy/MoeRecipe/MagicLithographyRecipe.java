package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record MagicLithographyRecipe(Ingredient inputItem, ItemStackTemplate output) implements Recipe<MagicLithographyRecipeInput> {

    public static final MapCodec<MagicLithographyRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("baseboard").forGetter(MagicLithographyRecipe::inputItem),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MagicLithographyRecipe::output)
    ).apply(i, MagicLithographyRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MagicLithographyRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MagicLithographyRecipe::inputItem,
                    ItemStackTemplate.STREAM_CODEC, MagicLithographyRecipe::output,
                    MagicLithographyRecipe::new);


    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(MagicLithographyRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return inputItem.test(input.getItem(0));
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "magic_lithography";
    }

    @Override
    public ItemStack assemble(MagicLithographyRecipeInput magicLithographyRecipeInput) {
        return this.output.create().copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagicLithographyRecipeInput>> getSerializer() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagicLithographyRecipeInput>> getType() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get();
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    public ItemStack getResultItem(){
        return this.output.create();
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
