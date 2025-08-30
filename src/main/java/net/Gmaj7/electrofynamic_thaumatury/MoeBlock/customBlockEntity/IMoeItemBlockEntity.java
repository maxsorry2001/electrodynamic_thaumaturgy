package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.neoforged.neoforge.items.IItemHandler;

public interface IMoeItemBlockEntity {
    public IItemHandler getItemHandler();

    public void drops();
}
