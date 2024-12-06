package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Protecting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PROTECT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.PROTECTING, 600));
        livingEntity.setData(MoeAttachmentType.ELECTROMAGNETIC_PROTECT, MoeFunction.getMagicAmount(itemStack));
    }

    @Override
    public int getBaseEnergyCost() {
        return 300;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
