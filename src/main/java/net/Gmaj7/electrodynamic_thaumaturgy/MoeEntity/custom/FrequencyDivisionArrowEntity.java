package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FrequencyDivisionArrowEntity extends Arrow {
    private ItemStack magicItem;
    private int maxTime;
    private int explodeTime = 5;
    private Vec3 startPos;
    public FrequencyDivisionArrowEntity(EntityType<? extends Arrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public FrequencyDivisionArrowEntity(Level level){
        super(MoeEntities.FREQUENCY_DIVISION_ARROW_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public FrequencyDivisionArrowEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner, int maxTime){
        super(MoeEntities.FREQUENCY_DIVISION_ARROW_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.magicItem = itemStack;
        this.pickup = Pickup.DISALLOWED;
        this.maxTime = maxTime;
        this.startPos = new Vec3(x,y, z);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.inGround) explodeTime --;
        if(this.tickCount < maxTime)
            this.teleportTo(startPos.x(), startPos.y(), startPos.z());
        if(this.tickCount == maxTime){
            this.shoot(0, -1, 0, 2, 0);
        }
        if(explodeTime <= 0 && magicItem != null){
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5));
            if(list.contains(getOwner())) list.remove(getOwner());
            if (this.getOwner() instanceof HarmonicSaintEntity && list.contains(((HarmonicSaintEntity) this.getOwner()).getOwner())) list.remove(((HarmonicSaintEntity) this.getOwner()));
            for (LivingEntity target : list)
                target.hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), this.getOwner()), MoeFunction.getMagicAmount(magicItem) / 7);
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BEACON_ACTIVATE;
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    protected void applyGravity() {
        if (this.tickCount < maxTime) {
            this.setDeltaMovement(0.0, -0.001, 0.0);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity livingEntity && livingEntity != getOwner()){
            if(magicItem != null)
                livingEntity.hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), this.getOwner() instanceof HarmonicSaintEntity ? ((HarmonicSaintEntity) this.getOwner()).getOwner() : this.getOwner()), MoeFunction.getMagicAmount(magicItem));
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
