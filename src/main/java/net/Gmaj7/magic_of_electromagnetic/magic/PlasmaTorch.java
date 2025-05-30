package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaTorchBeaconEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlasmaTorch implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PLASMA_TORCH;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        PlasmaTorchBeaconEntity plasmaTorchBeaconEntity = new PlasmaTorchBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        plasmaTorchBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        livingEntity.level().addFreshEntity(plasmaTorchBeaconEntity);
        if(livingEntity.level() instanceof ServerLevel)
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), plasmaTorchBeaconEntity.getX(), plasmaTorchBeaconEntity.getY() + 0.1, plasmaTorchBeaconEntity.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    public int getBaseEnergyCost() {
        return 500;
    }

    @Override
    public int getBaseCooldown() {
        return 120;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getBlock(livingEntity).getType() != HitResult.Type.MISS;
    }

    private BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }
}
