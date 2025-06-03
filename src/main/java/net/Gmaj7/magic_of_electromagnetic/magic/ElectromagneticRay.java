package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ElectromagneticRay implements IMoeMagic{
    public ElectromagneticRay(){}
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.RAY;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), livingEntity);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), MoeFunction.getMagicAmount(itemStack) * 0.75F);
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
        Vec3 vec3 = livingEntity.getLookAngle().normalize().scale(0.5).add(livingEntity.getEyePosition().add(0, -0.5, 0));
        if(level instanceof ServerLevel) {
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE.get(), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE_IN.get(), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0 ,0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 400;
    }

    @Override
    public int getBaseCooldown() {
        return 40;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
