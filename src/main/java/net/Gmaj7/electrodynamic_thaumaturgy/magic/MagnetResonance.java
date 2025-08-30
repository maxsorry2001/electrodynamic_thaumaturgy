package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagicCastMachineBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
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
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(MagicCastMachineBE.magicItem)), (int) MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem)));
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
        return "item.electrodynamic_thaumaturgy.magnet_resonance_module";
    }

    @Override
    public void blockCast(MagicCastMachineBE magicCastMachineBE) {
        LivingEntity target = getBlockTarget(magicCastMachineBE);
        if(target == null) return;
        target.addEffect(new MobEffectInstance(MoeEffects.MAGNET_RESONANCE, (int) (200 * MoeFunction.getEfficiency(MagicCastMachineBE.magicItem)), (int) MoeFunction.getMagicAmount(MagicCastMachineBE.magicItem)));
        if(!magicCastMachineBE.getLevel().isClientSide()) {
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastMachineBE.getLevel()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
        }
        magicCastMachineBE.setCooldown(getBaseCooldown());
        magicCastMachineBE.extractEnergy(getBaseEnergyCost());
    }
}
