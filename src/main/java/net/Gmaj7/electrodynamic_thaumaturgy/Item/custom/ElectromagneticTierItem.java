package net.Gmaj7.electrodynamic_thaumaturgy.Item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Init.ElectromagneticLevel;
import net.minecraft.world.item.Item;

public class ElectromagneticTierItem extends Item implements IEtModuleItem {
    private final ElectromagneticLevel tier;
    public ElectromagneticTierItem(ElectromagneticLevel tier, Properties properties) {
        super(properties);
        this.tier = tier;
    }

    public ElectromagneticLevel getTier() {
        return tier;
    }

    public boolean isEmpty(){
        return tier == ElectromagneticLevel.EMPTY;
    }
}
