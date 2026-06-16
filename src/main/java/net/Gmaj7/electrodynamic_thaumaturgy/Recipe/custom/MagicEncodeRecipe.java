package net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record MagicEncodeRecipe(Ingredient base, Ingredient code1, Ingredient code2, ItemStackTemplate output) implements Recipe<MagicEncodeRecipeInput> {

    public static final MapCodec<MagicEncodeRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("base").forGetter(MagicEncodeRecipe::base),
            Ingredient.CODEC.fieldOf("code1").forGetter(MagicEncodeRecipe::code1),
            Ingredient.CODEC.fieldOf("code2").forGetter(MagicEncodeRecipe::code2),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MagicEncodeRecipe::output)
    ).apply(i, MagicEncodeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MagicEncodeRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MagicEncodeRecipe::base,
                    Ingredient.CONTENTS_STREAM_CODEC, MagicEncodeRecipe::code1,
                    Ingredient.CONTENTS_STREAM_CODEC, MagicEncodeRecipe::code2,
                    ItemStackTemplate.STREAM_CODEC, MagicEncodeRecipe::output,
                    MagicEncodeRecipe::new);


    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(base);
        list.add(code1);
        list.add(code2);
        return list;
    }

    @Override
    public boolean matches(MagicEncodeRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }
        return base.test(input.getItem(0)) && code1().test(input.getItem(1)) && code2.test(input.getItem(2));
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "magic_encode";
    }

    @Override
    public ItemStack assemble(MagicEncodeRecipeInput magicEncodeRecipeInput) {
        return this.output.create().copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagicEncodeRecipeInput>> getSerializer() {
        return EtRecipes.MAGIC_ENCODE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagicEncodeRecipeInput>> getType() {
        return EtRecipes.MAGIC_ENCODE_TYPE.get();
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
