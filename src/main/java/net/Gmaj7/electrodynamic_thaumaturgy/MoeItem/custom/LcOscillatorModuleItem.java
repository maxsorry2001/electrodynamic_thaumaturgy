package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.ElectromagneticTier;

public class LcOscillatorModuleItem extends ElectromagneticTierItem {
    public LcOscillatorModuleItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getBasicAmount(){
        float amount;
        switch (this.getTier()){
            case IRON -> amount = 7F;
            case GOLD -> amount = 8F;
            case COPPER -> amount = 9.5F;
            case SUPERCONDUCTING -> amount = 11F;
            default -> amount = 0F;
        }
        return amount;
    }
}
