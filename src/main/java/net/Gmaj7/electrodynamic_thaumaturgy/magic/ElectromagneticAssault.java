package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
import org.joml.Vector3f;

import java.util.List;

public class ElectromagneticAssault extends AbstractSelfMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(MoeFunction.getMagicAmount(itemStack) / 2).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack) * 0.5F);
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
        BlockHitResult blockHitResult = MoeFunction.getHitBlock(level, livingEntity, start, end);
        BlockPos blockPos = blockHitResult.getBlockPos(), targetPos;
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            Direction direction = blockHitResult.getDirection();
            targetPos = blockPos.relative(direction, direction == Direction.DOWN ? 2 : 1);
        }
        else targetPos = blockPos;
        if(level instanceof ServerLevel) {
            Vec3 vec3 = new Vec3(targetPos.getX() - start.x(), targetPos.getY() - start.y(), targetPos.getZ() - start.z());
            Thread thread = new Thread(() -> makeParticle(level, livingEntity, start, vec3.normalize(), Mth.floor(vec3.length()) + 2));
            thread.start();
        }
        //livingEntity.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.MAGNETIC_LEVITATION_EFFECT, 140));
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 start = source.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = source.getLookAngle().normalize().scale(MoeFunction.getMagicAmount(itemStack) * 2).add(start);
        Level level = source.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, source, start, end, true, 0.5F);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) result).getEntity();
                if (entity instanceof LivingEntity) {
                    entity.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(itemStack) * 0.5F);
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
        BlockHitResult blockHitResult = MoeFunction.getHitBlock(level, source, start, end);
        BlockPos blockPos = blockHitResult.getBlockPos(), targetPos;
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            Direction direction = blockHitResult.getDirection();
            targetPos = blockPos.relative(direction, direction == Direction.DOWN ? 2 : 1);
        }
        else targetPos = blockPos;
        if(level instanceof ServerLevel) {
            Vec3 vec3 = new Vec3(targetPos.getX() - start.x(), targetPos.getY() - start.y(), targetPos.getZ() - start.z());
            Thread thread = new Thread(() -> makeParticle(level, source, start, vec3.normalize(), Mth.floor(vec3.length()) + 2));
            thread.start();
        }
        source.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
        source.addEffect(new MobEffectInstance(MoeEffects.MAGNETIC_LEVITATION_EFFECT, 140));
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 300;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.electromagnetic_assault_module";
    }

    public void makeParticle(Level level, LivingEntity livingEntity, Vec3 start, Vec3 vec3, int length){
        List<Vec3> list = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(120, 2), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < list.size(); j++) {
                Vec3 center = start.add(vec3.scale(i));
                Vec3 pos = center.add(list.get(j));
                ((ServerLevel) level).sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
