package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagneticFluxCascade extends AbstractFrontEntityMagic{

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {

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
