package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeRecipes {
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

    public static void register(IEventBus eventBus){
        SERIALIZER.register(eventBus);
        TYPE.register(eventBus);
    }
}
