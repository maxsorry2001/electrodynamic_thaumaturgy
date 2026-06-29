package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.MagneticFluxCascadeEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.MagicDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MagneticFluxCascade extends AbstractFrontEntityMagic{

    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(livingEntity.level(), livingEntity, itemStack, magicDefinition.amountRate());
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(magneticFluxCascadeEntity);
        if(livingEntity.level() instanceof ServerLevel) {
            Thread thread = new Thread(() -> magneticFluxCascadeEntity.makeParticle(true));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(source.level(), source, ElectromagneticDriverBE.magicItem, magicDefinition.amountRate());
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        source.level().addFreshEntity(magneticFluxCascadeEntity);
        if(source.level() instanceof ServerLevel){
            Thread thread = new Thread(() -> magneticFluxCascadeEntity.makeParticle(true));
            thread.start();
        }
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.magnetic_flux_cascade_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        MagneticFluxCascadeEntity magneticFluxCascadeEntity = new MagneticFluxCascadeEntity(electromagneticDriverBE.getLevel(), (LivingEntity) electromagneticDriverBE.getOwner(), ElectromagneticDriverBE.magicItem, magicDefinition.amountRate());
        magneticFluxCascadeEntity.setTarget(target);
        magneticFluxCascadeEntity.teleportTo(target.getX(), target.getY(), target.getZ());
        electromagneticDriverBE.getLevel().addFreshEntity(magneticFluxCascadeEntity);
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
        if(electromagneticDriverBE.getLevel() instanceof ServerLevel){
            Thread thread = new Thread(() -> magneticFluxCascadeEntity.makeParticle(true));
            thread.start();
        }
    }
}
