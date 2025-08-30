package net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe;

import net.Gmaj7.electrodynamic_thaumaturgy.MagicOfElectromagnetic;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MagicOfElectromagnetic.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPE =
            DeferredRegister.create(Registries.RECIPE_TYPE, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagicLithographyRecipe>> MAGIC_LITHOGRAPHY_SERIALIZER =
            SERIALIZER.register("magic_lithography", MagicLithographyRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagicLithographyRecipe>> MAGIC_LITHOGRAPHY_TYPE =
            TYPE.register("magic_lithography", () -> new RecipeType<MagicLithographyRecipe>() {
                @Override
                public String toString() {
                    return "magic_lithography";
                }
            });

    public static void register(IEventBus eventBus){
        SERIALIZER.register(eventBus);
        TYPE.register(eventBus);
    }
}
