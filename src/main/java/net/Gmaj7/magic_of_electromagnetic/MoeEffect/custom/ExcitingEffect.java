package net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ExcitingEffect extends MobEffect {
    public ExcitingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(livingEntity.hasData(MoeAttachmentType.EXCITING_DAMAGE))
            livingEntity.hurt(new DamageSource(livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC)), livingEntity.getData(MoeAttachmentType.EXCITING_DAMAGE));
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 20 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
