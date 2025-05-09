package net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class PulsedPlasmaEntity extends AbstractArrow {
    private float plasmaDamage;
    public PulsedPlasmaEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PulsedPlasmaEntity(Level pLevel) {
        super(MoeEntities.PULSED_PLASMA_ENTITY.get(), pLevel);
    }
    public PulsedPlasmaEntity(LivingEntity pOwner, Level pLevel) {
        super(MoeEntities.PULSED_PLASMA_ENTITY.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        entity.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), this.getOwner()), 12);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Vec3 motion = getDeltaMovement();
        switch (result.getDirection()){
            case DOWN, UP -> this.setDeltaMovement(motion.x(), motion.y() * -1, motion.z());
            case NORTH, SOUTH -> this.setDeltaMovement(motion.x(), motion.y(), motion.z() * -1);
            case WEST, EAST -> this.setDeltaMovement(motion.x() * -1, motion.y(), motion.z());
        }
        double f = motion.horizontalDistance();
        this.setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(motion.y, f) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BEACON_POWER_SELECT;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void tick() {
        super.tick();
        if(++tickCount >= 10)
            this.discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("plasma_damage", this.plasmaDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.plasmaDamage = compound.getFloat("plasma_damage");
    }

    public void setPlasmaDamage(float plasmaDamage) {
        this.plasmaDamage = plasmaDamage;
    }

    public float getPlasmaDamage() {
        return plasmaDamage;
    }
}
