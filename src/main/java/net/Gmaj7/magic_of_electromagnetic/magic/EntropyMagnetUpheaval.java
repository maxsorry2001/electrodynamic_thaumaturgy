package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntropyMagnetUpheaval implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ENTROPY_MAGNET_UPHEAVAL;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        List<HitResult> list = hitResult.getTargets();
        for (HitResult result : list){
            if (result instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) result).getEntity();
                if (entity instanceof LivingEntity target) {
                    if(hitResult.getNearest(livingEntity) == result)
                        target.addEffect(new MobEffectInstance(MoeEffects.LOW_ENTROPY, (int) MoeFunction.getMagicAmount(itemStack) * 20));
                    else target.igniteForTicks((int) MoeFunction.getMagicAmount(itemStack) * 20);
                }
            }
        }
        Vec3 vec3 = livingEntity.getLookAngle().normalize().scale(0.5).add(livingEntity.getEyePosition().add(0, -0.5, 0));
        if(level instanceof ServerLevel)
            ((ServerLevel) level).sendParticles(MoeParticles.FRONT_MAGIC_CIRCLE_PARTICLE.get(), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0, 0);
    }

    @Override
    public int getBaseEnergyCost() {
        return 400;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
