package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.items.IItemHandler;

public interface IMoeDirectionItemBlockEntity {
    public IItemHandler getItemHandlerWithDirection(Direction direction);

    public void drops();
}
