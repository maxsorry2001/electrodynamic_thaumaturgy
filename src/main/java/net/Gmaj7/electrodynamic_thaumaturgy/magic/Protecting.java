package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class Protecting extends AbstractSelfMagic{

    @Override
    public void playerCast(LivingEntity livingEntity, ItemStack itemStack) {
        if(!livingEntity.level().isClientSide()) {
            float p = MoeFunction.getMagicAmount(itemStack);
            ((MoeDataGet) livingEntity).getProtective().setProtecting(p);
            PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(p));
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack) {
        if(!source.level().isClientSide()) {
            float p = MoeFunction.getMagicAmount(itemStack);
            ((MoeDataGet) source).getProtective().setProtecting(p);
            PacketDistributor.sendToAllPlayers(new MoePacket.ProtectingPacket(p));
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
        }
    }

    @Override
    public int getBaseEnergyCost() {
        return 384;
    }

    @Override
    public int getBaseCooldown() {
        return 80;
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.protecting_module";
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        List<Vec3> circle = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(60, 2), 9 * Mth.PI / 16, (90 - livingEntity.getYRot()) * Mth.PI / 180);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(6, 2, 0),- 9 * Mth.PI / 16, (90 - livingEntity.getYRot()) * Mth.PI / 180);
        Vec3 start = new Vec3(livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ());
        int i;
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = start.add(circle.get(i));
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++){
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            for (int j = 0; j < line.size(); j++){
                Vec3 pos = start.add(line.get(j));
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
