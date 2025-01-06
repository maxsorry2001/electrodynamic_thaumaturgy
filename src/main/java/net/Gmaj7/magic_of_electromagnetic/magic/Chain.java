package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
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
            target.hurt(new DamageSource(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), MoeFunction.getMagicAmount(itemStack));
            addParticle(livingEntity, target);
            List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
            for (LivingEntity target1 : list) {
                if (target1 == target || target1 == livingEntity) continue;
                addParticle(target, target1);
                float b = MoeFunction.getMagicAmount(itemStack) / 5;
                target1.hurt(new DamageSource(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), b);
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

    private void addParticle(LivingEntity livingEntityStart, LivingEntity livingEntityEnd){
        Vec3 vec3Start = livingEntityStart.getEyePosition().add(0, (livingEntityStart.getY() - livingEntityStart.getEyeY()) / 2, 0);
        Vec3 vec3End = livingEntityEnd.getEyePosition().add(0, (livingEntityEnd.getY() - livingEntityEnd.getEyeY()) / 2, 0);
        Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
        Vec3 vec3Per = vec3Throw.normalize();
        int x = Mth.floor(vec3Throw.length());
        for (float j = 0.2F; j < x; j += 0.2F){
            Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
            livingEntityStart.level().addParticle(ParticleTypes.ELECTRIC_SPARK, vec3Point.x, vec3Point.y, vec3Point.z, 0, 0, 0);
        }
    }
}
