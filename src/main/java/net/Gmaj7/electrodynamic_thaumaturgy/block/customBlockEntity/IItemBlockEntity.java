package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public interface IItemBlockEntity {
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandler();

    public void drops();
}
