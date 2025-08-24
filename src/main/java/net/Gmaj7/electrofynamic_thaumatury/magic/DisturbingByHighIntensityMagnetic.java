package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.MagicCastBlockBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DisturbingByHighIntensityMagnetic extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        List<Mob> list = livingEntity.level().getEntitiesOfClass(Mob.class, livingEntity.getBoundingBox().inflate(MoeFunction.getMagicAmount(itemStack)));
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
    public void MobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {

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
        return "item.electrofynamic_thaumatury.disturbing_by_high_intensity_magnetic_module";
    }

    @Override
    public void blockCast(MagicCastBlockBE magicCastBlockBE) {
        List<Mob> list = magicCastBlockBE.getLevel().getEntitiesOfClass(Mob.class, new AABB(magicCastBlockBE.getBlockPos()).inflate(MoeFunction.getMagicAmount(MagicCastBlockBE.magicItem)));
        list.remove(magicCastBlockBE.getOwner());
        if(list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++){
            Mob target = list.get(i);
            if(target instanceof Enemy || target.getTarget() == magicCastBlockBE.getOwner()){
                target.setTarget(i == list.size() - 1 ? list.get(0) : list.get(i + 1));
            }
        }
        magicCastBlockBE.setCooldown(getBaseCooldown());
        magicCastBlockBE.extractEnergy(getBaseEnergyCost());
        if(magicCastBlockBE.getLevel() instanceof ServerLevel){
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), magicCastBlockBE.getBlockPos().getX(), magicCastBlockBE.getBlockPos().getY() + 1, magicCastBlockBE.getBlockPos().getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) magicCastBlockBE.getLevel()).sendParticles(MoeParticles.HIGH_INTENSITY_MAGNETIC_PARTICLE_IN.get(), magicCastBlockBE.getBlockPos().getX(), magicCastBlockBE.getBlockPos().getY() + 1, magicCastBlockBE.getBlockPos().getZ(), 1, 0, 0, 0, 0);
        }
    }
}
