package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
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
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(source.level(), source, ElectromagneticDriverBE.magicItem);
        magneticRecombinationCannonBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        source.level().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        if(source.level() instanceof ServerLevel) {
            ((ServerLevel) source.level()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.TORCH_PARTICLE_IN.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
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
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(electromagneticDriverBE.getLevel(), (LivingEntity) electromagneticDriverBE.getOwner(), ElectromagneticDriverBE.magicItem);
        magneticRecombinationCannonBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        electromagneticDriverBE.getLevel().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        if(electromagneticDriverBE.getLevel() instanceof ServerLevel) {
            ((ServerLevel) electromagneticDriverBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) electromagneticDriverBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE_IN.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
