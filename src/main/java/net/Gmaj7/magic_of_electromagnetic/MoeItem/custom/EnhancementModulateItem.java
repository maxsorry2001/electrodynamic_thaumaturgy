package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementData;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.minecraft.world.item.Item;

public class EnhancementModulateItem extends Item{
    private final EnhancementType enhancementType;
    public EnhancementModulateItem(EnhancementType enhancementType, Properties properties) {
        super(properties);
        this.enhancementType = enhancementType;
    }

    public EnhancementType getEnhancementType() {
        return enhancementType;
    }

    public EnhancementData modemEnhancementData(EnhancementData enhancementData) {
        float strength = enhancementData.strength();
        float coolDown = enhancementData.coolDown();
        float efficiency = enhancementData.efficiency();
        int potential_difference = enhancementData.potential_difference();
        int bioelectric_stop = enhancementData.bioelectricStop();
        switch (enhancementType){
            case STRENGTH -> strength = strength + 0.25F;
            case COOLDOWN -> coolDown =  Math.max(coolDown - 0.15F, 0.1F);
            case EFFICIENCY -> efficiency = Math.max(efficiency - 0.1F, 0.1F);
            case POTENTIAL_DIFFERENCE -> potential_difference = potential_difference + 1;
            case BIOELECTRIC_STOP -> bioelectric_stop = bioelectric_stop + 1;
        }
        return new EnhancementData(strength, coolDown, efficiency, potential_difference, bioelectric_stop);
    }
}
