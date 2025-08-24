package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
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
        target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack));
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(livingEntity.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(lightningBolt);
    }

    @Override
    public void MobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

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
        return "item.electrofynamic_thaumatury.lighting_strike_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        LivingEntity target = getBlockTarget(magicCastBlockBE);
        if(target != null && !magicCastBlockBE.getLevel().isClientSide()) {
            target.hurt(new DamageSource(MoeFunction.getHolder(magicCastBlockBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), magicCastBlockBE.getOwner()), MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem));
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(magicCastBlockBE.getLevel());
            lightningBolt.setVisualOnly(true);
            lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
            magicCastBlockBE.getLevel().addFreshEntity(lightningBolt);
            magicCastBlockBE.setCooldown(getBaseCooldown());
            magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        }
    }
}
