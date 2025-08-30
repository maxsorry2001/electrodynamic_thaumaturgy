package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class St_Elmo_s_fire extends AbstractSelfMagic{
    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        livingEntity.addEffect(new MobEffectInstance(MoeEffects.ST_ELMO_S_FIRE, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) MoeFunction.getMagicAmount(itemStack)));
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        source.addEffect(new MobEffectInstance(MoeEffects.ST_ELMO_S_FIRE, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) MoeFunction.getMagicAmount(itemStack)));
        if(source.level() instanceof ServerLevel) {
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), source.getX(), source.getY() + 0.1, source.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), source.getX(), source.getY() + 0.1, source.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 70;
    }


    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.st_elmo_s_fire_module";
    }
}
