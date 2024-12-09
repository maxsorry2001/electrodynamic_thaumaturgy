package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;

public class PowerAmplifierItem extends ElectromagneticTierItem{
    public PowerAmplifierItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getMagnification(){
        float result;
        switch (this.getTier()){
            case IRON -> result = 1.0F;
            case GOLD -> result = 1.5F;
            case COPPER -> result = 2.0F;
            case SUPERCONDUCTING -> result = 2.5F;
            default -> result = 0F;
        }
        return result;
    }
}
