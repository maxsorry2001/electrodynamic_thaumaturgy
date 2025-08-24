package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class PulsedPlasmaEntity extends AbstractArrow {
    private ItemStack magicItem;
    public PulsedPlasmaEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PulsedPlasmaEntity(Level pLevel) {
        super(MoeEntities.PULSED_PLASMA_ENTITY.get(), pLevel);
    }
    public PulsedPlasmaEntity(LivingEntity pOwner, Level pLevel, ItemStack itemStack) {
        super(MoeEntities.PULSED_PLASMA_ENTITY.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(pOwner.getX(), pOwner.getEyeY() - 0.1, pOwner.getZ());
        this.magicItem = itemStack.copy();
    }

    public PulsedPlasmaEntity(LivingEntity pOwner, Vec3 start, Level pLevel, ItemStack itemStack) {
        super(MoeEntities.PULSED_PLASMA_ENTITY.get(), pLevel);
        this.setOwner(pOwner);
        this.setPos(start.x(), start.y(), start.z());
        this.magicItem = itemStack.copy();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity && magicItem != null) {
            entity.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), this.getOwner()), MoeFunction.getMagicAmount(magicItem));
            MoeFunction.checkTargetEnhancement(magicItem, (LivingEntity) entity);
        }
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
        return new ItemStack(Items.ARROW);
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
        compound.put("moe_magic_item", this.magicItem.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.magicItem = ItemStack.parse(this.registryAccess(), compound.getCompound("moe_magic_item")).get();
    }
}
