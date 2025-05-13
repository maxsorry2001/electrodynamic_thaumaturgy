package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaTorchBeaconEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PlasmaTorch implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PLASMA_TORCH;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        PlasmaTorchBeaconEntity plasmaTorchBeaconEntity = new PlasmaTorchBeaconEntity(livingEntity.level(), livingEntity, itemStack);
        plasmaTorchBeaconEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 1.5F, 1.0F);
        plasmaTorchBeaconEntity.setDamage((int) MoeFunction.getMagicAmount(itemStack) * 3);
        livingEntity.level().addFreshEntity(plasmaTorchBeaconEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 500;
    }

    @Override
    public int getBaseCooldown() {
        return 120;
    }
}
