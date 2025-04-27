package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class Exciting implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.EXCITING;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(10));
        for (LivingEntity target : list){
            if(Math.sqrt(Math.pow(target.getX() - livingEntity.getX(), 2) + Math.pow(target.getZ() - livingEntity.getZ(), 2)) < 10 && (target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity))) {
                target.addEffect(new MobEffectInstance(MoeEffects.EXCITING, (int) (200 * MoeFunction.getEfficiency(itemStack)), (int) (1 * MoeFunction.getStrengthRate(itemStack))));
                MoeFunction.checkPotentialDifference(itemStack, livingEntity);
            }
        }
        if(livingEntity.level().isClientSide()){
            for (int j = 1; j < 90; j++){
                double theta = j * 2 *Math.PI / 90;
                livingEntity.level().addParticle(ParticleTypes.ELECTRIC_SPARK, livingEntity.getX() + 2 * Math.sin(theta), livingEntity.getY() + 1, livingEntity.getZ() + 2 * Math.cos(theta), Math.sin(theta) * 10, 0, Math.cos(theta) * 10);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 200;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }
}
