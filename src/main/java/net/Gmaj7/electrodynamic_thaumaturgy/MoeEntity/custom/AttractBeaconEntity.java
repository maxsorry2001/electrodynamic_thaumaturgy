package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class AttractBeaconEntity extends AbstractArrow {
    private int liveTime;
    public AttractBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public AttractBeaconEntity(Level level){
        super(MoeEntities.ATTRACT_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public AttractBeaconEntity(Level level, double x, double y, double z, LivingEntity owner){
        super(MoeEntities.ATTRACT_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(tickCount > liveTime && !this.level().isClientSide()) this.discard();
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(15));
        for (LivingEntity target : list){
            if (target == this.getOwner()) continue;
            if (this.getOwner() instanceof HarmonicSaintEntity && target == ((HarmonicSaintEntity) this.getOwner()).getOwner()) continue;
            Vec3 vec3 = new Vec3(this.getX() - target.getX(), this.getY() - target.getY(), this.getZ() - target.getZ());
            Vec3 vec31 = vec3.normalize().multiply(0.1, 0.1, 0.1);
            target.move(MoverType.SELF, vec31);
        }
        if(this.level() instanceof ServerLevel && tickCount % 20 == 0){
            Thread thread = new Thread(() -> makeParticle());
            thread.start();
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BEACON_POWER_SELECT;
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("moe_live_time", this.liveTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.liveTime = compound.getInt("moe_live_time");
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    public void makeParticle(){
        if(this.level().isClientSide()) return;
        List<Vec3> point = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 7), Mth.PI / 2, 0);
        Vec3 center = new Vec3(this.getX(), this.getY() + 0.1, this.getZ());
        for (int i = 0; i < point.size(); i++){
            Vec3 pos = center.add(point.get(i));
            ((ServerLevel)this.level()).sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255), point.get(i).scale(-0.2).toVector3f(), 5), pos.x(), pos.y(), pos.z(), 1, 0 ,0 ,0 ,0);
        }
    }
}
