package net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class ElectricFieldDomainEffect extends MobEffect {
    public ElectricFieldDomainEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getOnPos()).inflate(10));
        for (LivingEntity target : list){
            if(target != livingEntity && target.walkAnimation.speed() > 0.01 && target.onGround()) {
                float num = 3 * amplifier;
                float rr = (float) (Math.pow(target.getX() - livingEntity.getX(), 2) + Math.pow(target.getZ() - livingEntity.getZ(), 2));
                rr = Math.max(rr, 1);
                target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), num / rr);
            }
        }
        if(!livingEntity.level().isClientSide())
            makeParticle((ServerLevel) livingEntity.level(), livingEntity);
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 10 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }

    private void makeParticle(ServerLevel serverLevel, LivingEntity livingEntity){
        List<Vec3> point = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(60, 10), Mth.PI / 2, 0);
        Vec3 center = livingEntity.position().add(0, 0.2, 0);
        for (int i = 0; i < point.size(); i++){
            Vec3 pos = center.add(point.get(i));
            serverLevel.sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255), point.get(i).scale(-0.05).toVector3f(), 20), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
    }
}
