package net.Gmaj7.electrodynamic_thaumaturgy.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDriverBE;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.CoulombDomainBeaconEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointRotateParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class CoulombDomain extends AbstractBlockBeaconMagic {

    @Override
    public void playerCast(Player player, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockHitResult blockHitResult = getBlock(player);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(player.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, player);
        player.level().addFreshEntity(coulombDomainBeaconEntity);
        if(!player.level().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) player.level(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32);
                makeParticle((ServerLevel) player.level(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32);
            });
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), itemStack, source);
        target.level().addFreshEntity(coulombDomainBeaconEntity);
        if(!source.level().isClientSide()){
            Thread thread1 = new Thread(() -> makeParticle((ServerLevel) source.level(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32));
            Thread thread2 = new Thread(() -> makeParticle((ServerLevel) source.level(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32));
            thread1.start();
            thread2.start();
        }
    }

    @Override
    protected BlockHitResult getBlock(LivingEntity livingEntity){
        Vec3 start = livingEntity.getEyePosition().subtract(0, 0.3, 0);
        Vec3 end = livingEntity.getLookAngle().normalize().scale(8).add(start);
        return Function.getHitBlock(livingEntity.level(), livingEntity, start, end);
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.coulomb_domain_module";
    }

    @Override
    public void blockCast(ElectromagneticDriverBE electromagneticDriverBE, MagicDefinition magicDefinition) {
        LivingEntity target = getBlockTarget(electromagneticDriverBE);
        if(target == null) return;
        BlockPos blockPos = target.getOnPos();
        Vec3 vec3 = blockPos.getCenter();
        if(!electromagneticDriverBE.extract(magicDefinition.baseEnergyCost())) return;
        CoulombDomainBeaconEntity coulombDomainBeaconEntity = new CoulombDomainBeaconEntity(target.level(), vec3.x(), blockPos.getY() + 1, vec3.z(), ElectromagneticDriverBE.magicItem ,(LivingEntity) electromagneticDriverBE.getOwner());
        target.level().addFreshEntity(coulombDomainBeaconEntity);
        electromagneticDriverBE.setCooldown(magicDefinition.baseCooldown());
        if(!electromagneticDriverBE.getLevel().isClientSide()){
            Thread thread = new Thread(() -> {
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), coulombDomainBeaconEntity, 10, 0, -Mth.PI / 32);
                makeParticle((ServerLevel) electromagneticDriverBE.getLevel(), coulombDomainBeaconEntity, 5, 5, Mth.PI / 32);
            });
            thread.start();
        }
    }

    private void makeParticle(ServerLevel level, CoulombDomainBeaconEntity coulombDomainBeaconEntity, int radius, int dy, float omega) {
        List<Vec3> circle = Function.rotatePointsYX(Function.getCirclePoints(12 * radius, radius), Mth.PI / 2, 0);
        List<Vec3> polygon = Function.rotatePointsYX(Function.getPolygonVertices(3, radius, 0), Mth.PI / 2, 0);
        List<Vec3> polygon2 = Function.rotatePointsYX(Function.getPolygonVertices(3, radius, Mth.PI), Mth.PI / 2, 0);
        Vec3 center = new Vec3(coulombDomainBeaconEntity.getX(), coulombDomainBeaconEntity.getY() + 0.2, coulombDomainBeaconEntity.getZ()).add(0, dy, 0);
        int i = 0;
        for (; i < circle.size(); i++) {
            Vec3 pos = center.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128,255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = Function.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 40);
            List<Vec3> line2 = Function.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 40);
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                Vec3 pos2 = center.add(line2.get(j));
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
                level.sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(128, 128, 255), new Vector3f(Mth.PI / 2, 0, omega), 100), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
