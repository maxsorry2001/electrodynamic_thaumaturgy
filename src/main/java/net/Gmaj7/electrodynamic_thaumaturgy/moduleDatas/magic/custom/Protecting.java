package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.init.mixinData.DataGet;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.ProtectingPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.particle.custom.PointRotateParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.List;

public class Protecting extends AbstractSelfMagic{

    @Override
    public void playerCast(Player livingEntity, ItemStack itemStack, MagicDefinition magicDefinition) {
        if(!livingEntity.level().isClientSide()) {
            float p = Function.getDamageAmount(itemStack);
            ((DataGet) livingEntity).getProtective().setProtecting(p);
            PacketDistributor.sendToAllPlayers(new ProtectingPacket(p));
            Thread thread = new Thread(() -> makeParticle((ServerLevel) livingEntity.level(), livingEntity));
            thread.start();
        }
    }

    @Override
    public void mobCast(LivingEntity source, LivingEntity target, ItemStack itemStack, MagicDefinition magicDefinition) {
        if(!source.level().isClientSide()) {
            float p = Function.getDamageAmount(itemStack);
            ((DataGet) source).getProtective().setProtecting(p);
            PacketDistributor.sendToAllPlayers(new ProtectingPacket(p));
            Thread thread = new Thread(() -> makeParticle((ServerLevel) source.level(), source));
            thread.start();
        }
    }

    @Override
    public String getTranslate() {
        return "item.electrodynamic_thaumaturgy.protecting_module";
    }

    private void makeParticle(ServerLevel level, LivingEntity livingEntity) {
        float xRot = 9 * Mth.PI / 16, yRot = (90 - livingEntity.getYRot()) * Mth.PI / 180;
        List<Vec3> circle = Function.rotatePointsYX(Function.getCirclePoints(60, 2), xRot, yRot);
        List<Vec3> polygon = Function.rotatePointsYX(Function.getPolygonVertices(6, 2, 0),-xRot, yRot);
        Vec3 start = new Vec3(livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ());
        int i;
        for (i = 0; i < circle.size(); i++) {
            Vec3 pos = start.add(circle.get(i));
            level.sendParticles(new PointRotateParticleOption(start.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(xRot, yRot, Mth.PI / 8), 20), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        for (i = 0; i < polygon.size(); i++){
            List<Vec3> line = Function.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), 10);
            for (int j = 0; j < line.size(); j++){
                Vec3 pos = start.add(line.get(j));
                level.sendParticles(new PointRotateParticleOption(start.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(-xRot, yRot, Mth.PI / 8), 20), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
