package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ElectromagneticAssault implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ELECTROMAGNETIC_ASSAULT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(10).add(start);
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
        BlockPos blockPos = new BlockPos((int) end.x(), (int) end.y(), (int) end.z());
        BlockState blockState = level.getBlockState(blockPos);
        while (!blockState.isEmpty()){
            blockPos = blockPos.above();
            blockState = level.getBlockState(blockPos);
        }
        Vec3 vec3 = livingEntity.getLookAngle().normalize().scale(0.5).add(livingEntity.getEyePosition().add(0, -0.5, 0));
        if(level instanceof ServerLevel) {
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE.get(), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE_IN.get(), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0 ,0);
        }
        livingEntity.teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
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
}
