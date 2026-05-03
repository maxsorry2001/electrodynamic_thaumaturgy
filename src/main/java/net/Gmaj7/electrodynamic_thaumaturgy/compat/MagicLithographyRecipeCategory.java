package net.Gmaj7.electrodynamic_thaumaturgy.compat;

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
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeRecipe.MagicLithographyRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class MagicLithographyRecipeCategory implements IRecipeCategory<RecipeHolder<MagicLithographyRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_lithography");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/magic_lithography_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MagicLithographyRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE));
    }

    @Override
    public IRecipeType<RecipeHolder<MagicLithographyRecipe>> getRecipeType() {
        return JEIRecipeTypes.MAGIC_LITHOGRAPHY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("magic_lithography");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MagicLithographyRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().inputItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<MagicLithographyRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        guiGraphics.fillGradient(156, 50, 164, 56, 0xffb51500, 0xff600b00);
    }
}
