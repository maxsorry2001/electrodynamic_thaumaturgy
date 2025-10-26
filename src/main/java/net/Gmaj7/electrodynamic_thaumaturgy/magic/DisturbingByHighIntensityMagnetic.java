package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class DisturbingByHighIntensityMagnetic extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        List<Mob> list = livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(MoeFunction.getMagicAmount(itemStack)));
        list.remove(livingEntity);
        for (int i = 0; i < list.size(); i++){
            Mob target = list.get(i);
            if(target instanceof Enemy || target.getTarget() == livingEntity){
                target.setTarget(i == list.size() - 1 ? list.get(0) : list.get(i + 1));
                if(!target.level().isClientSide()){
                    Thread thread = new Thread(() -> makeParticle((ServerLevel) target.level(), target));
                    thread.start();
                }
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        List<Mob> list = source.level().getEntitiesOfClass(Mob.class, source.getBoundingBox().inflate(MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem)));
        list.remove(source);
        if(target instanceof  Mob) list.add((Mob) target);
        if(list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++){
            Mob changeTarget = list.get(i);
            if(changeTarget instanceof Enemy || changeTarget.getTarget() == target){
                changeTarget.setTarget(target);
                if(!target.level().isClientSide()){
                    Thread thread = new Thread(() -> makeParticle((ServerLevel) target.level(), target));
                    thread.start();
                }
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 70;
    }


    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.disturbing_by_high_intensity_magnetic_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        List<Mob> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(Mob.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem)));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++){
            Mob target = list.get(i);
            if(target instanceof Enemy || target.getTarget() == electromagneticDriverBE.getOwner()){
                target.setTarget(i == list.size() - 1 ? list.get(0) : list.get(i + 1));
                if(!target.level().isClientSide()){
                    Thread thread = new Thread(() -> makeParticle((ServerLevel) target.level(), target));
                    thread.start();
                }
            }
        }
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity){
        float xRot = Mth.PI / 2, yRot = 0;
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(30, 1), xRot, yRot);
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
