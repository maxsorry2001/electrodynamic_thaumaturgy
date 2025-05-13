package net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom;

import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class LowEntropy extends MobEffect {
    public LowEntropy(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(new DamageSources(livingEntity.level().registryAccess()).freeze(), 2 + amplifier);
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 20 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
