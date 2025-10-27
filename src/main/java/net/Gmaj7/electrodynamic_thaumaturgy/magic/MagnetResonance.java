package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class MagnetResonance extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) MoeFunction.getMagicAmount(itemStack)));
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), target));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(ElectromagneticDriverBE.magicItem)), (int) MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem)));
        if(!source.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), target));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 25;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.magnet_resonance_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(ElectromagneticDriverBE.magicItem)), (int) MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem)));
        if(!electromagneticDriverBE.getLevel().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), target));
            thread.start();
        }
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }

    private void makeParticle(ServerLevel serverLevel, LivingEntity livingEntity){
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(30, 1), Mth.PI / 2, 0);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), Mth.PI / 2, Mth.PI / 2);
        Vec3 center = livingEntity.getEyePosition(), centerBottom = center.add(0, -1.5, 0);
        for (int i = 0; i < polygon.size(); i++){
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get(i + 1 >= polygon.size() ? 0 : i + 1), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get(i + 1 >= polygon2.size() ? 0 : i + 1), 10);
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
