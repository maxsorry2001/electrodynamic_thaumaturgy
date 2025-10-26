package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MagmaLightingBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class MagmaLighting extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        Direction direction = blockHitResult.getDirection();
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(livingEntity.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, livingEntity);
        magmaLightingBeaconEntity.setDirection(direction);
        livingEntity.level().addFreshEntity(magmaLightingBeaconEntity);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> {
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
            });
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(source.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get()), (LivingEntity) source);
        magmaLightingBeaconEntity.setDirection(Direction.UP);
        source.level().addFreshEntity(magmaLightingBeaconEntity);
        if(!source.level().isClientSide()) {
            Thread thread = new Thread(() -> {
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
            });
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 50;
    }

    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.magma_lighting_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(electromagneticDriverBE.getLevel(), vec3.x(), blockPos.getY() + 1, vec3.z(), MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get()), (LivingEntity) electromagneticDriverBE.getOwner());
        magmaLightingBeaconEntity.setDirection(Direction.UP);
        electromagneticDriverBE.getLevel().addFreshEntity(magmaLightingBeaconEntity);
        if(!electromagneticDriverBE.getLevel().isClientSide()) {
            Thread thread = new Thread(() -> {
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeCircleParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 2, 3, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 6, 5, Mth.PI / 16);
                makeInParticle((ServerLevel) magmaLightingBeaconEntity.level(), magmaLightingBeaconEntity, 10, 7, Mth.PI / 16);
            });
            thread.start();
        }
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }

    private void makeCircleParticle(ServerLevel level, MagmaLightingBeaconEntity magmaLightingBeaconEntity, int height, double radius, float omega){
        List<Vec3> point = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints((int) (30 * radius), radius), Mth.PI / 2, 0);
        Vec3 center = new Vec3(magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ()).add(0, height, 0);
        for (int i = 0; i < point.size(); i++){
            Vec3 pos = center.add(point.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, omega), 20), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
    }
    private void makeInParticle(ServerLevel level, MagmaLightingBeaconEntity magmaLightingBeaconEntity, int height, double radius, float omega){
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(4, radius, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(4, radius, Mth.PI / 4), Mth.PI / 2, 0);
        Vec3 center = new Vec3(magmaLightingBeaconEntity.getX(), magmaLightingBeaconEntity.getY(), magmaLightingBeaconEntity.getZ()).add(0, height, 0);
        int i;
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), (int) (10 * radius));
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), (int) (10 * radius));
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, omega), 20), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, omega), 20), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
