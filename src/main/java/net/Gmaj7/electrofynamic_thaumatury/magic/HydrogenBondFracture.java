package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HydrogenBondFracture extends AbstractFrontEntityMagic {

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 40);
        Level level = livingEntity.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), livingEntity), MoeFunction.getMagicAmount(itemStack) * 2);
        if(level instanceof ServerLevel){
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 120;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrofynamic_thaumatury.hydrogen_bond_fracture_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        LivingEntity target = getBlockTarget(magicCastBlockBE);
        if(target == null) return;
        Level level = magicCastBlockBE.getLevel();
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), magicCastBlockBE.getOwner()), MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem) * 2);
        if(level instanceof ServerLevel){
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level).sendParticles(MoeParticles.SELF_MAGIC_CIRCLE_PARTICLE_IN.get(), target.getX(), target.getY() + 0.1, target.getZ(), 1, 0, 0, 0, 0);
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }
}
