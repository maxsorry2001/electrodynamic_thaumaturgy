package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.CoulombDomainBeaconEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MagmaLightingBeaconEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CoulombDomain implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.COULOMB_DOMAIN;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        coulombDomainBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        livingEntity.level().addFreshEntity(coulombDomainBeaconEntity);

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
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getBlock(livingEntity).getType() != HitResult.Type.MISS;
    }

    private BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }
}
