package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PulsedPlasma implements IMoeMagic{
    public PulsedPlasma(){}
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PULSED_PLASMA;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(livingEntity, livingEntity.level());
        pulsedPlasmaEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 5, 1.5F);
        pulsedPlasmaEntity.setPlasmaDamage(MoeFunction.getMagicAmount(itemStack));
        livingEntity.level().addFreshEntity(pulsedPlasmaEntity);
    }

    @Override
    public int getBaseEnergyCost() {
        return 600;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
