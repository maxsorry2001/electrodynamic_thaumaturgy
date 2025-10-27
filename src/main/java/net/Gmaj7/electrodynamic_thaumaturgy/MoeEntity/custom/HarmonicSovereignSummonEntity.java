package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
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
        List<Vec3> circleXY45 = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(40, 2), Mth.PI / 4, 0);
        List<Vec3> circleXY135 = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(40, 2), -Mth.PI / 4, 0);
        List<Vec3> circleZY45 = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(40, 2), Mth.PI / 4, Mth.PI / 2);
        List<Vec3> circleZY135 = MoeFunction.rotatePointsYX(MoeFunction.generateCirclePoints(40, 2), -Mth.PI / 4, Mth.PI / 2);
        Vec3 rotateCenter = new Vec3(this.getX(), this.getY() + 5, this.getZ()),
        centerXY45 = rotateCenter.add(0, 0, 4), centerXY135 = rotateCenter.add(0, 0, -4), centerZY45 = rotateCenter.add(4, 0, 0), centerZY135 = rotateCenter.add(-4, 0, 0);
        for (int i = 0; i < circleXY45.size(); i++){
            Vec3 pos1 = centerXY45.add(circleXY45.get(i)), pos2 = centerXY135.add(circleXY135.get(i)), pos3 = centerZY45.add(circleZY45.get(i)), pos4 = centerZY135.add(circleZY135.get(i));
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos1.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos1.x(), pos1.y(), pos1.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos2.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos3.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos3.x(), pos3.y(), pos3.z(), 1, 0, 0, 0, 0);
            ((ServerLevel) this.level()).sendParticles(new PointRotateParticleOption(new Vector3f((float) rotateCenter.x(), (float) pos4.y(), (float) rotateCenter.z()), new Vector3f(255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos4.x(), pos4.y(), pos4.z(), 1, 0, 0, 0, 0);
        }

    }
}
