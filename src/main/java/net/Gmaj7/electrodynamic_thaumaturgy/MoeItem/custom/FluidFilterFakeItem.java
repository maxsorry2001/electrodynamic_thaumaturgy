package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.function.Consumer;

public class FluidFilterFakeItem extends Item {
    public FluidFilterFakeItem(Properties properties) {
        super(properties);
    }

    public static SimpleFluidContent getFluidFilter(ItemStack itemStack){
        return itemStack.get(MoeDataComponentTypes.FLUID_FILTER);
    }

    public static ItemStack creatFluidFilter(FluidStack fluidStack){
        ItemStack itemStack = new ItemStack(MoeItems.FLUID_FILTER_FAKE_ITEM.get());
        try (Transaction transaction = Transaction.openRoot()){
            itemStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(itemStack)).insert(FluidResource.of(fluidStack), 1, transaction);
            transaction.commit();
        }
        return itemStack;
    }
}
