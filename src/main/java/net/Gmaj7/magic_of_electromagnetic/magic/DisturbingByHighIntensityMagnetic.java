package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DisturbingByHighIntensityMagnetic implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<Mob> list = livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(7, 2, 7));
        list.remove(livingEntity);
        for (int i = 0; i < list.size(); i++){
            Mob target = list.get(i);
            if(target instanceof Enemy || target.getTarget() == livingEntity){
                target.setTarget(i == list.size() - 1 ? list.get(0) : list.get(i + 1));
            }
        }
        if(livingEntity.level() instanceof ServerLevel){
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.HIGH_INTENSITY_MAGNETIC_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 30;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return true;
    }
}
