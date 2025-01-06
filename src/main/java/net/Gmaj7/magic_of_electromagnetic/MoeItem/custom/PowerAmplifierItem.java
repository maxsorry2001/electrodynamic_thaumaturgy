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
            case GOLD -> result = 1.33F;
            case COPPER -> result = 1.66F;
            case SUPERCONDUCTING -> result = 2.0F;
            default -> result = 0F;
        }
        return result;
    }
}
