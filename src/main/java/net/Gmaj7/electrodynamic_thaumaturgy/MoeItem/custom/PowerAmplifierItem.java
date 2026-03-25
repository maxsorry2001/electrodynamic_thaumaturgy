package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.ElectromagneticLevel;

public class PowerAmplifierItem extends ElectromagneticTierItem{
    public PowerAmplifierItem(ElectromagneticLevel tier, Properties properties) {
        super(tier, properties);
    }

    public float getMagnification(){
        float result;
        switch (this.getTier()){
            case PRIMARY -> result = 1.33F;
            case INTERMEDIATE -> result = 1.66F;
            case ADVANCED -> result = 2.0F;
            case SUPERCONDUCTING -> result = 2.5F;
            default -> result = 1.0F;
        }
        return result;
    }
}
