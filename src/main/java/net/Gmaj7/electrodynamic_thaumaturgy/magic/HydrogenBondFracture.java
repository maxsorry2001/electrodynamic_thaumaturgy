package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class HydrogenBondFracture extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 40);
        Level level = livingEntity.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack) * 2);
        if(level instanceof ServerLevel){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) level, livingEntity);
                makeTargetParticle((ServerLevel) level, target);
            });
            thread.start();
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Level level = source.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem) * 2);
        if(level instanceof ServerLevel){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) level, source);
                makeTargetParticle((ServerLevel) level, target);
            });
            thread.start();
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 120;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.hydrogen_bond_fracture_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        Level level = electromagneticDriverBE.getLevel();
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem) * 2);
        if(level instanceof ServerLevel){
            Thread thread = new Thread(() -> {
                makeTargetParticle((ServerLevel) level, target);
            });
            thread.start();
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        float xRot = livingEntity.getXRot() * Mth.PI / 180;
        float yRot = -livingEntity.getYRot() * Mth.PI / 180;
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(15, 1), xRot, yRot);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), xRot, yRot);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, Mth.PI), xRot, yRot);
        int i;
        Vec3 center = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2));
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(xRot, yRot, Mth.PI / 16), 5), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 5);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 5);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(xRot, yRot, -Mth.PI / 16), 5), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(xRot, yRot, -Mth.PI / 16), 5), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }

    private void makeTargetParticle(ServerLevel level, LivingEntity livingEntity){
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, Mth.PI), Mth.PI / 2, 0);
        int i;
        Vec3 center = new Vec3(livingEntity.getX(), (livingEntity.getY() + livingEntity.getEyeY()) / 2, livingEntity.getZ());
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), line.get(j).scale(-0.1).toVector3f(), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), line2.get(j).scale(-0.1).toVector3f(), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
