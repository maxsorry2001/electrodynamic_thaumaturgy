package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class St_Elmo_s_fire implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ST_ELMO_S_FIRE;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 600;
    }

    @Override
    public int getBaseCooldown() {
        return 70;
    }
}
