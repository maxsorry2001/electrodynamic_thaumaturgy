package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.screen.ItemPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.ItemPipeNetFilterPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.FluidFakeItem;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemPipeNetHandler extends PipeNetHandler<ItemPipeNetScreen>{

    @Override
    protected ItemPipeNetFilterTarget getTarget(BlockPos currentPos, Direction dir, Rect2i area, int finalSlot, ItemPipeNetScreen gui) {
        return new ItemPipeNetFilterTarget(currentPos, dir, area, finalSlot, gui);
    }

    @Override
    protected ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient) {
        var stack = ingredient.getIngredient(ingredient.getType());
        ItemStack itemStack = ItemStack.EMPTY;
        if(stack.get() instanceof ItemStack) itemStack = (ItemStack) stack.get();
        else if(stack.get() instanceof FluidStack) {
            itemStack = FluidFakeItem.creatFluidFilter((FluidStack) stack.get());
        }
        return itemStack;
    }

    protected static class ItemPipeNetFilterTarget<T extends MutableDataComponentHolder> extends TargetFilter<T,ItemPipeNetScreen>{

        ItemPipeNetFilterTarget(BlockPos pos, Direction direction, Rect2i area, int slot, ItemPipeNetScreen screen) {
            super(pos, direction, area, slot, screen);
        }

        @Override
        public void accept(T ingredient) {
            ItemStack itemStack = ItemStack.EMPTY;
            if(ingredient instanceof ItemStack) itemStack = (ItemStack) ingredient;
            if(itemStack.isEmpty()) return;
            ClientPacketDistributor.sendToServer(new ItemPipeNetFilterPacket(pos, direction, slot, itemStack, screen.getMenu().getNetId()));
        }
    }
}
