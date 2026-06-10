package net.Gmaj7.electrodynamic_thaumaturgy.compat;

import mezz.jei.api.recipe.types.IRecipeType;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticDissociationRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.ElectromagneticInfusionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicEncodeRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagnetoFusionRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class JEIRecipeTypes {
    public static final IRecipeType<RecipeHolder<MagicEncodeRecipe>> MAGIC_ENCODE =
            create(ElectrodynamicThaumaturgy.MODID, "magic_encode", MagicEncodeRecipe.class);
    public static final IRecipeType<RecipeHolder<MagnetoFusionRecipe>> MAGNETO_FUSION =
            create(ElectrodynamicThaumaturgy.MODID, "magneto_fusion", MagnetoFusionRecipe.class);
    public static final IRecipeType<RecipeHolder<ElectromagneticDissociationRecipe>> ELECTROMAGNETIC_DISSOCIATION =
            create(ElectrodynamicThaumaturgy.MODID, "electromagnetic_dissociation", ElectromagneticDissociationRecipe.class);
    public static final IRecipeType<RecipeHolder<ElectromagneticInfusionRecipe>> ELECTROMAGNETIC_INFUSION =
            create(ElectrodynamicThaumaturgy.MODID, "electromagnetic_infusion", ElectromagneticInfusionRecipe.class);


    public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> create(String modid, String name, Class<? extends R> recipeClass) {
        Identifier uid = Identifier.fromNamespaceAndPath(modid, name);
        @SuppressWarnings({"unchecked", "RedundantCast"})
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return IRecipeType.create(uid, holderClass);
    }
}
