package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MagneticFluxCascadeEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagneticFluxCascade extends AbstractFrontEntityMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(livingEntity.level(), livingEntity, itemStack);
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(magneticFluxCascadeEntity);
        if(livingEntity.level() instanceof ServerLevel) ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.MAGNETIC_FLUX_CASCADE_PARTICLE.get(), target.getX(),  (target.getY() + target.getEyeY()) / 2, target.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(source.level(), source, MagicCastMachineBE.magicItem);
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        source.level().addFreshEntity(magneticFluxCascadeEntity);
        if(source.level() instanceof ServerLevel) ((ServerLevel) source.level()).sendParticles(MoeParticles.MAGNETIC_FLUX_CASCADE_PARTICLE.get(), target.getX(),  (target.getY() + target.getEyeY()) / 2, target.getZ(), 1, 0, 0, 0, 0);

    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 100;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.magnetic_flux_cascade_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(magicCastMachineBE.getLevel(), (LivingEntity) magicCastMachineBE.getOwner(), MagicCastMachineBE.magicItem);
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        magicCastMachineBE.getLevel().addFreshEntity(magneticFluxCascadeEntity);
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
        if(magicCastMachineBE.getLevel() instanceof ServerLevel) ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.MAGNETIC_FLUX_CASCADE_PARTICLE.get(), target.getX(),  (target.getY() + target.getEyeY()) / 2, target.getZ(), 1, 0, 0, 0, 0);
    }
}
