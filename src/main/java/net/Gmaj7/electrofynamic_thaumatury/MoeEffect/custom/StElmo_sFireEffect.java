package net.Gmaj7.electrofynamic_thaumatury.MoeEffect.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;

import java.util.List;

public class StElmo_sFireEffect extends MobEffect {
    public StElmo_sFireEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(3));
        for (LivingEntity target : list){
            if (target != livingEntity && target instanceof Enemy){
                target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), 5 + 2F * amplifier);
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }
}
