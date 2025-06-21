package net.Gmaj7.electrofynamic_thaumatury.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeRecipe.MagicLithographyRecipe;
import net.Gmaj7.electrofynamic_thaumatury.MoeRecipe.MoeRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MagicLithographyRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<MagicLithographyRecipe> list = recipeManager.getAllRecipesFor(MoeRecipes.MAGIC_LITHOGRAPHY_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MagicLithographyRecipeCategory.RECIPE_TYPE, list);
    }
}
