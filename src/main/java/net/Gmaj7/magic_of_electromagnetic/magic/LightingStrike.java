package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LightingStrike extends AbstractFrontEntityMagic {
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.LIGHTING_STRIKE;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target != null) {
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), livingEntity), MoeFunction.getMagicAmount(itemStack) / 2);
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
}
