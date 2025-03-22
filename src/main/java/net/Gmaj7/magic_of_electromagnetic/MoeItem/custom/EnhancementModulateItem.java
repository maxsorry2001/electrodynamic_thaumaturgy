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
            case EMPTY -> num = 0;
        }
        return num;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.MAIN_HAND){
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            if(mainHand.getItem() instanceof EnhancementModulateItem && offHand.getItem() instanceof MoeMagicTypeModuleItem){
                EnhancementData enhancementData = offHand.get(MoeDataComponentTypes.ENHANCEMENT_DATA);
                switch (((EnhancementModulateItem) mainHand.getItem()).getEnhancementType()){
                    case STRENGTH -> offHand.set(MoeDataComponentTypes.ENHANCEMENT_DATA, new EnhancementData(enhancementData.strength() + getEnhancementNum(), enhancementData.coolDown(), enhancementData.efficiency()));
                    case COOLDOWN -> offHand.set(MoeDataComponentTypes.ENHANCEMENT_DATA, new EnhancementData(enhancementData.strength(), Math.max(enhancementData.coolDown() - getEnhancementNum(), 0.1F), enhancementData.efficiency()));
                    case EFFICIENCY -> offHand.set(MoeDataComponentTypes.ENHANCEMENT_DATA, new EnhancementData(enhancementData.strength(), enhancementData.coolDown(), Math.max(enhancementData.efficiency() - getEnhancementNum(), 0.1F)));
                    default -> {}
                }
                player.swing(InteractionHand.MAIN_HAND);
                return InteractionResultHolder.success(mainHand);
            }
            else return InteractionResultHolder.fail(mainHand);
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }
}
