package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoCorrosiveNovaEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(source);
        PhotoCorrosiveNovaEntity photoCorrosiveNovaEntity = new PhotoCorrosiveNovaEntity(source.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), itemStack, source);
        source.level().addFreshEntity(photoCorrosiveNovaEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 0;
    }

    @Override
    public int getBaseCooldown() {
        return 0;
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
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
