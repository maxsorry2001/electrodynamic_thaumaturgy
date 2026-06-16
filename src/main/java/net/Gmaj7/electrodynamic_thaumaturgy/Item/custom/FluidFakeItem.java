package net.Gmaj7.electrodynamic_thaumaturgy.Item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class FluidFakeItem extends Item {
    public FluidFakeItem(Properties properties) {
        super(properties);
    }

    public static SimpleFluidContent getFluidFilter(ItemStack itemStack){
        return itemStack.get(EtDataComponentTypes.FLUID_FILTER);
    }

    public static ItemStack creatFluidFilter(FluidStack fluidStack){
        ItemStack itemStack = new ItemStack(EtItems.FLUID_FAKE_ITEM.get());
        try (Transaction transaction = Transaction.openRoot()){
            itemStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(itemStack)).insert(FluidResource.of(fluidStack), 1, transaction);
            transaction.commit();
        }
        return itemStack;
    }
}
