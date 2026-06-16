package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public interface IDirectionItemBlockEntity {
    StacksResourceHandler<ItemStack, ItemResource> getItemHandlerWithDirection(Direction direction);

    void drops();

    void changeDirectionSet(Direction direction);
}
