package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Iterator;

public class DomainReconstruction extends AbstractSelfMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        if(!livingEntity.level().isClientSide()) {
            Collection<MobEffectInstance> collection = livingEntity.getActiveEffects();
            Iterator<MobEffectInstance> iterator = collection.iterator();
            while(iterator.hasNext()) {
                MobEffectInstance mobEffectInstance = iterator.next();
                if(!mobEffectInstance.getEffect().value().isBeneficial())
                    livingEntity.removeEffect(mobEffectInstance.getEffect());
            }
            livingEntity.heal(MoeFunction.getMagicAmount(itemStack));
            if(livingEntity.level() instanceof ServerLevel) {
                ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
                ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public void MobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

    }

    @Override
    public int getBaseEnergyCost() {
        return 448;
    }

    @Override
    public int getBaseCooldown() {
        return 70;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.domain_reconstruction_module";
    }
}
