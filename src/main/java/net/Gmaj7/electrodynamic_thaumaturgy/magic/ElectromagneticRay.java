package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class ElectromagneticRay extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        Level level = livingEntity.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, livingEntity, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, hitResult.getEnd(), livingEntity, true);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
                    MoeFunction.checkTargetEnhancement(itemStack, (LivingEntity) target);
                }
            }
        }
        if(!level.isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) level, livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 start = new Vec3(source.getX(), source.getY() + 1, source.getZ());
        Vec3 end = new Vec3(target.getX(), (target.getY() + target.getEyeY()) / 2, target.getZ());
        Level level = source.level();
        MoeFunction.RayHitResult hitResult = MoeFunction.getLineHitResult(level, source, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, end, source, false);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) result).getEntity();
                if (entity instanceof LivingEntity) {
                    entity.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
                    MoeFunction.checkTargetEnhancement(ElectromagneticDriverBE.magicItem, (LivingEntity) target);
                }
            }
        }
        if(!level.isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) level, source));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 128;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.ray_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity lineTarget = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 start = new Vec3(electromagneticDriverBE.getBlockPos().getX(), electromagneticDriverBE.getBlockPos().getY() + 1, electromagneticDriverBE.getBlockPos().getZ());
        Vec3 end = new Vec3(lineTarget.getX(), (lineTarget.getY() + lineTarget.getEyeY()) / 2, lineTarget.getZ());
        Level level = electromagneticDriverBE.getLevel();
        MoeFunction.RayHitResult hitResult = MoeFunction.getBlockLineHitResult(level, electromagneticDriverBE, start, end, true, 0.5F);
        MoeRayEntity moeRayEntity = new MoeRayEntity(level, start, end, (LivingEntity) electromagneticDriverBE.getOwner(), false);
        level.addFreshEntity(moeRayEntity);
        for (HitResult result : hitResult.getTargets()) {
            if (result instanceof EntityHitResult) {
                Entity target = ((EntityHitResult) result).getEntity();
                if (target instanceof LivingEntity) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
                    MoeFunction.checkTargetEnhancement(ElectromagneticDriverBE.magicItem, (LivingEntity) target);
                }
            }
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
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(xRot, yRot, Mth.PI / 16), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(xRot, yRot, -Mth.PI / 16), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(xRot, yRot, -Mth.PI / 16), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
