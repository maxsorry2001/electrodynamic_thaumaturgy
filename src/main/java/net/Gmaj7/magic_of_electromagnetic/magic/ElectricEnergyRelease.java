package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ElectricEnergyRelease implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ELECTRIC_ENERGY_RELEASE;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2));
        for (LivingEntity target : list){
            if(target == livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_ELECTRIC_RELEASE.getDelegate(), (int) (MoeFunction.getMagicAmount(itemStack) * 10)));
                continue;
            }
            target.hurt(new DamageSource(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), MoeFunction.getMagicAmount(itemStack) / 4);
            target.knockback(0.5, livingEntity.getX() - target.getX(), livingEntity.getZ() - target.getZ());
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 20;
    }
}
