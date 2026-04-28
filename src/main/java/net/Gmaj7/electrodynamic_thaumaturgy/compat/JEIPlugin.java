package net.Gmaj7.electrodynamic_thaumaturgy.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicLithographyRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MoeRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static RecipeMap recipeMap = RecipeMap.EMPTY;
    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "jei_plugin");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MagicLithographyRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(JEIRecipeTypes.MAGIC_LITHOGRAPHY, this.getRecipes(recipeMap, MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(JEIRecipeTypes.MAGIC_LITHOGRAPHY, new ItemStack(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.asItem()));
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get());
        }
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipeReceived(RecipesReceivedEvent event) {
            recipeMap = event.getRecipeMap();
        }
    }
}
