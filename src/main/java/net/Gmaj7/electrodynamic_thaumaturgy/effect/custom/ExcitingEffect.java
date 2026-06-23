package net.Gmaj7.electrodynamic_thaumaturgy.effect.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ExcitingEffect extends MobEffect {
    public ExcitingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(new DamageSource(Function.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy)), (float) (0.5 * amplifier));
        return super.applyEffectTick(serverLevel, livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
