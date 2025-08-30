package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.EnhancementData;
import net.minecraft.world.item.Item;

public class EnhancementModulateItem extends Item{
    private final EnhancementData.EnhancementType enhancementType;
    public EnhancementModulateItem(EnhancementData.EnhancementType enhancementType, Properties properties) {
        super(properties);
        this.enhancementType = enhancementType;
    }

    public EnhancementData.EnhancementType getEnhancementType() {
        return enhancementType;
    }

    public EnhancementData modemEnhancementData(EnhancementData enhancementData) {
        float strength = enhancementData.strength();
        float coolDown = enhancementData.coolDown();
        float efficiency = enhancementData.efficiency();
        int entropy = enhancementData.entropy();
        int lifeExtraction = enhancementData.lifeExtraction();
        switch (enhancementType){
            case STRENGTH -> strength = strength + 0.25F;
            case COOLDOWN -> coolDown =  Math.max(coolDown - 0.15F, 0.1F);
            case EFFICIENCY -> efficiency = Math.max(efficiency - 0.1F, 0.1F);
            case ENTROPY -> entropy += 1;
            case LIFE_EXTRACTION -> lifeExtraction += 1;
        }
        return new EnhancementData(strength, coolDown, efficiency, entropy, lifeExtraction);
    }
}
