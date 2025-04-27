package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Heal implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.HEAL;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        livingEntity.heal(MoeFunction.getMagicAmount(itemStack));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (200 * MoeFunction.getEfficiency(itemStack))));
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 30;
    }
}
