package net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ElectricFieldDomainEffect extends MobEffect {
    public ElectricFieldDomainEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getOnPos()).inflate(5));
        for (LivingEntity target : list){
            if(target != livingEntity && target.walkAnimation.speed() > 0.01 && livingEntity.hasData(MoeAttachmentType.ELECTRIC_FIELD_DOMAIN_DAMAGE) && target.onGround()) {
                float num = livingEntity.getData(MoeAttachmentType.ELECTRIC_FIELD_DOMAIN_DAMAGE);
                float r = (float) Math.min(Math.abs(livingEntity.getX() - target.getX()), Math.abs(livingEntity.getZ() - target.getZ()));
                r = Math.max(r, 1);
                target.hurt(new DamageSource(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), livingEntity), (float) (num / Math.pow(r, 2)));
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
