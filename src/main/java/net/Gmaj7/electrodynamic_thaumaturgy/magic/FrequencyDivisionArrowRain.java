package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.FrequencyDivisionBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class FrequencyDivisionArrowRain extends AbstractBlockBeaconMagic {
    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        Vec3 vec3 = blockHitResult.getBlockPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(livingEntity.level(), vec3.x(), vec3.y(), vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), frequencyDivisionArrowEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = target.getOnPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(source.level(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, source);
        source.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(!source.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), frequencyDivisionArrowEntity));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 100;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.frequency_division_arrow_rain_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        Vec3 vec3 = target.getOnPos().getCenter();
        FrequencyDivisionBeaconEntity frequencyDivisionArrowEntity = new FrequencyDivisionBeaconEntity(electromagneticDriverBE.getLevel(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, (LivingEntity) electromagneticDriverBE.getOwner());
        electromagneticDriverBE.getLevel().addFreshEntity(frequencyDivisionArrowEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(!electromagneticDriverBE.getLevel().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), frequencyDivisionArrowEntity));
            thread.start();
        }
    }

    private void makeParticle(ServerLevel level, FrequencyDivisionBeaconEntity frequencyDivisionBeaconEntity){
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(60, 4), Mth.PI / 2, 0);
        List<Vec3> circle2 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(45, 2.828), Mth.PI / 2, 0);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, Mth.PI), Mth.PI / 2, 0);
        List<Vec3> polygon3 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(4, 2.828, Mth.PI), Mth.PI / 2, 0);
        Vec3 center = new Vec3(frequencyDivisionBeaconEntity.getX(), frequencyDivisionBeaconEntity.getY() + 0.8, frequencyDivisionBeaconEntity.getZ());
        int i = 0;
        for (; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i)).add(0, 10, 0);
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255,255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 60), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            if(i < circle2.size()){
                Vec3 pos2 = center.add(circle2.get(i));
                level.sendParticles(new PointRotateParticleOption(center.add(0, 5, 0).toVector3f(), new Vector3f(255, 255,255), new Vector3f(Mth.PI / 2, 0, -Mth.PI / 32), 60), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 20);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 20);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j)).add(0, 10, 0);
                Vec3 pos2 = center.add(line2.get(j)).add(0, 10, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 60), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 60), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
        for (i = 0; i < polygon3.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon3.get(i), polygon3.get((i + 1) % polygon3.size()), 14);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(184, 206, 11), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 60), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
