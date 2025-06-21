package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

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

    public AttractBeaconEntity(Level level, LivingEntity owner){
        super(MoeEntities.ATTRACT_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(++tickCount > liveTime && !this.level().isClientSide()) this.discard();
        List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(10));
        for (Entity target : list){
            if (target == this.getOwner()) continue;
            Vec3 vec3 = new Vec3(this.getX() - target.getX(), this.getY() - target.getY(), this.getZ() - target.getZ());
            Vec3 vec31 = vec3.normalize().multiply(0.1, 0.1, 0.1);
            target.move(MoverType.SELF, vec31);
        }
        if(this.level() instanceof ServerLevel serverLevel && tickCount % 20 == 0){
            BlockPos blockPos = this.getOnPos().above();
            serverLevel.getServer().getPlayerList().getPlayers().forEach(player -> serverLevel.sendParticles(player, MoeParticles.NORMAL_SHRINK_CIRCLE_PARTICLE.get(), false, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 0D, 0D, 0D, 0D));
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
}
