package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.MagneticFluxCascadeEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagneticFluxCascade extends AbstractFrontEntityMagic{

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(livingEntity.level(), livingEntity, itemStack);
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(magneticFluxCascadeEntity);
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
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
        return "item.electrofynamic_thaumatury.magnetic_flux_cascade_module";
    }
}
