package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoCorrosiveNovaEntity;
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

public class PhotoCorrosiveNova extends AbstractBlockBeaconMagic{
    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity) {
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(7).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity = new PhotoCorrosiveNovaEntity(livingEntity.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY() + 1, blockHitResult.getBlockPos().getZ(), itemStack, livingEntity);
        livingEntity.level().addFreshEntity(photoCorrosiveNovaEntity);
        if(!livingEntity.level().isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), photoCorrosiveNovaEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(source);
        PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity = new PhotoCorrosiveNovaEntity(source.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), itemStack, source);
        source.level().addFreshEntity(photoCorrosiveNovaEntity);
        if(!source.level().isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), photoCorrosiveNovaEntity));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 200;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.photo_corrosive_nova_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity = new PhotoCorrosiveNovaEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), ElectromagneticDriverBE.magicItem ,(LivingEntity) electromagneticDriverBE.getOwner());
        target.level().addFreshEntity(photoCorrosiveNovaEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(!electromagneticDriverBE.getLevel().isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), photoCorrosiveNovaEntity));
            thread.start();
        }
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    private void makeParticle(ServerLevel level, PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity) {
        List<Vec3> circle1 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 0.5), 0, 0),
                circle2 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 0.5), 0, Mth.PI / 2);
        Vec3 center = photoCorrosiveNovaEntity.position().add(0, 1, 0);
        for (int i = 0; i < circle1.size(); i++) {
            Vec3 pos1 = center.add(circle1.get(i)), pos2 = center.add(circle2.get(i));
            level.sendParticles(new PointRotateParticleOption(new Vector3f((float) center.x(), (float) pos1.y(), (float) center.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 8), 150), pos1.x(), pos1.y(), pos1.z(), 1, 0, 0, 0, 0);
            level.sendParticles(new PointRotateParticleOption(new Vector3f((float) center.x(), (float) pos2.y(), (float) center.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 8), 150), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
        }
    }
}
