package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.PipeNetScreen;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public abstract class PipeNetHandler<T extends PipeNetScreen<?>> implements IGhostIngredientHandler<T> {

    @Override
    public <I> List<Target<I>> getTargetsTyped(T gui, ITypedIngredient<I> ingredient, boolean doStart) {
        ItemStack itemStack = wrapDraggedItem(ingredient);
        if(doStart) System.out.println(1);
        return List.of();
    }

    @Nullable
    private static ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient) {
        return VanillaTypes.ITEM_STACK.castIngredient(ingredient)
                .orElse(null);
    }
}