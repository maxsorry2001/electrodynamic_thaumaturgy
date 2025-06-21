package net.Gmaj7.electrofynamic_thaumatury.magic;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFrontEntityMagic implements IMoeMagic{

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return false;
    }

    protected static LivingEntity getNearestFrontTarget(LivingEntity livingEntity, double length){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.25, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(length).add(start);
        MoeFunction.RayHitResult result = MoeFunction.getLineHitResult(livingEntity.level(), livingEntity, start, end, false, 0.5F);
        HitResult hitResult = result.getNearest(livingEntity);
        if(hitResult instanceof EntityHitResult){
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if(entity instanceof LivingEntity) return (LivingEntity) entity;
            else return null;
        }
        else return null;
    }
}
