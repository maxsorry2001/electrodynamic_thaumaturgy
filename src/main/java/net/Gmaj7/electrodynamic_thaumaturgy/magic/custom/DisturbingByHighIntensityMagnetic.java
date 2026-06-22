package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Particle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class DisturbingByHighIntensityMagnetic extends AbstractWideMagic{

    @Override
    public void playerCast(Player player, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<Mob> list = player.level().getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(Function.getDamageAmount(itemStack)));
        list.remove(player);
        for (int i = 0; i < list.size(); i++){
            Mob target = list.get(i);
            if(target instanceof Enemy || target.getTarget() == player){
                target.setTarget(i == list.size() - 1 ? list.get(0) : list.get(i + 1));
                if(!target.level().isClientSide()){
                    Thread thread = new Thread(() -> makeParticle((ServerLevel) target.level(), target));
                    thread.start();
                }
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        List<Mob> list = source.level().getEntitiesOfClass(Mob.class, source.getBoundingBox().inflate(Function.getDamageAmount(ElectromagneticDriverBE.magicItem)));
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
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.disturbing_by_high_intensity_magnetic_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        List<Mob> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(Mob.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(Function.getDamageAmount(ElectromagneticDriverBE.magicItem)));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
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
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity){
        float xRot = Mth.PI / 2, yRot = 0;
        List<Vec3> circle = Function.rotatePointsYX(Function.getCirclePoints(30, 2), xRot, yRot);
        List<Vec3> polygon = Function.rotatePointsYX(Function.getPolygonVertices(3, 2, 0), xRot, yRot);
        List<Vec3> polygon2 = Function.rotatePointsYX(Function.getPolygonVertices(3, 2, Mth.PI), xRot, yRot);
        int i;
        Vec3 center = livingEntity.getEyePosition();
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 128, 171), circle.get(i).scale(-0.1).toVector3f(), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = Function.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = Function.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 128, 171), line.get(j).scale(-0.1).toVector3f(), 10), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 128, 171), line2.get(j).scale(-0.1).toVector3f(), 10), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
