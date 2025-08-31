package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LightingStrike extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target == null) return;
        target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(livingEntity.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(lightningBolt);
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(source.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
        source.level().addFreshEntity(lightningBolt);
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.lighting_strike_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target != null && !electromagneticDriverBE.getLevel().isClientSide()) {
            target.hurt(new DamageSource(MoeFunction.getHolder(electromagneticDriverBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(electromagneticDriverBE.getLevel());
            lightningBolt.setVisualOnly(true);
            lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
            electromagneticDriverBE.getLevel().addFreshEntity(lightningBolt);
            electromagneticDriverBE.setCooldown(getBaseCooldown());
            electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        }
    }
}
