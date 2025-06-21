package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public abstract class AbstractBlockBeaconMagic implements IMoeMagic{

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getBlock(livingEntity).getType() != HitResult.Type.MISS;
    }

    protected abstract BlockHitResult getBlock(LivingEntity livingEntity);
}
