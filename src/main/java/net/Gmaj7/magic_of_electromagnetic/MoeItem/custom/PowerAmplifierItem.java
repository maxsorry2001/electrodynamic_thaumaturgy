package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;

public class PowerAmplifierItem extends ElectromagneticTierItem{
    public PowerAmplifierItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getMagnification(){
        float result = 1.0F;
        switch (this.getTier()){
            case IRON -> result = 0.8F;
            case GOLD -> result = 1.2F;
            case COPPER -> result = 1.6F;
            case NETHERRITE -> result = 2.0F;
        }
        return result;
    }
}
