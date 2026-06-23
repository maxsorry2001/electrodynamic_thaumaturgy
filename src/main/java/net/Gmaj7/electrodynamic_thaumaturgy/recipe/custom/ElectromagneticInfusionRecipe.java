package net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom;

import com.mojang.serialization.Codec;
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
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public record ElectromagneticInfusionRecipe(Ingredient inputItem, FluidIngredient inputFluid, int fluidCost, ItemStackTemplate output) implements Recipe<ElectromagneticInfusionRecipeInput> {
    public static final MapCodec<ElectromagneticInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("item").forGetter(ElectromagneticInfusionRecipe::inputItem),
            FluidIngredient.CODEC.fieldOf("fluid").forGetter(ElectromagneticInfusionRecipe::inputFluid),
            Codec.INT.fieldOf("fluid_cost").forGetter(ElectromagneticInfusionRecipe::fluidCost),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(ElectromagneticInfusionRecipe::output)
    ).apply(i, ElectromagneticInfusionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ElectromagneticInfusionRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, ElectromagneticInfusionRecipe::inputItem,
                    FluidIngredient.STREAM_CODEC, ElectromagneticInfusionRecipe::inputFluid,
                    ByteBufCodecs.INT, ElectromagneticInfusionRecipe::fluidCost,
                    ItemStackTemplate.STREAM_CODEC, ElectromagneticInfusionRecipe::output,
                    ElectromagneticInfusionRecipe::new);
    @Override
    public boolean matches(ElectromagneticInfusionRecipeInput input, Level level) {
        return this.inputItem.test(input.inputItem()) && this.inputFluid.test(input.inputFluid());
    }

    @Override
    public ItemStack assemble(ElectromagneticInfusionRecipeInput electromagneticInfusionRecipeInput) {
        return output.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "misc";
    }

    @Override
    public RecipeSerializer<? extends Recipe<ElectromagneticInfusionRecipeInput>> getSerializer() {
        return EtRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<ElectromagneticInfusionRecipeInput>> getType() {
        return EtRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get();
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
