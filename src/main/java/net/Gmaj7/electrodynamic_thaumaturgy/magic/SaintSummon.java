package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSaintEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SaintSummon extends AbstractSelfMagic{
    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        HarmonicSaintEntity harmonicSaintEntity = new HarmonicSaintEntity(livingEntity.level(), livingEntity, 600);
        livingEntity.level().addFreshEntity(harmonicSaintEntity);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 1024;
    }

    @Override
    public int getBaseCooldown() {
        return 600;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.saint_summon_module";
    }
}
