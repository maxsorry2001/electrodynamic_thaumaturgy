package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.PulsedPlasmaEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PulsedPlasma extends AbstractWideMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(livingEntity, livingEntity.level(), itemStack);
        pulsedPlasmaEntity.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 0, 5, 1.5F);
        livingEntity.level().addFreshEntity(pulsedPlasmaEntity);
        if(!livingEntity.level().isClientSide()){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Vec3 vec3 = new Vec3(target.getX() - source.getX(), target.getY() - source.getY() - 3, target.getZ() - source.getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity(source, source.level(), itemStack);
        pulsedPlasmaEntity.shootFromRotation(source, source.getXRot(), source.getYRot(), 0, 5, 1.5F);
        source.level().addFreshEntity(pulsedPlasmaEntity);
        if(!source.level().isClientSide()) {
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 128;
    }

    @Override
    public int getBaseCooldown() {
        return 40;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.pulsed_plasma_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        List<LivingEntity> list = electromagneticDriverBE.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(electromagneticDriverBE.getBlockPos()).inflate(7));
        list.remove(electromagneticDriverBE.getOwner());
        if(list.isEmpty()) return;
        LivingEntity target = list.get(RandomSource.create().nextInt(list.size()));
        Vec3 vec3 = new Vec3(target.getX() - electromagneticDriverBE.getBlockPos().getX(), target.getY() - electromagneticDriverBE.getBlockPos().getY() - 3, target.getZ() - electromagneticDriverBE.getBlockPos().getZ()).normalize();
        PulsedPlasmaEntity pulsedPlasmaEntity = new PulsedPlasmaEntity((LivingEntity) electromagneticDriverBE.getOwner(), new Vec3(electromagneticDriverBE.getBlockPos().getX(), electromagneticDriverBE.getBlockPos().getY() + 3, electromagneticDriverBE.getBlockPos().getZ()), electromagneticDriverBE.getLevel(), ElectromagneticDriverBE.magicItem);
        pulsedPlasmaEntity.shoot(vec3.x(), vec3.y(), vec3.z(), 2, 5);
        electromagneticDriverBE.getLevel().addFreshEntity(pulsedPlasmaEntity);
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(30, 1), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, 0), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        List<Vec3> polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 1, Mth.PI), livingEntity.getXRot() * Mth.PI / 180, -livingEntity.getYRot() * Mth.PI / 180);
        int i;
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2)).add(circle.get(i));
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            List<Vec3> line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2)).add(line.get(j));
                Vec3 pos2 = livingEntity.getEyePosition().add(livingEntity.getLookAngle().normalize().scale(2)).add(line2.get(j));
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
