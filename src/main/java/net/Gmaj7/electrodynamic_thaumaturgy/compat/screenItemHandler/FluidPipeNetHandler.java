package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.screen.FluidPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.FluidPipeNetFilterPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.FluidFakeItem;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidPipeNetHandler extends PipeNetHandler<FluidPipeNetScreen>{
    @Override
    protected ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient) {
        var stack = ingredient.getIngredient(ingredient.getType());
        ItemStack itemStack = ItemStack.EMPTY;
        if(stack.get() instanceof ItemStack) itemStack = (ItemStack) stack.get();
        else if(stack.get() instanceof FluidStack) itemStack = FluidFakeItem.creatFluidFilter((FluidStack)stack.get());
        return itemStack;
    }

    @Override
    protected <I> TargetFilter<I, FluidPipeNetScreen> getTarget(BlockPos currentPos, Direction dir, Rect2i area, int finalSlot, FluidPipeNetScreen gui) {
        return new FluidPipeNetFilterTarget(currentPos, dir, area, finalSlot, gui);
    }

    protected static class FluidPipeNetFilterTarget<T extends MutableDataComponentHolder> extends TargetFilter<T, FluidPipeNetScreen>{

        FluidPipeNetFilterTarget(BlockPos pos, Direction direction, Rect2i area, int slot, FluidPipeNetScreen screen) {
            super(pos, direction, area, slot, screen);
        }

        @Override
        public void accept(T ingredient) {
            ItemStack itemStack = ItemStack.EMPTY;
            if(ingredient instanceof ItemStack) itemStack = (ItemStack) ingredient;
            else if (ingredient instanceof FluidStack) itemStack = FluidFakeItem.creatFluidFilter((FluidStack) ingredient);
            if(itemStack.isEmpty() && !itemStack.is(EtItems.FLUID_FAKE_ITEM) && !itemStack.is(EtItems.FILTER_SETTING) && !(itemStack.getItem() instanceof BucketItem)) return;
            ClientPacketDistributor.sendToServer(new FluidPipeNetFilterPacket(pos, direction, slot, itemStack, screen.getMenu().getNetId()));
        }
    }
}
