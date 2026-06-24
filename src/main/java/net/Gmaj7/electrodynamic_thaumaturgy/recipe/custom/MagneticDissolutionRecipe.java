package net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.EtRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public record MagneticDissolutionRecipe(FluidIngredient inputFluid, int fluidCost, Ingredient inputItem, FluidStackTemplate output) implements Recipe<MagneticDissolutionRecipeInput> {
    public static final MapCodec<MagneticDissolutionRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            FluidIngredient.CODEC.fieldOf("fluid").forGetter(MagneticDissolutionRecipe::inputFluid),
            Codec.INT.fieldOf("fluid_cost").forGetter(MagneticDissolutionRecipe::fluidCost),
            Ingredient.CODEC.fieldOf("item").forGetter(MagneticDissolutionRecipe::inputItem),
            FluidStackTemplate.CODEC.fieldOf("output").forGetter(MagneticDissolutionRecipe::output)
    ).apply(i, MagneticDissolutionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MagneticDissolutionRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    FluidIngredient.STREAM_CODEC, MagneticDissolutionRecipe::inputFluid,
                    ByteBufCodecs.INT, MagneticDissolutionRecipe::fluidCost,
                    Ingredient.CONTENTS_STREAM_CODEC, MagneticDissolutionRecipe::inputItem,
                    FluidStackTemplate.STREAM_CODEC, MagneticDissolutionRecipe::output,
                    MagneticDissolutionRecipe::new);
    @Override
    public boolean matches(MagneticDissolutionRecipeInput input, Level level) {
        return this.inputItem.test(input.inputItem()) && this.inputFluid.test(input.inputFluid());
    }

    @Override
    public ItemStack assemble(MagneticDissolutionRecipeInput magneticDissolutionRecipeInput) {
        return ItemStack.EMPTY;
    }
    
    public FluidStack assembleFluid(MagneticDissolutionRecipeInput input){
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
    public RecipeSerializer<? extends Recipe<MagneticDissolutionRecipeInput>> getSerializer() {
        return EtRecipes.MAGNETIC_DISSOLUTION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagneticDissolutionRecipeInput>> getType() {
        return EtRecipes.MAGNETIC_DISSOLUTION_RECIPE_TYPE.get();
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
