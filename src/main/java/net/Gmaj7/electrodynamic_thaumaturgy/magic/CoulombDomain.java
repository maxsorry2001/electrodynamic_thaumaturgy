package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class CoulombDomain extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(livingEntity.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(coulombDomainBeaconEntity);
        if(!livingEntity.level().isClientSide){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) livingEntity.level(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32);
                makeParticle((ServerLevel) livingEntity.level(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32);
            });
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, source);
        target.level().addFreshEntity(coulombDomainBeaconEntity);
        if(!source.level().isClientSide()){
            Thread thread1 = new Thread(() -> makeParticle((ServerLevel) source.level(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32));
            Thread thread2 = new Thread(() -> makeParticle((ServerLevel) source.level(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32));
            thread1.start();
            thread2.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 300;
    }

    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.coulomb_domain_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), ElectromagneticDriverBE.magicItem ,(LivingEntity) electromagneticDriverBE.getOwner());
        target.level().addFreshEntity(coulombDomainBeaconEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(!electromagneticDriverBE.getLevel().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32);
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32);
            });
            thread.start();
        }
    }

    private void makeParticle(ServerLevel level, CoulombDomainBeaconEntity coulombDomainBeaconEntity, int radius, int dy, float omega) {
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(12 * radius, radius), Mth.PI / 2, 0);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, radius, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, radius, Mth.PI), Mth.PI / 2, 0);
        Vec3 center = new Vec3(coulombDomainBeaconEntity.getX(), coulombDomainBeaconEntity.getY() + 0.2, coulombDomainBeaconEntity.getZ()).add(0, dy, 0);
        int i = 0;
        for (; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128,255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 40);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 40);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
