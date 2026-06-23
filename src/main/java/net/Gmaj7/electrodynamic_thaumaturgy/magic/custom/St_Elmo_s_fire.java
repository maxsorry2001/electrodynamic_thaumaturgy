package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.effect.EtEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class St_Elmo_s_fire extends AbstractSelfMagic{
    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        livingEntity.addEffect(new MobEffectInstance(EtEffects.ST_ELMO_S_FIRE, (int) (200 * Function.getEfficiency(itemStack)), (int) Function.getDamageAmount(itemStack)));
        if(livingEntity.level() instanceof ServerLevel) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        source.addEffect(new MobEffectInstance(EtEffects.ST_ELMO_S_FIRE, (int) (200 * Function.getEfficiency(itemStack)), (int) Function.getDamageAmount(itemStack)));
        if(source.level() instanceof ServerLevel) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
        }
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.st_elmo_s_fire_module";
    }

    private void makeParticle(ServerLevel serverLevel, LivingEntity livingEntity){
        List<Vec3> circle = Function.rotatePointsYX(Function.getCirclePoints(30, 1), Mth.PI / 2, 0);
        List<Vec3> polygon = Function.rotatePointsYX(Function.getPolygonVertices(3, 1, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = Function.rotatePointsYX(Function.getPolygonVertices(3, 1, 0), Mth.PI / 2, Mth.PI / 2);
        Vec3 center = livingEntity.getEyePosition(), centerBottom = center.add(0, -1.5, 0);
        for (int i = 0; i < polygon.size(); i++){
            List<Vec3> line = Function.getLinePoints(polygon.get(i), polygon.get(i + 1 >= polygon.size() ? 0 : i + 1), 10);
            List<Vec3> line2 = Function.getLinePoints(polygon2.get(i), polygon2.get(i + 1 >= polygon2.size() ? 0 : i + 1), 10);
            for (int j = 0; j < line.size(); j++){
                Vec3 posCircleUp = center.add(circle.get(j + i * 10)), posCircleBottom = centerBottom.add(circle.get(j + i * 10)),
                        posLineUp = center.add(line.get(j)), poaLineBottom = centerBottom.add(line2.get(j));
                serverLevel.sendParticles(new PointLineParticleOption(posCircleBottom.toVector3f(), new Vector3f(255), new Vector3f(0, -0.3F, 0), 5), posCircleUp.x(), posCircleUp.y(), posCircleUp.z(), 1, 0, 0, 0, 0);
                serverLevel.sendParticles(new PointLineParticleOption(posCircleUp.toVector3f(), new Vector3f(255), new Vector3f(0, 0.3F, 0), 5), posCircleBottom.x(), posCircleBottom.y(), posCircleBottom.z(), 1, 0, 0, 0, 0);
                serverLevel.sendParticles(new PointLineParticleOption(poaLineBottom.toVector3f(), new Vector3f(255), new Vector3f(0, -0.3F, 0), 5), posLineUp.x(), posLineUp.y(), posLineUp.z(), 1, 0, 0, 0, 0);
                serverLevel.sendParticles(new PointLineParticleOption(posLineUp.toVector3f(), new Vector3f(255), new Vector3f(0, 0.3F, 0), 5), poaLineBottom.x(), poaLineBottom.y(), poaLineBottom.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
