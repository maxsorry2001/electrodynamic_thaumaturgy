package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class TreeCurrent extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target != null && !livingEntity.level().isClientSide()) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
            addParticle(livingEntity, target);
            List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
            for (LivingEntity target1 : list) {
                if (target1 == target || target1 == livingEntity) continue;
                addParticle(target, target1);
                target1.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
                MoeFunction.checkTargetEnhancement(itemStack, target1);
            }
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source instanceof HarmonicSaintEntity ? ((HarmonicSaintEntity) source).getOwner() : source), MoeFunction.getMagicAmount(itemStack));
        addParticle(source, target);
        List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
        for (LivingEntity target1 : list) {
            if (target1 == target || target1 == source || (source instanceof HarmonicSaintEntity && target1 == ((HarmonicSaintEntity) source).getOwner())) continue;
            addParticle(target, target1);
            target1.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(itemStack));
            MoeFunction.checkTargetEnhancement(itemStack, target1);
        }
        if(!source.level().isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
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
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        target.hurt(new DamageSource(MoeFunction.getHolder(electromagneticDriverBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
        blockParticle(new Vec3(electromagneticDriverBE.getBlockPos().getX(), electromagneticDriverBE.getBlockPos().getY() + 1, electromagneticDriverBE.getBlockPos().getZ()), target);
        List<LivingEntity> list = target.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(5));
        for (LivingEntity target1 : list) {
            if (target1 == target || target1 == electromagneticDriverBE.getOwner()) continue;
            addParticle(target, target1);
            target1.hurt(new DamageSource(MoeFunction.getHolder(electromagneticDriverBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
        }
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(30, 1), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, Mth.PI), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        int i;
        Vec3 center = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2));
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
