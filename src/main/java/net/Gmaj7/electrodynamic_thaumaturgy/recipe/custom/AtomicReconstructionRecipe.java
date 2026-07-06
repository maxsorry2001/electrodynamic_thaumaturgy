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

public record AtomicReconstructionRecipe (Ingredient input, int itemCost, int energyUse, int tickUse, ItemStackTemplate output) implements Recipe<AtomicReconstructionRecipeInput> {
    public static final MapCodec<AtomicReconstructionRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("input").forGetter(AtomicReconstructionRecipe::input),
            Codec.INT.fieldOf("item_cost").forGetter(AtomicReconstructionRecipe::itemCost),
            Codec.INT.fieldOf("energy_use").forGetter(AtomicReconstructionRecipe::energyUse),
            Codec.INT.fieldOf("tick_use").forGetter(AtomicReconstructionRecipe::tickUse),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(AtomicReconstructionRecipe::output)
    ).apply(i, AtomicReconstructionRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AtomicReconstructionRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, AtomicReconstructionRecipe::input,
                    ByteBufCodecs.INT, AtomicReconstructionRecipe::itemCost,
                    ByteBufCodecs.INT, AtomicReconstructionRecipe::energyUse,
                    ByteBufCodecs.INT, AtomicReconstructionRecipe::tickUse,
                    ItemStackTemplate.STREAM_CODEC, AtomicReconstructionRecipe::output,
                    AtomicReconstructionRecipe::new);
    @Override
    public boolean matches(AtomicReconstructionRecipeInput atomicReconstructionRecipeInput, Level level) {
        return input.test(atomicReconstructionRecipeInput.input());
    }

    @Override
    public ItemStack assemble(AtomicReconstructionRecipeInput atomicReconstructionRecipeInput) {
        return output.create();
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
    public RecipeSerializer<? extends Recipe<AtomicReconstructionRecipeInput>> getSerializer() {
        return EtRecipes.ATOMIC_RECONSTRUCTION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<AtomicReconstructionRecipeInput>> getType() {
        return EtRecipes.ATOMIC_RECONSTRUCTION_RECIPE_TYPE.get();
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
