package net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class StElmo_sFireEffect extends MobEffect {
    public StElmo_sFireEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(3));
        for (LivingEntity target : list){
            if (target != livingEntity && target instanceof Enemy){
                target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), 5 + 2F * amplifier);
                if(!livingEntity.level().isClientSide()){
                    Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity, target));
                    thread.start();
                }
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    private void makeParticle(ServerLevel level, LivingEntity source, LivingEntity target){
        Vec3 start = source.position().add(0, 2, 0), end = new Vec3(target.getX(), (target.getY() + target.getEyeY()) / 2, target.getZ());
        List<Vec3> points = MoeFunction.getLinePoints(start, end, 30);
        for (int i = 0; i < points.size(); i++){
            level.sendParticles(new PointLineParticleOption(points.get(i).toVector3f(), new Vector3f(138, 46, 226), new Vector3f(0), 10), points.get(i).x(), points.get(i).y(), points.get(i).z(), 1, 0, 0, 0, 0);
        }
    }
}
