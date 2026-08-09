package net.Gmaj7.electrodynamic_thaumaturgy.entity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MagnoAnnihilationCannonBullet extends AbstractArrow {
    private float damage;
    private int pattern;
    public MagnoAnnihilationCannonBullet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MagnoAnnihilationCannonBullet(Level level){
        super(EtEntities.MAGNO_ANNIHILATION_CANNON_BULLET_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MagnoAnnihilationCannonBullet(Level level, LivingEntity owner, float damage, int pattern){
        this(level, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), owner, damage, pattern);
    }

    public MagnoAnnihilationCannonBullet(Level level, double x, double y, double z, LivingEntity owner, float damage, int pattern){
        super(EtEntities.MAGNO_ANNIHILATION_CANNON_BULLET_ENTITY.get(), level);
        this.setOwner(owner);
        this.damage = damage;
        this.setPos(x, y, z);
        this.pickup = Pickup.DISALLOWED;
        this.pattern = pattern;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount >= 40) this.discard();
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
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putFloat("damage", damage);
        output.putInt("pattern", pattern);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        damage = input.getFloatOr("damage", 2F);
        pattern = input.getIntOr("pattern", 0);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity livingEntity && livingEntity != getOwner()){
            switch (pattern) {
                case 0 -> livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), livingEntity.getHealth() * Math.min(damage / 100, 0.4F));
                case 1 -> livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), (livingEntity.getMaxHealth() - livingEntity.getHealth()) * Math.min(damage / 100, 0.4F));
                default -> livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage * 2);
            }
            this.discard();
        }
    }

    private Entity getDamageOwner(){
        return this.getOwner() instanceof OwnableEntity ? ((OwnableEntity) this.getOwner()).getOwner() : this.getOwner();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}
