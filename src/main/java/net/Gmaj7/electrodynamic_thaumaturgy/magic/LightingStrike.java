package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class LightingStrike extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 20);
        if(target == null) return;
        target.hurt(new DamageSource(MoeFunction.getHolder(livingEntity.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack));
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(livingEntity.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
        livingEntity.level().addFreshEntity(lightningBolt);
        if(!livingEntity.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        target.hurt(new DamageSource(MoeFunction.getHolder(source.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(source.level());
        lightningBolt.setVisualOnly(true);
        lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
        source.level().addFreshEntity(lightningBolt);
        if(!source.level().isClientSide()) {
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
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.lighting_strike_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target != null && !electromagneticDriverBE.getLevel().isClientSide()) {
            target.hurt(new DamageSource(MoeFunction.getHolder(electromagneticDriverBE.getLevel(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem));
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(electromagneticDriverBE.getLevel());
            lightningBolt.setVisualOnly(true);
            lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
            electromagneticDriverBE.getLevel().addFreshEntity(lightningBolt);
            electromagneticDriverBE.setCooldown(getBaseCooldown());
            electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        }
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(30, 1), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, Mth.PI), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        int i;
        Vec3 center = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2));
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointParticleOption(center.toVector3f(), new Vector3f(255, 255, 255)), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
