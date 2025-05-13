package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MagmaLightingBeaconEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagmaLighting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.MAGMA_LIGHTING;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        MagmaLightingBeaconEntity magmaLightingBeaconEntity = new MagmaLightingBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        magmaLightingBeaconEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 2F, 0);
        livingEntity.level().addFreshEntity(magmaLightingBeaconEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 600;
    }

    @Override
    public int getBaseCooldown() {
        return 30;
    }
}
