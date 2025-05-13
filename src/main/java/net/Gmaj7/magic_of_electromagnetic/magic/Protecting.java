package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class Protecting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.PROTECT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        ((MoeDataGet) livingEntity).getProtective().setProtecting(MoeFunction.getMagicAmount(itemStack));
    }

    @Override
    public int getBaseEnergyCost() {
        return 300;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }
}
