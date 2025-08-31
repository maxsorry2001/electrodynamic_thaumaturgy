package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public abstract class AbstractFrontEntityMagic implements IMoeMagic{

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

    protected LivingEntity getBlockTarget(ElectromagneticDriverBE electromagneticDriverBE){
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        return list.get(RandomSource.create().nextInt(list.size()));
    }
}
