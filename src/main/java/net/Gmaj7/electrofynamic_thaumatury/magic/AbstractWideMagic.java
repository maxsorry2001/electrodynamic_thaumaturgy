package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractWideMagic implements IMoeMagic{

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canBlockCast(MagicCastMachineBE magicCastMachineBE) {
        return true;
    }
}
