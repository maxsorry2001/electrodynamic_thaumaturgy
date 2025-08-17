package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSelfMagic implements IMoeMagic{
    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {

    }

    @Override
    public boolean canBlockCast(MagicCastBlockBE magicCastBlockBE) {
        return false;
    }
}
