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
        int entropy = enhancementData.entropy();
        switch (enhancementType){
            case STRENGTH -> strength = strength + 0.25F;
            case COOLDOWN -> coolDown =  Math.max(coolDown - 0.15F, 0.1F);
            case EFFICIENCY -> efficiency = Math.max(efficiency - 0.1F, 0.1F);
            case ENTROPY -> entropy = entropy + 1;
        }
        return new EnhancementData(strength, coolDown, efficiency, entropy);
    }
}
