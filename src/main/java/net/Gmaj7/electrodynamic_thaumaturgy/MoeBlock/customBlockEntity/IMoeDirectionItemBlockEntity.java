package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public interface IMoeDirectionItemBlockEntity {
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction);

    public void drops();
}
