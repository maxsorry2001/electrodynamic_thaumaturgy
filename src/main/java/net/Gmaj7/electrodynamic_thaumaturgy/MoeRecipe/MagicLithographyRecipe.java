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

public class MagicLithographyRecipe implements Recipe<MagicLithographyRecipeInput> {

    private final Recipe.CommonInfo commonInfo;
    private final MagicLithographyRecipe.BlockBookInfo blockBookInfo;
    private final Ingredient inputItem;
    private final ItemStackTemplate output;

    public static final MapCodec<MagicLithographyRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            CommonInfo.MAP_CODEC.forGetter(magicLithographyRecipe -> magicLithographyRecipe.commonInfo),
            BlockBookInfo.CODEC.forGetter(magicLithographyRecipe -> magicLithographyRecipe.blockBookInfo),
            Ingredient.CODEC.fieldOf("baseboard").forGetter(MagicLithographyRecipe::getInputItem),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MagicLithographyRecipe::getOutput)
    ).apply(i, MagicLithographyRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MagicLithographyRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    CommonInfo.STREAM_CODEC, magicLithographyRecipe -> magicLithographyRecipe.commonInfo,
                    BlockBookInfo.STREAM_CODEC, magicLithographyRecipe -> magicLithographyRecipe.blockBookInfo,
                    Ingredient.CONTENTS_STREAM_CODEC, MagicLithographyRecipe::getInputItem,
                    ItemStackTemplate.STREAM_CODEC, MagicLithographyRecipe::getOutput,
                    MagicLithographyRecipe::new);

    public MagicLithographyRecipe(CommonInfo commonInfo, BlockBookInfo blockBookInfo, Ingredient inputItem, ItemStackTemplate output) {
        this.commonInfo = commonInfo;
        this.blockBookInfo = blockBookInfo;
        this.inputItem = inputItem;
        this.output = output;
    }


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
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return blockBookInfo.group();
    }

    @Override
    public ItemStack assemble(MagicLithographyRecipeInput magicLithographyRecipeInput) {
        return this.output.create();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagicLithographyRecipeInput>> getSerializer() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagicLithographyRecipeInput>> getType() {
        return MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get();
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public ItemStackTemplate getOutput() {
        return output;
    }

    public ItemStack getResultItem(){
        return this.output.create();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(inputItem);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public record BlockBookInfo(CraftingBookCategory craftingBookCategory, String group) implements Recipe.BookInfo<CraftingBookCategory>{
        public static final MapCodec<BlockBookInfo> CODEC = Recipe.BookInfo.mapCodec(
                CraftingBookCategory.CODEC, CraftingBookCategory.MISC, BlockBookInfo::new
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, BlockBookInfo> STREAM_CODEC = Recipe.BookInfo.streamCodec(
                CraftingBookCategory.STREAM_CODEC, BlockBookInfo::new
        );

        @Override
        public CraftingBookCategory category() {
            return craftingBookCategory;
        }
    }
}
