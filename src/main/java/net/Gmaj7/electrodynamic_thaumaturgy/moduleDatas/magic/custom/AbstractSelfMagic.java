package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.MagicDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSelfMagic implements IMoeMagic{
    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition definition) {

    }

    @Override
    public boolean canBlockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition definition) {
        return false;
    }
}
