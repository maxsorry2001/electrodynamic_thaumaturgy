package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IMoeMagic {
    MoeMagicType getType();

    void cast(LivingEntity livingEntity, ItemStack itemStack);

    int getBaseEnergyCost();

    int getBaseCooldown();

    boolean success(LivingEntity livingEntity, ItemStack itemStack);
}
