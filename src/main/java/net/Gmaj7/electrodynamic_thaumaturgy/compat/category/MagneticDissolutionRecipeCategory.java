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
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.JEIRecipeTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.MagneticDissolutionRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class MagneticDissolutionRecipeCategory implements IRecipeCategory<RecipeHolder<MagneticDissolutionRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_encode");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/magic_encode_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MagneticDissolutionRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(EtBlocks.MAGNETIC_DISSOLVER_MACHINE));
    }

    @Override
    public IRecipeType<RecipeHolder<MagneticDissolutionRecipe>> getRecipeType() {
        return JEIRecipeTypes.MAGNETIC_DISSOLUTION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("electromagnetic_infusion");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MagneticDissolutionRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 16).add(recipe.value().inputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().inputFluid().fluids().get(0).value(), recipe.value().fluidCost());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.value().output().fluid().value());
    }

    @Override
    public void draw(RecipeHolder<MagneticDissolutionRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }
}
