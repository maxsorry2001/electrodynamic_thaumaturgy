package net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.ElectromagneticTier;
import net.minecraft.world.item.Item;

public class ElectromagneticTierItem extends Item implements IMoeModuleItem {
    private final ElectromagneticTier tier;
    public ElectromagneticTierItem(ElectromagneticTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public ElectromagneticTier getTier() {
        return tier;
    }

    public boolean isEmpty(){
        return tier == ElectromagneticTier.EMPTY;
    }
}
