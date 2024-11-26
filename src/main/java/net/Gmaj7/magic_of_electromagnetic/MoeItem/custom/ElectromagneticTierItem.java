package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;
import net.minecraft.world.item.Item;

public class ElectromagneticTierItem extends Item {
    private final ElectromagneticTier tier;
    public ElectromagneticTierItem(ElectromagneticTier tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public ElectromagneticTier getTier() {
        return tier;
    }
}
