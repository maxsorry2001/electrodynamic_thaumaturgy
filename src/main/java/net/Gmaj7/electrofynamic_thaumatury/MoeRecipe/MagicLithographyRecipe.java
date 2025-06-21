package net.Gmaj7.electrofynamic_thaumatury.MoeRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record MagicLithographyRecipe(Ingredient inputItem, ItemStack output) implements Recipe<MagicLithographyRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(MagicLithographyRecipeInput magicLithographyRecipeInput, Level level) {
        return inputItem.test(magicLithographyRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(MagicLithographyRecipeInput magicLithographyRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MagicLithographyRecipe>{
        public static final MapCodec<MagicLithographyRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("baseboard").forGetter(MagicLithographyRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(MagicLithographyRecipe::output)
        ).apply(i, MagicLithographyRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MagicLithographyRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, MagicLithographyRecipe::inputItem,
                        ItemStack.STREAM_CODEC, MagicLithographyRecipe::output,
                        MagicLithographyRecipe::new);

        @Override
        public MapCodec<MagicLithographyRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MagicLithographyRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
