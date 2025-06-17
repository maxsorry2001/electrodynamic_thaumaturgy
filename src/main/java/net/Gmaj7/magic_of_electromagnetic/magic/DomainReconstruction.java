package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Iterator;

public class DomainReconstruction implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.DOMAIN_RECONSTRUCTION;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
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
    public int getBaseEnergyCost() {
        return 448;
    }

    @Override
    public int getBaseCooldown() {
        return 70;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
