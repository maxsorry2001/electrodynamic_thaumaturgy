package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IMoeMagic {

    void cast(LivingEntity livingEntity, ItemStack itemStack);

    int getBaseEnergyCost();

    int getBaseCooldown();

    boolean success(LivingEntity livingEntity, ItemStack itemStack);

    String getTranslate();
}
