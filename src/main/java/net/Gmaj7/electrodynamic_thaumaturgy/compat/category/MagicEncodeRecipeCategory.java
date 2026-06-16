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
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.Recipe.custom.MagicEncodeRecipe;
import net.Gmaj7.electrodynamic_thaumaturgy.compat.JEIRecipeTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class MagicEncodeRecipeCategory implements IRecipeCategory<RecipeHolder<MagicEncodeRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_encode");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/magic_encode_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public MagicEncodeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(EtBlocks.MAGIC_ENCODE_TABLE));
    }

    @Override
    public IRecipeType<RecipeHolder<MagicEncodeRecipe>> getRecipeType() {
        return JEIRecipeTypes.MAGIC_ENCODE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("magic_encode");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MagicEncodeRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 16).add(recipe.value().base());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().code1());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 52).add(recipe.value().code2());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<MagicEncodeRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }
}
