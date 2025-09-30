package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FluxDissipation extends AbstractWideMagic{
    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {

    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 0;
    }

    @Override
    public int getBaseCooldown() {
        return 0;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.flux_dissipation_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {

    }
}
