package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ElectromagneticAssault implements IMoeMagic{

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(MoeFunction.getMagicAmount(itemStack) * 2).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack) * 0.5F);
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
        BlockHitResult blockHitResult = MoeFunction.getHitBlock(level, livingEntity, start, end);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = new Vec3(end.x() - livingEntity.getX(), end.y() - livingEntity.getY(), end.z() - livingEntity.getZ()).normalize();
        if (blockHitResult.getType() != HitResult.Type.MISS)
            livingEntity.teleportTo(blockPos.getX() - vec3.x(), blockPos.getY() - vec3.y(), blockPos.getZ() - vec3.z());
        else
            livingEntity.teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.MAGNETIC_LEVITATION_EFFECT, 300));
        Vec3 vec3p = livingEntity.getLookAngle().normalize().scale(0.5).add(livingEntity.getEyePosition().add(0, -0.5, 0));
        if(level instanceof ServerLevel) {
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE.get(), vec3p.x(), vec3p.y(), vec3p.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE_IN.get(), vec3p.x(), vec3p.y(), vec3p.z(), 1, 0, 0, 0 ,0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 60;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.electromagnetic_assault_module";
    }
}
