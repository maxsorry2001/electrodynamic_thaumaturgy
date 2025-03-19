package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnhancementModuleItem extends Item implements IMoeModuleItem{
    private final EnhancementType enhancementType;
    public EnhancementModuleItem(EnhancementType enhancementType, Properties properties) {
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
            case EMPTY -> num = 0;
        }
        return num;
    }

    @Override
    public boolean isEmpty() {
        return enhancementType == EnhancementType.EMPTY;
    }

    public static float checkStrength(ItemStack itemStack){
        float strength = 0F;
        if(itemStack.getItem() instanceof EnhancementModuleItem && ((EnhancementModuleItem) itemStack.getItem()).getEnhancementType() == EnhancementType.STRENGTH){
            strength = strength + 0.25F;
        }
        return strength;
    }

    public static float checkCooldown(ItemStack itemStack){
        float cooldown = 0;
        if(itemStack.getItem() instanceof EnhancementModuleItem && ((EnhancementModuleItem) itemStack.getItem()).getEnhancementType() == EnhancementType.COOLDOWN){
            cooldown = cooldown + 0.15F;
        }
        return cooldown;
    }
}
