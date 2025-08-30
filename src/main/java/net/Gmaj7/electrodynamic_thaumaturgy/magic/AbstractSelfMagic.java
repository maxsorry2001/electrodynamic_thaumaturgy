package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSelfMagic implements IMoeMagic{
    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {

    }

    @Override
    public boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        return false;
    }
}
