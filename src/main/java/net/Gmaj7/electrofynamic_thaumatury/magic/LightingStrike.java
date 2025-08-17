package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LightingStrike extends AbstractFrontEntityMagic {

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target != null) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack));
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(livingEntity.level());
            lightningBolt.setVisualOnly(true);
            lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
            livingEntity.level().addFreshEntity(lightningBolt);
        }
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
        List<LivingEntity> list = magicCastBlockBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(7));
        list.remove(magicCastBlockBE.getOwner());
        LivingEntity target = list.get(RandomSource.create().nextInt(list.size()));
        if(target != null && !magicCastBlockBE.getLevel().isClientSide()) {
            target.hurt(new DamageSource(MoeFunction.getHolder(magicCastBlockBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), magicCastBlockBE.getOwner()), 10);
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(magicCastBlockBE.getLevel());
            lightningBolt.setVisualOnly(true);
            lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
            magicCastBlockBE.getLevel().addFreshEntity(lightningBolt);
            magicCastBlockBE.setInfinity(getBaseCooldown());
            magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        }
    }
}
