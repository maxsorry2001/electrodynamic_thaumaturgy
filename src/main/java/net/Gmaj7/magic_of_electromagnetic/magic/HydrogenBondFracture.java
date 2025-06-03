package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class HydrogenBondFracture implements IMoeMagic {
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.HYDROGEN_BOND_FRACTURE;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = MoeFunction.getNearestFrontTarget(livingEntity, 20);
        Level level = livingEntity.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT)), MoeFunction.getMagicAmount(itemStack) * 6);
        if(level instanceof ServerLevel){
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + randomSource.nextFloat(), target.getY() + 1 + randomSource.nextFloat(), target.getZ() + randomSource.nextFloat(), 5, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 40;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return MoeFunction.getNearestFrontTarget(livingEntity, 20) != null;
    }
}
