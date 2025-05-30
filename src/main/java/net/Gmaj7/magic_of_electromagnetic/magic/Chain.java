package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Chain implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.CHAIN;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = livingEntity.level().getNearestEntity(LivingEntity.class, TargetingConditions.forCombat(), livingEntity, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getBoundingBox().inflate(6, 2, 6));
        if(target != null) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), MoeFunction.getMagicAmount(itemStack));
            addParticle(livingEntity, target);
            List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
            for (LivingEntity target1 : list) {
                if (target1 == target || target1 == livingEntity) continue;
                addParticle(target, target1);
                float b = MoeFunction.getMagicAmount(itemStack) / 5;
                target1.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), b);
                MoeFunction.checkTargetEnhancement(itemStack, target1);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    private void addParticle(LivingEntity livingEntityStart, LivingEntity livingEntityEnd){
        Vec3 vec3Start = livingEntityStart.getEyePosition().add(0, (livingEntityStart.getY() - livingEntityStart.getEyeY()) / 2, 0);
        Vec3 vec3End = livingEntityEnd.getEyePosition().add(0, (livingEntityEnd.getY() - livingEntityEnd.getEyeY()) / 2, 0);
        Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
        Vec3 vec3Per = vec3Throw.normalize();
        int x = Mth.floor(vec3Throw.length());
        if(livingEntityStart.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntityStart.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntityEnd.getX(), livingEntityEnd.getY() , livingEntityEnd.getZ(), 1, 0, 0, 0, 0);
            for (float j = 0.2F; j < x; j += 0.2F) {
                Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
                ((ServerLevel) livingEntityStart.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, vec3Point.x, vec3Point.y, vec3Point.z, 1, 0, 0, 0, 0);
            }
        }
    }
}
