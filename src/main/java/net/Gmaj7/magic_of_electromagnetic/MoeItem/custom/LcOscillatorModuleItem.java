package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;

public class LcOscillatorModuleItem extends ElectromagneticTierItem {
    public LcOscillatorModuleItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getBasicAmount(){
        float amount;
        switch (this.getTier()){
            case IRON -> amount = 2.5F;
            case GOLD -> amount = 5.0F;
            case COPPER -> amount = 7.5F;
            case NETHERITE -> amount = 10.0F;
            default -> amount = 0F;
        }
        return amount;
    }
}
