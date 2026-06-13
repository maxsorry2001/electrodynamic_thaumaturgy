package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class FluidFilterFakeItem extends Item {
    public FluidFilterFakeItem(Properties properties) {
        super(properties);
    }

    public static FluidResource getFluidFilter(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.FLUID_FILTER);
    }

    public static ItemStack creatFluidFilter(FluidResource fluidResource){
        ItemStack itemStack = new ItemStack(MoeItems.FLUID_FILTER_FAKE_ITEM.get());
        itemStack.set(MoeDataComponentTypes.FLUID_FILTER, fluidResource);
        return itemStack;
    }
}
