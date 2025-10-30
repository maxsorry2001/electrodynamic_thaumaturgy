package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoacousticPulseBeaconEntity;
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

public class PhotoacousticPulse extends AbstractBlockBeaconMagic{
    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(40).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        PhotoacousticPulseBeaconEntity photoacousticPulseBeaconEntity = new PhotoacousticPulseBeaconEntity(livingEntity.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(photoacousticPulseBeaconEntity);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), photoacousticPulseBeaconEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = target.getOnPos().getCenter();
        PhotoacousticPulseBeaconEntity photoacousticPulseBeaconEntity = new PhotoacousticPulseBeaconEntity(source.level(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, source);
        source.level().addFreshEntity(photoacousticPulseBeaconEntity);
        if(!source.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), photoacousticPulseBeaconEntity));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 200;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.photoacoustic_pulse_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        Vec3 vec3 = target.getOnPos().getCenter();
        PhotoacousticPulseBeaconEntity photoacousticPulseBeaconEntity = new PhotoacousticPulseBeaconEntity(electromagneticDriverBE.getLevel(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, (LivingEntity) electromagneticDriverBE.getOwner());
        electromagneticDriverBE.getLevel().addFreshEntity(photoacousticPulseBeaconEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(!electromagneticDriverBE.getLevel().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), photoacousticPulseBeaconEntity));
            thread.start();
        }
    }

    private void makeParticle(ServerLevel serverLevel, PhotoacousticPulseBeaconEntity entity){
        Vec3 center =new Vec3(entity.getX(), entity.getY() + 0.1, entity.getZ());
        List<Vec3> circle1 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 2), Mth.PI / 2, 0);
        List<Vec3> circle2 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(45, 4), Mth.PI / 2, 0);
        List<Vec3> triangle = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, 0), Mth.PI / 2, 0);
        List<Vec3> triangle2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 4, Mth.PI), Mth.PI / 2, 0);
        dealParticle(serverLevel, center, circle1, Mth.PI / 16);
        dealParticle(serverLevel, center, circle2, -Mth.PI / 16);
        for (int i = 1; i < triangle.size(); i++){
            List<Vec3> line = MoeFunction.rotatePointsYX(MoeFunction.getLinePoints(triangle.get(i), triangle.get(i + 1 >= triangle.size() ? 1 : i + 1), 10), Mth.PI / 2, 0);
            List<Vec3> line2 = MoeFunction.rotatePointsYX(MoeFunction.getLinePoints(triangle.get(i), triangle.get(i + 1 >= triangle2.size() ? 1 : i + 1), 15), Mth.PI / 2, 0);
            dealParticle(serverLevel, center, line, Mth.PI / 16);
            dealParticle(serverLevel, center, line2, -Mth.PI / 16);
        }
    }

    private void dealParticle(ServerLevel serverLevel, Vec3 center, List<Vec3> circle1, float omega) {
        for (Vec3 addVec3 : circle1) {
            Vec3 pos = center.add(addVec3);
            serverLevel.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, omega), 30), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
    }
}
