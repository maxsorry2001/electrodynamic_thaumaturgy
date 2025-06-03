package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TreeCurrent implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.TREE_CURRENT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = MoeFunction.getNearestFrontTarget(livingEntity, 20);
        if(target != null) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), MoeFunction.getMagicAmount(itemStack));
            addParticle(livingEntity, target);
            List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
            for (LivingEntity target1 : list) {
                if (target1 == target || target1 == livingEntity) continue;
                addParticle(target, target1);
                target1.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), MoeFunction.getMagicAmount(itemStack) / 3);
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
        return MoeFunction.getNearestFrontTarget(livingEntity, 20) != null;
    }

    private void addParticle(LivingEntity livingEntityStart, LivingEntity livingEntityEnd){
        Vec3 vec3Start = livingEntityStart.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3End = livingEntityEnd.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
        Vec3 vec3Per = vec3Throw.normalize();
        int x = Mth.floor(vec3Throw.length());
        if(livingEntityStart.level() instanceof ServerLevel) {
            MoeRayEntity moeRayEntity = new MoeRayEntity(livingEntityStart.level(), vec3Start, vec3End, livingEntityStart);
            livingEntityStart.level().addFreshEntity(moeRayEntity);
            RandomSource randomSource = RandomSource.create();
            ((ServerLevel) livingEntityStart.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntityEnd.getX(), livingEntityEnd.getY() , livingEntityEnd.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntityStart.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntityEnd.getX(), livingEntityEnd.getY() , livingEntityEnd.getZ(), 1, 0, 0, 0, 0);
            for (float j = 0.2F; j < x; j += 0.2F) {
                Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
                ((ServerLevel) livingEntityStart.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, vec3Point.x + randomSource.nextFloat(), vec3Point.y + randomSource.nextFloat(), vec3Point.z + randomSource.nextFloat(), 1, 0, 0, 0, 0);
            }
        }
    }
}
