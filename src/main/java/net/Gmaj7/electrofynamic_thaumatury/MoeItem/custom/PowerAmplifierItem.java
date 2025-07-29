package net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.ElectromagneticTier;

public class PowerAmplifierItem extends ElectromagneticTierItem{
    public PowerAmplifierItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getMagnification(){
        float result;
        switch (this.getTier()){
            case IRON -> result = 1.33F;
            case GOLD -> result = 1.66F;
            case COPPER -> result = 2.0F;
            case SUPERCONDUCTING -> result = 2.33F;
            default -> result = 1.0F;
        }
        return result;
    }
}
