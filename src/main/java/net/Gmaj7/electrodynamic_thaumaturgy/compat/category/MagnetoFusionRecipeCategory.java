package net.Gmaj7.electrodynamic_thaumaturgy.compat.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagnetoFusionRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.JEIRecipeTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

public class MagnetoFusionRecipeCategory implements IRecipeCategory<RecipeHolder<MagnetoFusionRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_encode");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/magic_encode_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MagnetoFusionRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(EtBlocks.MAGNETO_FUSION_MACHINE));
    }

    @Override
    public IRecipeType<RecipeHolder<MagnetoFusionRecipe>> getRecipeType() {
        return JEIRecipeTypes.MAGNETO_FUSION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("magneto_fusion");
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 85;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MagnetoFusionRecipe> recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.value().ingredients().size(); i++){
            builder.addSlot(RecipeIngredientRole.INPUT, 54, 16 + 18 * i).add(recipe.value().ingredients().get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.value().result());
    }

    @Override
    public void draw(RecipeHolder<MagnetoFusionRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }
}
