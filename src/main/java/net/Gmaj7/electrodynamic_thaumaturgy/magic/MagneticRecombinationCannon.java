package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.MagneticRecombinationCannonBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
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
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(source.level(), source, MagicCastMachineBE.magicItem);
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
        return "item.electrodynamic_thaumaturgy.magnetic_recombination_cannon_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        MagneticRecombinationCannonBeaconEntity magneticRecombinationCannonBeaconEntity = new MagneticRecombinationCannonBeaconEntity(magicCastMachineBE.getLevel(), (LivingEntity) magicCastMachineBE.getOwner(), MagicCastMachineBE.magicItem);
        magneticRecombinationCannonBeaconEntity.setPos(vec3.x(), blockPos.getY() + 1, vec3.z());
        magicCastMachineBE.getLevel().addFreshEntity(magneticRecombinationCannonBeaconEntity);
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
        if(magicCastMachineBE.getLevel() instanceof ServerLevel) {
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.TORCH_PARTICLE_IN.get(), magneticRecombinationCannonBeaconEntity.getX(), magneticRecombinationCannonBeaconEntity.getY() + 0.1, magneticRecombinationCannonBeaconEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }
}
