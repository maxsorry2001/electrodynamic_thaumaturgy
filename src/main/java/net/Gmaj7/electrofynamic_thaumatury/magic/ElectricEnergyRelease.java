package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ElectricEnergyRelease extends AbstractSelfMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2));
        for (LivingEntity target : list){
            if(target == livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_ELECTRIC_RELEASE.getDelegate(), (int) (MoeFunction.getMagicAmount(itemStack) * 10)));
                continue;
            }
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack) / 4);
            target.knockback(0.5, livingEntity.getX() - target.getX(), livingEntity.getZ() - target.getZ());
        }
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        List<LivingEntity> list = source.level().getEntitiesOfClass(LivingEntity.class, source.getBoundingBox().inflate(2));
        for (LivingEntity livingEntity : list){
            if(livingEntity == source) {
                source.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_ELECTRIC_RELEASE.getDelegate(), (int) (MoeFunction.getMagicAmount(itemStack) * 10)));
                continue;
            }
            livingEntity.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack) / 4);
            livingEntity.knockback(0.5, livingEntity.getX() - target.getX(), livingEntity.getZ() - target.getZ());
        }
        if(source.level() instanceof ServerLevel) {
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), source.getX(), source.getY() + 0.1, source.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), source.getX(), source.getY() + 0.1, source.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }


    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.electric_energy_release_module";
    }
}
