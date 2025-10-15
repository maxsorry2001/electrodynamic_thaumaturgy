package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PhotoCorrosiveNovaEntity extends Entity {
    public final AnimationState animationState = new AnimationState();
    public int animationTime = 10;
    public LivingEntity owner;
    public PhotoCorrosiveNovaEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public PhotoCorrosiveNovaEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner){
        super(MoeEntities.PHOTO_CORROSIVE_NOVA_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    private void setAnimationStates(){
        if(this.animationTime <= 1){
            this.animationTime = 10;
            this.animationState.start(this.tickCount);
        }
        else {
            this.animationTime --;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            this.setAnimationStates();
        }
    }
}
