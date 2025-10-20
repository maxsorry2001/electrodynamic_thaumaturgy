package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PhotoacousticPulseBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = target.getOnPos().getCenter();
        PhotoacousticPulseBeaconEntity photoacousticPulseBeaconEntity = new PhotoacousticPulseBeaconEntity(source.level(), vec3.x(), vec3.y(), vec3.z(), ElectromagneticDriverBE.magicItem, source);
        source.level().addFreshEntity(photoacousticPulseBeaconEntity);
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
    }
}
