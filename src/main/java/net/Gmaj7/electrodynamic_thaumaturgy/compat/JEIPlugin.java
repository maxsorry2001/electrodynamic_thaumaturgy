package net.Gmaj7.electrodynamic_thaumaturgy.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.FluidPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.ItemPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MoeRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.ElectromagneticDissociationRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.ElectromagneticInfusionRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.MagicEncodeRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.MagnetoFusionRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler.FluidPipeNetHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler.ItemPipeNetHandler;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.Collections;
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
        registration.addRecipeCategories(new MagicEncodeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MagnetoFusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ElectromagneticDissociationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ElectromagneticInfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(new ItemStack(MoeItems.FLUID_FILTER_FAKE_ITEM.get())));
        registration.addRecipes(JEIRecipeTypes.MAGIC_ENCODE, this.getRecipes(recipeMap, MoeRecipes.MAGIC_ENCODE_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.MAGNETO_FUSION, this.getRecipes(recipeMap, MoeRecipes.MAGNO_FUSION_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.ELECTROMAGNETIC_DISSOCIATION, this.getRecipes(recipeMap, MoeRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.ELECTROMAGNETIC_INFUSION, this.getRecipes(recipeMap, MoeRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(JEIRecipeTypes.MAGIC_ENCODE, new ItemStack(MoeBlocks.MAGIC_ENCODE_TABLE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.MAGNETO_FUSION, new ItemStack(MoeBlocks.MAGNETO_FUSION_MACHINE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.ELECTROMAGNETIC_DISSOCIATION, new ItemStack(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.ELECTROMAGNETIC_INFUSION, new ItemStack(MoeBlocks.ENERGY_BLOCK.asItem()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(ItemPipeNetScreen.class, new ItemPipeNetHandler());
        registration.addGhostIngredientHandler(FluidPipeNetScreen.class, new FluidPipeNetHandler());
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(MoeRecipes.MAGIC_ENCODE_TYPE.get());
            event.sendRecipes(MoeRecipes.MAGNO_FUSION_TYPE.get());
            event.sendRecipes(MoeRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get());
            event.sendRecipes(MoeRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get());
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
