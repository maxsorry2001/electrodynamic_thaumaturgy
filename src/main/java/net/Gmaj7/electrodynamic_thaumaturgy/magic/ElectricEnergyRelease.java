package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class ElectricEnergyRelease extends AbstractSelfMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        List<LivingEntity> list = livingEntity.level().getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(2));
        for (LivingEntity target : list){
            if(target == livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_ELECTRIC_RELEASE.getDelegate(), (int) (MoeFunction.getMagicAmount(itemStack) * 10)));
                continue;
            }
            target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack) / 4);
            target.knockback(0.5, livingEntity.getX() - target.getX(), livingEntity.getZ() - target.getZ());
        }
        if(livingEntity.level() instanceof ServerLevel) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        List<LivingEntity> list = source.level().getEntitiesOfClass(LivingEntity.class, source.getBoundingBox().inflate(2));
        for (LivingEntity livingEntity : list){
            if(livingEntity == source) {
                source.addEffect(new MobEffectInstance(MoeEffects.ELECTRIC_ELECTRIC_RELEASE.getDelegate(), (int) (MoeFunction.getMagicAmount(itemStack) * 10)));
                continue;
            }
            livingEntity.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack) / 4);
            livingEntity.knockback(0.5, livingEntity.getX() - target.getX(), livingEntity.getZ() - target.getZ());
        }
        if(source.level() instanceof ServerLevel) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 256;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.electric_energy_release_module";
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        float xRot = 9 * Mth.PI / 16, yRot = (90 - livingEntity.getYRot()) * Mth.PI / 180;
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(60, 2), xRot, yRot);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(6, 2, 0),-xRot, yRot);
        Vec3 center = new Vec3(livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ());
        int i;
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointLineParticleOption(pos.toVector3f(), new Vector3f(191, 62, 255), circle.get(i).scale(0.2).toVector3f(), 20), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++){
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            for (int j = 0; j < line.size(); j++){
                Vec3 pos = center.add(line.get(j));
                level.sendParticles(new PointLineParticleOption(pos.toVector3f(), new Vector3f(191, 62, 255), line.get(j).scale(0.2).toVector3f(), 20), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
