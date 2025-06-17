package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MirageEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MiragePursuit implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.MIRAGE_PURSUIT;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        MirageEntity mirageEntity = new MirageEntity(livingEntity.level(), livingEntity);
        livingEntity.level().addFreshEntity(mirageEntity);
        if(livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) livingEntity.level()).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), livingEntity.getX(), livingEntity.getY() + 0.1, livingEntity.getZ(), 1, 0, 0, 0, 0);
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 400;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        boolean flag = true;
        List<MirageEntity> list = livingEntity.level().getEntitiesOfClass(MirageEntity.class, livingEntity.getBoundingBox().inflate(0, 3, 0));
        for (MirageEntity mirage : list){
            if(mirage.getOwner() == livingEntity) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
