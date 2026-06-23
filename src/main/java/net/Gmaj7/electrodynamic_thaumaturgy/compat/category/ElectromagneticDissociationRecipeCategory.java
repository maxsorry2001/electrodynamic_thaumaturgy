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
import net.Gmaj7.electrodynamic_thaumaturgy.recipe.custom.ElectromagneticDissociationRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ElectromagneticDissociationRecipeCategory implements IRecipeCategory<RecipeHolder<ElectromagneticDissociationRecipe>> {
    public static final Identifier UID = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magic_encode");
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_dissociation_gui.png");
    private final IDrawable background;
    private final IDrawable icon;

    public ElectromagneticDissociationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE));
    }

    @Override
    public IRecipeType<RecipeHolder<ElectromagneticDissociationRecipe>> getRecipeType() {
        return JEIRecipeTypes.ELECTROMAGNETIC_DISSOCIATION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("electromagnetic_dissociation");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ElectromagneticDissociationRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().ingredient());
        List<ItemStackTemplate> outputs = recipe.value().outputs();
        if(recipe.value().needCatalyst()){
            builder.addSlot(RecipeIngredientRole.INPUT, 81, 20).add(Ingredient.of(Items.NETHER_STAR));
        }
        for (int i = 0; i < outputs.size(); i++){
            builder.addSlot(RecipeIngredientRole.OUTPUT, 104 + 18 * i, 34).add(outputs.get(i));
        }
    }

    @Override
    public void draw(RecipeHolder<ElectromagneticDissociationRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }
}
