package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MagnetResonance extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) MoeFunction.getMagicAmount(itemStack)));
        if(!livingEntity.level().isClientSide()) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(MagicCastBlockBE.magicItem)), (int) MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem)));
        if(!source.level().isClientSide()) {
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 25;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.magnet_resonance_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        LivingEntity target = getBlockTarget(magicCastBlockBE);
        if(target == null) return;
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(MagicCastBlockBE.magicItem)), (int) MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem)));
        if(!magicCastBlockBE.getLevel().isClientSide()) {
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
        }
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
    }
}
