package net.Gmaj7.electrodynamic_thaumaturgy.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.FilterSettingScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.FluidPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.ItemPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.EtRecipes;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.ElectromagneticDissociationRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.ElectromagneticInfusionRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.MagicEncodeRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.category.MagnetoFusionRecipeCategory;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler.FilterSettingHandler;
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
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(new ItemStack(EtItems.FLUID_FAKE_ITEM.get())));
        registration.addRecipes(JEIRecipeTypes.MAGIC_ENCODE, this.getRecipes(recipeMap, EtRecipes.MAGIC_ENCODE_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.MAGNETO_FUSION, this.getRecipes(recipeMap, EtRecipes.MAGNO_FUSION_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.ELECTROMAGNETIC_DISSOCIATION, this.getRecipes(recipeMap, EtRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get()));
        registration.addRecipes(JEIRecipeTypes.ELECTROMAGNETIC_INFUSION, this.getRecipes(recipeMap, EtRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(JEIRecipeTypes.MAGIC_ENCODE, new ItemStack(EtBlocks.MAGIC_ENCODE_TABLE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.MAGNETO_FUSION, new ItemStack(EtBlocks.MAGNETO_FUSION_MACHINE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.ELECTROMAGNETIC_DISSOCIATION, new ItemStack(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.asItem()));
        registration.addCraftingStation(JEIRecipeTypes.ELECTROMAGNETIC_INFUSION, new ItemStack(EtBlocks.ENERGY_BLOCK.asItem()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGhostIngredientHandler(ItemPipeNetScreen.class, new ItemPipeNetHandler());
        registration.addGhostIngredientHandler(FluidPipeNetScreen.class, new FluidPipeNetHandler());
        registration.addGhostIngredientHandler(FilterSettingScreen.class, new FilterSettingHandler());
    }

    @EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(EtRecipes.MAGIC_ENCODE_TYPE.get());
            event.sendRecipes(EtRecipes.MAGNO_FUSION_TYPE.get());
            event.sendRecipes(EtRecipes.ELECTROMAGNETIC_DISSOCIATION_RECIPE_TYPE.get());
            event.sendRecipes(EtRecipes.ELECTROMAGNETIC_INFUSION_RECIPE_TYPE.get());
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
