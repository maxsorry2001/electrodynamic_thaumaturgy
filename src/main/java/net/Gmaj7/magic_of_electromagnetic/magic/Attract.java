package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaArrowEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Attract implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ATTRACT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        PlasmaArrowEntity plasmaArrowEntity = new PlasmaArrowEntity(livingEntity.level(), livingEntity);
        plasmaArrowEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 1.5F, 1.0F);
        plasmaArrowEntity.setLiveTime((int) MoeFunction.getMagicAmount(itemStack) * 10);
        livingEntity.level().addFreshEntity(plasmaArrowEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }
}
