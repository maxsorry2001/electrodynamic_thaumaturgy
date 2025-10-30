package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class SaintSummon extends AbstractSelfMagic{
    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        HarmonicSaintEntity harmonicSaintEntity = new HarmonicSaintEntity(livingEntity.level(), livingEntity, 600);
        livingEntity.level().addFreshEntity(harmonicSaintEntity);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 1024;
    }

    @Override
    public int getBaseCooldown() {
        return 600;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.saint_summon_module";
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity){
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 2), Mth.PI / 2, 0);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, Mth.PI), Mth.PI / 2, 0);
        int i;
        Vec3 center = livingEntity.position().add(0, 0.2, 0);
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, -Mth.PI / 32), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, -Mth.PI / 32), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
