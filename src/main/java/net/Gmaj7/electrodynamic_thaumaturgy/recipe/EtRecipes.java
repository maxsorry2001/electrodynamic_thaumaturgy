package net.Gmaj7.electrodynamic_thaumaturgy.recipe;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EtRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ElectrodynamicThaumaturgy.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE =
            DeferredRegister.create(Registries.RECIPE_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagicEncodeRecipe>> MAGIC_ENCODE_SERIALIZER =
            SERIALIZER.register("magic_encode", () -> new RecipeSerializer<>(MagicEncodeRecipe.CODEC, MagicEncodeRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagicEncodeRecipe>> MAGIC_ENCODE_TYPE =
            TYPE.register("magic_encode", () -> new RecipeType<MagicEncodeRecipe>() {
                @Override
                public String toString() {
                    return "magic_encode";
                }
            });
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagnetoFusionRecipe>> MAGNO_FUSION_SERIALIZER =
            SERIALIZER.register("magno_fusion", () -> new RecipeSerializer<>(MagnetoFusionRecipe.CODEC, MagnetoFusionRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagnetoFusionRecipe>> MAGNO_FUSION_TYPE =
            TYPE.register("magno_fusion", () -> new RecipeType<MagnetoFusionRecipe>() {
                @Override
                public String toString() {
                    return "magno_fusion";
                }
            });
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElectromagneticDissociationRecipe>> ELECTROMAGNETIC_DISSOCIATION_RECIPE_SERIALIZER =
            SERIALIZER.register("electromagnetic_dissociation", () -> new RecipeSerializer<>(ElectromagneticDissociationRecipe.CODEC, ElectromagneticDissociationRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectromagneticDissociationRecipe>> ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE =
            TYPE.register("electromagnetic_dissociation", () -> new RecipeType<ElectromagneticDissociationRecipe>() {
                @Override
                public String toString() {return "electromagnetic_dissociation";}
            });
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElectromagneticInfusionRecipe>> ELECTROMAGNETIC_INFUSION_RECIPE_SERIALIZER =
            SERIALIZER.register("electromagnetic_infusion", () -> new RecipeSerializer<>(ElectromagneticInfusionRecipe.CODEC, ElectromagneticInfusionRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElectromagneticInfusionRecipe>> ELECTROMAGNETIC_INFUSION_RECIPE_TYPE =
            TYPE.register("electromagnetic_infusion", () -> new RecipeType<ElectromagneticInfusionRecipe>() {
                @Override
                public String toString() {
                    return "electromagnetic_infusion";
                }
            });
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagneticDissolutionRecipe>> MAGNETIC_DISSOLUTION_RECIPE_SERIALIZER =
            SERIALIZER.register("magnetic_dissolution", () -> new RecipeSerializer<>(MagneticDissolutionRecipe.CODEC, MagneticDissolutionRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagneticDissolutionRecipe>> MAGNETIC_DISSOLUTION_RECIPE_TYPE =
            TYPE.register("magnetic_dissolution", () -> new RecipeType<MagneticDissolutionRecipe>() {
                @Override
                public String toString() {
                    return "magnetic_dissolution";
                }
            });

    public static void register(IEventBus eventBus){
        SERIALIZER.register(eventBus);
        TYPE.register(eventBus);
    }
}
