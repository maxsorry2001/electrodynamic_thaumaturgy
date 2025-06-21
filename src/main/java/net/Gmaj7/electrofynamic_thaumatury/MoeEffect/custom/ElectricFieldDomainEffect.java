package net.Gmaj7.electrofynamic_thaumatury.MoeEffect.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ElectricFieldDomainEffect extends MobEffect {
    public ElectricFieldDomainEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getOnPos()).inflate(10));
        for (LivingEntity target : list){
            if(target != livingEntity && target.walkAnimation.speed() > 0.01 && target.onGround()) {
                float num = 3 * amplifier;
                float rr = (float) (Math.pow(target.getX() - livingEntity.getX(), 2) + Math.pow(target.getZ() - livingEntity.getZ(), 2));
                rr = Math.max(rr, 1);
                target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), num / rr);
            }
        }
        if(livingEntity.level() instanceof ServerLevel)
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.NORMAL_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
