package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MagneticRecombinationCannonBeaconEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MagneticRecombinationCannon extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        BlockHitResult blockHitResult = getBlock(livingEntity);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        magneticRecombinationCannonBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        livingEntity.level().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.TORCH_PARTICLE_IN.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void MobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 160;
    }

    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(20).add(start);
        return MoeFunction.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.magnetic_recombination_cannon_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        LivingEntity target = getBlockTarget(magicCastBlockBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(magicCastBlockBE.getLevel(), (LivingEntity) magicCastBlockBE.getOwner(), MagicCastBlockBE.magicItem);
        magneticRecombinationCannonBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        magicCastBlockBE.getLevel().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        if(magicCastBlockBE.getLevel() instanceof ServerLevel) {
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE_IN.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
