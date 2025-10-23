package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class HydrogenBondFracture extends AbstractFrontEntityMagic {

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        LivingEntity target = getNearestFrontTarget(livingEntity, 40);
        Level level = livingEntity.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), livingEntity), MoeFunction.getMagicAmount(itemStack) * 2);
        if(level instanceof ServerLevel){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) level, livingEntity));
            thread.start();
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        Level level = source.level();
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), source), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem) * 2);
        if(level instanceof ServerLevel){
            Thread thread = new Thread(() -> makeParticle((ServerLevel) level, source));
            thread.start();
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 512;
    }

    @Override
    public int getBaseCooldown() {
        return 120;
    }

    @Override
    public boolean success(LivingEntity livingEntity, ItemStack itemStack) {
        return getNearestFrontTarget(livingEntity, 20) != null;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.hydrogen_bond_fracture_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        Level level = electromagneticDriverBE.getLevel();
        electromagneticDriverBE.setCooldown(getBaseCooldown());
        electromagneticDriverBE.extractEnergy(getBaseEnergyCost());
        target.hurt(new DamageSource(MoeFunction.getHolder(level, Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), electromagneticDriverBE.getOwner()), MoeFunction.getMagicAmount(ElectromagneticDriverBE.magicItem) * 2);
        if(level instanceof ServerLevel){
            for (int i = 0; i < 10; i++){
                RandomSource randomSource = RandomSource.create();
                ((ServerLevel) level).sendParticles(MoeParticles.HYDROGEN_BOND_PARTICLE.get(), target.getX() + 4 * (randomSource.nextFloat() - 0.5), target.getY() + 2 * randomSource.nextFloat(), target.getZ() + 4 * (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
            }
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
