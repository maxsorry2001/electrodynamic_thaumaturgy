package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.ElectromagneticLevel;

public class LcOscillatorModuleItem extends ElectromagneticTierItem {
    public LcOscillatorModuleItem(ElectromagneticLevel tier, Properties properties) {
        super(tier, properties);
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
