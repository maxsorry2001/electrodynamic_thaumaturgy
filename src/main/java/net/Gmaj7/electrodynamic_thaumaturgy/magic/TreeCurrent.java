package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
        return 120;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    private void addParticle(LivingEntity livingEntityStart, LivingEntity livingEntityEnd){
        Vec3 vec3Start = livingEntityStart.getEyePosition().subtract(0, 0.25, 0);
        Vec3 vec3End = livingEntityEnd.getEyePosition().subtract(0, 0.25, 0);
        if(livingEntityStart.level() instanceof ServerLevel) {
            MoeRayEntity moeRayEntity = new MoeRayEntity(livingEntityStart.level(), vec3Start, vec3End, livingEntityStart, false);
            livingEntityStart.level().addFreshEntity(moeRayEntity);
        }
    }

    private void blockParticle(Vec3 vec3Start, LivingEntity livingEntityEnd){
        Vec3 vec3End = livingEntityEnd.getEyePosition().subtract(0, 0.25, 0);
        if(livingEntityEnd.level() instanceof ServerLevel) {
            MoeRayEntity moeRayEntity = new MoeRayEntity(livingEntityEnd.level(), vec3Start, vec3End, livingEntityEnd, false);
            livingEntityEnd.level().addFreshEntity(moeRayEntity);
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
        float xRot = livingEntity.getXRot() * Mth.PI / 180;
        float yRot = -livingEntity.getYRot() * Mth.PI / 180;
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 1), xRot, yRot);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), xRot, yRot);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, Mth.PI), xRot, yRot);
        int i;
        Vec3 center = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2));
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255), new Vector3f(xRot, yRot, Mth.PI / 16), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255), new Vector3f(xRot, yRot, -Mth.PI / 16), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255), new Vector3f(xRot, yRot, -Mth.PI / 16), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
