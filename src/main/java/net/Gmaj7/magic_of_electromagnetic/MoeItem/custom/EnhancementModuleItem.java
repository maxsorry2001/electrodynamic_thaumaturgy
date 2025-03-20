package net.Gmaj7.magic_of_electromagnetic.MoeItem.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementData;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.MAIN_HAND){
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            if(mainHand.getItem() instanceof EnhancementModuleItem && offHand.getItem() instanceof MoeMagicTypeModuleItem){
                EnhancementData enhancementData = offHand.get(MoeDataComponentTypes.ENHANCEMENT_DATA);
                switch (((EnhancementModuleItem) mainHand.getItem()).getEnhancementType()){
                    case STRENGTH -> offHand.set(MoeDataComponentTypes.ENHANCEMENT_DATA, new EnhancementData(enhancementData.strength() + getEnhancementNum(), enhancementData.coolDown()));
                    case COOLDOWN -> offHand.set(MoeDataComponentTypes.ENHANCEMENT_DATA, new EnhancementData(enhancementData.strength(), enhancementData.coolDown() + getEnhancementNum()));
                    default -> {}
                }
                player.swing(InteractionHand.MAIN_HAND);
                return InteractionResultHolder.success(mainHand);
            }
            else return InteractionResultHolder.fail(mainHand);
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
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
