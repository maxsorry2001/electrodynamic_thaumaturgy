package net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
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
        if(livingEntity.level().isClientSide()){
            for (int j = 1; j < 90; j++){
                double theta = j * 2 *Math.PI / 90;
                livingEntity.level().addParticle(ParticleTypes.ELECTRIC_SPARK, livingEntity.getX() + 2 * Math.sin(theta), livingEntity.getY(), livingEntity.getZ() + 2 * Math.cos(theta), Math.sin(theta) * 10, 0, Math.cos(theta) * 10);
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
