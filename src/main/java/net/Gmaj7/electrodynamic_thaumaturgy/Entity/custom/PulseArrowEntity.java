package net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class PulseArrowEntity extends Arrow {
    private float damage;
    private Vec3 startPos;
    public PulseArrowEntity(EntityType<? extends Arrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public PulseArrowEntity(Level level){
        super(EtEntities.PULSE_ARROW_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public PulseArrowEntity(Level level, LivingEntity owner, float damage){
        this(level, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), owner, damage);
    }

    public PulseArrowEntity(Level level, double x, double y, double z, LivingEntity owner, float damage){
        super(EtEntities.PULSE_ARROW_ENTITY.get(), level);
        this.setOwner(owner);
        this.damage = damage;
        this.setPos(x, y, z);
        this.pickup = Pickup.DISALLOWED;
        this.startPos = new Vec3(x,y, z);
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
        return true;
    }


    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity livingEntity && livingEntity != getOwner()){
            livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), this.getOwner() instanceof OwnableEntity ? ((OwnableEntity) this.getOwner()).getOwner() : this.getOwner()), damage);
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
