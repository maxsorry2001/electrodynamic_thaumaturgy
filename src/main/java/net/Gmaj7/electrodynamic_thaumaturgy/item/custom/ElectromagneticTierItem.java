package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.ElectromagneticLevel;
import net.minecraft.world.item.Item;

public abstract class ElectromagneticTierItem extends Item implements IEtModuleItem {
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

    protected abstract int getChangeSlot();
}
