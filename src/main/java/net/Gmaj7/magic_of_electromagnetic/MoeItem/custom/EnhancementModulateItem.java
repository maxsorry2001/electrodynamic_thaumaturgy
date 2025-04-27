package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementData;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EnhancementModulateItem extends Item{
    private final EnhancementType enhancementType;
    public EnhancementModulateItem(EnhancementType enhancementType, Properties properties) {
        super(properties);
        this.enhancementType = enhancementType;
    }

    public EnhancementType getEnhancementType() {
        return enhancementType;
    }

    public float getEnhancementNum(){
        float num = 0;
        switch (enhancementType){
            case STRENGTH -> num = 0.25F;
            case COOLDOWN -> num = 0.15F;
            case EFFICIENCY -> num = 0.1F;
            case POTENTIAL_DIFFERENCE -> num = 1F;
            case EMPTY -> num = 0;
        }
        return num;
    }

    public EnhancementData modemEnhancementData(EnhancementData enhancementData) {
        EnhancementData newEnhancementData;
        switch (enhancementType){
            case STRENGTH -> newEnhancementData = new EnhancementData(enhancementData.strength() + getEnhancementNum(), enhancementData.coolDown(), enhancementData.efficiency(), enhancementData.potential_difference());
            case COOLDOWN -> newEnhancementData = new EnhancementData(enhancementData.strength(), Math.max(enhancementData.coolDown() - getEnhancementNum(), 0.1F), enhancementData.efficiency(), enhancementData.potential_difference());
            case EFFICIENCY -> newEnhancementData = new EnhancementData(enhancementData.strength(), enhancementData.coolDown(), Math.max(enhancementData.efficiency() - getEnhancementNum(), 0.1F), enhancementData.potential_difference());
            case POTENTIAL_DIFFERENCE -> newEnhancementData = new EnhancementData(enhancementData.strength(), enhancementData.coolDown(), enhancementData.efficiency(), enhancementData.potential_difference() + (int) getEnhancementNum());
            default -> {newEnhancementData = EnhancementData.defaultData;}
        }
        return newEnhancementData;
    }
}
