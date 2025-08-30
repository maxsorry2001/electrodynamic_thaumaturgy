package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TreeCurrent extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target != null) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
            addParticle(livingEntity, target);
            List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
            for (LivingEntity target1 : list) {
                if (target1 == target || target1 == livingEntity) continue;
                addParticle(target, target1);
                target1.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
                MoeFunction.checkTargetEnhancement(itemStack, target1);
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(itemStack));
        addParticle(source, target);
        List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
        for (LivingEntity target1 : list) {
            if (target1 == target || target1 == source) continue;
            addParticle(target, target1);
            target1.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(itemStack));
            MoeFunction.checkTargetEnhancement(itemStack, target1);
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
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    private void addParticle(LivingEntity livingEntityStart, LivingEntity livingEntityEnd){
        Vec3 vec3Start = livingEntityStart.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3End = livingEntityEnd.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
        Vec3 vec3Per = vec3Throw.normalize();
        int x = Mth.floor(vec3Throw.length());
        if(livingEntityStart.level() instanceof ServerLevel) {
            MoeRayEntity moeRayEntity = new MoeRayEntity(livingEntityStart.level(), vec3Start, vec3End, livingEntityStart, false);
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

    private void blockParticle(Vec3 vec3Start, LivingEntity livingEntityEnd){
        Vec3 vec3End = livingEntityEnd.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
        Vec3 vec3Per = vec3Throw.normalize();
        int x = Mth.floor(vec3Throw.length());
        if(livingEntityEnd.level() instanceof ServerLevel) {
            MoeRayEntity moeRayEntity = new MoeRayEntity(livingEntityEnd.level(), vec3Start, vec3End, livingEntityEnd, false);
            livingEntityEnd.level().addFreshEntity(moeRayEntity);
            RandomSource randomSource = RandomSource.create();
            ((ServerLevel) livingEntityEnd.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntityEnd.getX(), livingEntityEnd.getY() , livingEntityEnd.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntityEnd.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntityEnd.getX(), livingEntityEnd.getY() , livingEntityEnd.getZ(), 1, 0, 0, 0, 0);
            for (float j = 0.2F; j < x; j += 0.2F) {
                Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
                ((ServerLevel) livingEntityEnd.level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, vec3Point.x + randomSource.nextFloat(), vec3Point.y + randomSource.nextFloat(), vec3Point.z + randomSource.nextFloat(), 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.tree_current_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        target.hurt(new DamageSource(MoeFunction.getHolder(magicCastMachineBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), magicCastMachineBE.getOwner()), MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem));
        blockParticle(new Vec3(magicCastMachineBE.getBlockPos().getX(), magicCastMachineBE.getBlockPos().getY() + 1, magicCastMachineBE.getBlockPos().getZ()), target);
        List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
        for (LivingEntity target1 : list) {
            if (target1 == target || target1 == magicCastMachineBE.getOwner()) continue;
            addParticle(target, target1);
            target1.hurt(new DamageSource(MoeFunction.getHolder(magicCastMachineBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), magicCastMachineBE.getOwner()), MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem));
        }
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
