package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.ElectromagneticLevel;

public class LcOscillatorItem extends ElectromagneticTierItem {
    public LcOscillatorItem(ElectromagneticLevel tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public int getChangeSlot() {
        return 1;
    }

    public float getBasicAmount(){
        float amount;
        switch (this.getTier()){
            case PRIMARY -> amount = 7F;
            case INTERMEDIATE -> amount = 8F;
            case ADVANCED -> amount = 9.5F;
            case SUPERCONDUCTING -> amount = 11F;
            default -> amount = 0F;
        }
        return amount;
    }
}
