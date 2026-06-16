package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom.MagnetoOrderSageEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.PointRotateParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class SageSMagnetismSeal extends AbstractSelfMagic{
    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        MagnetoOrderSageEntity magnetoOrderSageEntity = new MagnetoOrderSageEntity(livingEntity.level(), livingEntity, 600);
        livingEntity.level().addFreshEntity(magnetoOrderSageEntity);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {

    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.sage_s_magnetism_seal_module";
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity){
        List<Vec3> circle = Function.rotatePointsYX(Function.getCirclePoints(30, 2), Mth.PI / 2, 0);
        List<Vec3> polygon = Function.rotatePointsYX(Function.getPolygonVertices(3, 4, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = Function.rotatePointsYX(Function.getPolygonVertices(3, 4, Mth.PI), Mth.PI / 2, 0);
        int i;
        Vec3 center = livingEntity.position().add(0, 0.2, 0);
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = Function.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = Function.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, -Mth.PI / 32), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, -Mth.PI / 32), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
