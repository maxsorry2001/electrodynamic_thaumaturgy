package net.Gmaj7.magic_of_electromagnetic.magic;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeAttachmentType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ElectromagneticForceRamp implements IMoeMagic{
    @Override
    public MoeMagicType getType() {
        return MoeMagicType.ELECTROMAGNETIC_FORCE_RAMP;
    }

    @Override
    public void cast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(10));
        for (LivingEntity target : list){
            if(target.distanceTo(livingEntity) < 10 && (target instanceof Enemy || (target instanceof Mob && ((Mob) target).getTarget() == livingEntity))) {
                target.addDeltaMovement(new Vec3(0, MoeFunction.getMagicAmount(itemStack) / 3, 0));
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 300;
    }

    @Override
    public int getBaseCooldown() {
        return 60;
    }
}
