package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class MagneticRecombinationCannonBeaconEntity extends AbstractArrow {
    private int startTime;
    private ItemStack magicItem;
    public MagneticRecombinationCannonBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
        this.magicItem = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public MagneticRecombinationCannonBeaconEntity(Level level){
        super(MoeEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
        this.magicItem = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public MagneticRecombinationCannonBeaconEntity(Level level, double x, double y, double z, ItemStack magicItem, LivingEntity owner){
        super(MoeEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.magicItem = magicItem.copy();
        this.pickup = Pickup.DISALLOWED;
        this.startTime = -1;
    }

    @Override
    public void tick() {
        super.tick();
        this.startTime++;
        if(this.level().isClientSide()) return;
        if(startTime == 0) makeParticleA();
        if(startTime == 25) {
            Thread thread = new Thread(() -> makeParticleB(3, 4, 0, 75));
            thread.start();
        }
        if(startTime == 50) {
            Thread thread = new Thread(() -> makeParticleB(5, 7, 43 * Mth.PI / 96, 50));
            thread.start();
        }
        if(startTime == 75) {
            Thread thread = new Thread (() -> makeParticleB(7, 10, 43 * Mth.PI / 48, 25));
            thread.start();
        }
        if(startTime == 100 && magicItem != null){
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(7));
            for (LivingEntity target : list){
                if(target != this.getOwner() && target.getY() >= this.getBlockY() - 3 && !(this.getOwner() instanceof HarmonicSaintEntity && target == ((HarmonicSaintEntity) this.getOwner()).getOwner())) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), this.getOwner()), (int) MoeFunction.getMagicAmount(magicItem) * 2);
                    MoeFunction.checkTargetEnhancement(magicItem, target);
                }
            }
        }
        else if(startTime >= 120) this.discard();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ENCHANTMENT_TABLE_USE;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(MoeItems.EMPTY_PRIMARY_MODULE.get());
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public int getStartTime() {
        return startTime;
    }

    private void makeParticleA(){
        if(this.level().isClientSide()) return;
        List<Vec3> polygon1 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, 0), Mth.PI / 2, 0),
                    polygon2 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, Mth.PI / 2), Mth.PI / 2, 0),
                    polygon3 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, Mth.PI), Mth.PI / 2, 0),
                    polygon4 = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, 2, 3 * Mth.PI / 2), Mth.PI / 2, 0);
        Vec3 center = this.position(),center1 = center.add(-4, 0.2, 0), center2 = center.add(0, 0.2, -4), center3 = center.add(4, 0.2, 0), center4 = center.add(0, 0.2, 4);
        for (int i = 0; i < polygon1.size(); i++){
            List<Vec3> line1 = MoeFunction.getLinePoints(polygon1.get(i), polygon1.get((i + 1) % polygon1.size()), 10),
                        line2 = MoeFunction.getLinePoints(polygon2.get(i), polygon2.get((i + 1) % polygon2.size()), 10),
                        line3 = MoeFunction.getLinePoints(polygon3.get(i), polygon3.get((i + 1) % polygon3.size()), 10),
                        line4 = MoeFunction.getLinePoints(polygon4.get(i), polygon4.get((i + 1) % polygon4.size()), 10);
            for (int j = 0; j < line1.size(); j++){
                Vec3 pos1 = center1.add(line1.get(j)), pos2 = center2.add(line2.get(j)), pos3 = center3.add(line3.get(j)), pos4 = center4.add(line4.get(j));
                ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 128, 128), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos1.x(), pos1.y(), pos1.z(), 1, 0, 0, 0, 0);
                ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 128, 128), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos2.x(), pos2.y(), pos2.z(), 1, 0, 0, 0, 0);
                ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 128, 128), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos3.x(), pos3.y(), pos3.z(), 1, 0, 0, 0, 0);
                ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 128, 128), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), 100), pos4.x(), pos4.y(), pos4.z(), 1, 0, 0, 0, 0);
            }
        }
    }

    private void makeParticleB(float radius, int height, float degree, int tick){
        if(this.level().isClientSide()) return;
        List<Vec3> point = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints((int) (30 * radius), radius), Mth.PI / 2, 0);
        List<Vec3> polygon = MoeFunction.rotatePointsYX(MoeFunction.getPolygonVertices(3, radius, degree), Mth.PI / 2, 0);
        Vec3 center = new Vec3(this.getX(), this.getY(), this.getZ()).add(0, height, 0);
        for (int i = 0; i < point.size(); i++){
            Vec3 pos = center.add(point.get(i));
            ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(255, 255, 255), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), tick), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
        }
        int i;
        for (i = 0; i < polygon.size(); i++) {
            List<Vec3> line = MoeFunction.getLinePoints(polygon.get(i), polygon.get((i + 1) % polygon.size()), (int) (10 * radius));
            for (int j = 0; j < line.size(); j++) {
                Vec3 pos = center.add(line.get(j));
                ((ServerLevel)this.level()).sendParticles(new PointRotateParticleOption(center.toVector3f(), new Vector3f(height == 4 ? 255 : 128, height == 7 ? 255 : 128, height == 10 ? 255 : 128), new Vector3f(Mth.PI / 2, 0, Mth.PI / 32), tick), pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0);
            }
        }
    }
}
