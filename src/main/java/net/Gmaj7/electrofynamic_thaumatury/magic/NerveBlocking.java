package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NerveBlocking extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(MoeFunction.getMagicAmount(itemStack) * 2));
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity)) {
                target.addEffect(new MobEffectInstance(MoeEffects.NERVE_BLOCKING, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) (1 * MoeFunction.getStrengthRate(itemStack))));
                MoeFunction.checkTargetEnhancement(itemStack, livingEntity);
            }
        }
        if(livingEntity.level() instanceof ServerLevel){
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        List<LivingEntity> list = source.level().getEntitiesOfClass(LivingEntity.class, new AABB(source.blockPosition()).inflate(MoeFunction.getMagicAmount(itemStack) * 2));
        list.remove(source);
        list.add(target);
        for (LivingEntity livingEntity : list){
            if((target instanceof Mob && ((Mob) target).getTarget() == source)) {
                target.addEffect(new MobEffectInstance(MoeEffects.NERVE_BLOCKING, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) (1 * MoeFunction.getStrengthRate(itemStack))));
                MoeFunction.checkTargetEnhancement(itemStack, livingEntity);
            }
        }
        if(source.level() instanceof ServerLevel){
            ((ServerLevel) source.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), source.getX(), source.getY() + 1, source.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) source.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE_IN.get(), source.getX(), source.getY() + 1, source.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 30;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.block_nerve_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        List<LivingEntity> list = magicCastBlockBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem) * 2));
        list.remove(magicCastBlockBE.getOwner());
        if(list.isEmpty()) return;
        for (LivingEntity target : list){
            if(target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == magicCastBlockBE.getOwner())) {
                target.addEffect(new MobEffectInstance(MoeEffects.NERVE_BLOCKING, (int) (200 * MoeFunction.getEfficiency(MagicCastBlockBE.magicItem)), (int) (1 * MoeFunction.getStrengthRate(MagicCastBlockBE.magicItem))));
            }
        }
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        if(magicCastBlockBE.getLevel() instanceof ServerLevel){
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), magicCastBlockBE.getBlockPos().getX(), magicCastBlockBE.getBlockPos().getY() + 1, magicCastBlockBE.getBlockPos().getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE_IN.get(), magicCastBlockBE.getBlockPos().getX(), magicCastBlockBE.getBlockPos().getY() + 1, magicCastBlockBE.getBlockPos().getZ(), 1, 0, 0, 0, 0);
        }
    }
}
