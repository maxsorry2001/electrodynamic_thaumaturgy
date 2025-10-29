package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class HarmonicSovereignSummonEntity extends AbstractArrow {
    private int summonTick = 100;
    public HarmonicSovereignSummonEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public HarmonicSovereignSummonEntity(Level pLevel) {
        super(MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get(), pLevel);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide()){
            if(summonTick == 100){
                Thread thread = new Thread(() -> makeParticleA());
                thread.start();
            }
            if(summonTick == 75){
                Thread thread = new Thread(() -> {
                    makeParticleB();
                    makeParticleC();
                });
                thread.start();
            }
            if(summonTick == 50){
                Thread thread = new Thread(() -> makeParticleD(summonTick, 4, 4));
                thread.start();
            }
            if(summonTick == 40){
                Thread thread = new Thread(() -> makeParticleD(summonTick, 6, 6));
                thread.start();
            }
            if(summonTick == 30){
                Thread thread = new Thread(() -> makeParticleD(summonTick, 8, 8));
                thread.start();
            }
        }
        if(summonTick < 0){
            HarmonicSovereignEntity harmonicSovereignEntity = MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get().create(level());
            harmonicSovereignEntity.teleportTo(getOnPos().getX() + 0.5, getOnPos().getY() + 1, getOnPos().getZ() + 0.5);
            level().addFreshEntity(harmonicSovereignEntity);
            for (int i = 1; i <= 8; i++){
                float r = i * 2 * Mth.PI / 8;
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level());
                lightningBolt.teleportTo(getX() + 4 * Mth.sin(r), getY(), getZ() + 4 * Mth.cos(r));
                lightningBolt.setVisualOnly(true);
                level().addFreshEntity(lightningBolt);
            }
            this.discard();
        }
        this.summonTick --;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    private void makeParticleA(){
        if (this.level().isClientSide()) return;
        List<Vec3> circleXY45 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(40, 2), Mth.PI / 4, 0);
        List<Vec3> circleXY135 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(40, 2), -Mth.PI / 4, 0);
        List<Vec3> circleZY45 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(40, 2), Mth.PI / 4, Mth.PI / 2);
        List<Vec3> circleZY135 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(40, 2), -Mth.PI / 4, Mth.PI / 2);
        Vec3 rotateCenter = this.position().add(0, 5, 0),
        centerXY45 = rotateCenter.add(0, 0, 4), centerXY135 = rotateCenter.add(0, 0, -4), centerZY45 = rotateCenter.add(4, 0, 0), centerZY135 = rotateCenter.add(-4, 0, 0);
        for (int i = 0; i < circleXY45.size(); i++){
            Vec3 pos1 = centerXY45.add(circleXY45.get(i)), pos2 = centerXY135.add(circleXY135.get(i)), pos3 = centerZY45.add(circleZY45.get(i)), pos4 = centerZY135.add(circleZY135.get(i));
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos1.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos1.x(), pos1.y(), pos1.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos2.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos3.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos3.x(), pos3.y(), pos3.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos4.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos4.x(), pos4.y(), pos4.z(), 1, 0, 0, 0, 0);
        }
    }

    private void makeParticleB(){
        if(this.level().isClientSide()) return;
        List<Vec3> polygon1 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, 0), Mth.PI / 2, 0),
                polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, Mth.PI), Mth.PI / 2, 0);
        Vec3 center = this.position().add(0, 0.2, 0);
        for (int i = 0; i < polygon1.size(); i++){
            List<Vec3> line1 = MoeFunction.getLinePoints(polygon1.get(i), polygon1.get((i + 1) % polygon1.size()), 10),
                    line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10);
            for (int j = 0; j < line1.size(); j++){
                Vec3 pos1 = center.add(line1.get(j)), pos2 = center.add(line2.get(j));
                ((ServerLevel) this.level()).sendParticles(new PointLineParticleOption(pos1.toVector3f(), new Vector3f(255), line1.get(j).scale(0.1).toVector3f(), 75), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
                ((ServerLevel) this.level()).sendParticles(new PointLineParticleOption(pos2.toVector3f(), new Vector3f(255), line2.get(j).scale(0.1).toVector3f(), 75), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
            }
        }
    }

    private void makeParticleC(){
        if (this.level().isClientSide()) return;
        List<Vec3> circle1 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 2), Mth.PI / 2, 0);
        Vec3 center = this.position().add(0, 0.2, 0);
        for (int i = 0; i < circle1.size(); i++){
            Vec3 pos1 = center.add(circle1.get(i));
            ((ServerLevel) this.level()).sendParticles(new PointLineParticleOption(pos1.toVector3f(), new Vector3f(255), circle1.get(i).scale(0.1).toVector3f(), 75), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
        }
    }

    private void makeParticleD(int tick, float dy, float radius){
        if(this.level().isClientSide()) return;
        List<Vec3> circle1 = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints((int) (10 * radius), radius), Mth.PI / 2, 0);
        Vec3 center = this.position().add(0, dy, 0);
        for (int i = 0; i < circle1.size(); i++){
            Vec3 pos1 = center.add(circle1.get(i));
            ((ServerLevel) this.level()).sendParticles(new PointLineParticleOption(pos1.toVector3f(), new Vector3f(255), circle1.get(i).scale((float)1 / tick).toVector3f(), tick), center.x(), center.y(), center.z(), 1, 0, 0, 0, 0);
        }
    }
}
