package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.FluidPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen.ItemPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.FluidPipeNetFilterPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.ItemPipeNetFilterPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.FluidFilterFakeItem;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class FluidPipeNetHandler extends PipeNetHandler<FluidPipeNetScreen>{
    @Override
    protected ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient) {
        var stack = ingredient.getIngredient(ingredient.getType());
        ItemStack itemStack = ItemStack.EMPTY;
        if(stack.get() instanceof ItemStack) itemStack = (ItemStack) stack.get();
        else if(stack.get() instanceof FluidStack) itemStack = FluidFilterFakeItem.creatFluidFilter((FluidStack)stack.get());
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
            else if (ingredient instanceof FluidStack) itemStack = FluidFilterFakeItem.creatFluidFilter((FluidStack) ingredient);
            if(itemStack.isEmpty() && !itemStack.is(MoeItems.FLUID_FILTER_FAKE_ITEM) && !itemStack.is(MoeItems.FILTER_SETTING) && !(itemStack.getItem() instanceof BucketItem)) return;
            ClientPacketDistributor.sendToServer(new FluidPipeNetFilterPacket(pos, direction, slot, itemStack, screen.getMenu().getNetId()));
        }
    }
}
