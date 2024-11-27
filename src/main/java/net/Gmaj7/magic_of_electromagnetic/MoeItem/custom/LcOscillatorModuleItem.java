package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;

public class LcOscillatorModuleItem extends ElectromagneticTierItem {
    public LcOscillatorModuleItem(ElectromagneticTier tier, Properties properties) {
        super(tier, properties);
    }

    public float getBasicDamage(){
        float damage;
        switch (this.getTier()){
            case IRON -> damage = 2.5F;
            case GOLD -> damage = 5.0F;
            case COPPER -> damage = 7.5F;
            case NETHERITE -> damage = 10.0F;
            default -> damage = 0F;
        }
        return damage;
    }
}
