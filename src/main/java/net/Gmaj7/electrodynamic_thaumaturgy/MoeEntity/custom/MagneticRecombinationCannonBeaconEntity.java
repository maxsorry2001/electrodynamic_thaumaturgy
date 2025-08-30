package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class MagneticRecombinationCannonBeaconEntity extends AbstractArrow {
    private int startTime;
    private ItemStack itemStack;
    public MagneticRecombinationCannonBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public MagneticRecombinationCannonBeaconEntity(Level level){
        super(MoeEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public MagneticRecombinationCannonBeaconEntity(Level level, LivingEntity owner, ItemStack itemStack){
        super(MoeEntities.MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = itemStack.copy();
    }

    @Override
    public void tick() {
        super.tick();
        this.startTime++;
        if(startTime == 100 && itemStack != null){
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(7));
            for (LivingEntity target : list){
                if(target != this.getOwner() && target.getY() >= this.getBlockY() - 3 && !(this.getOwner() instanceof HarmonicSaintEntity && target == ((HarmonicSaintEntity) this.getOwner()).getOwner())) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), this.getOwner()), (int) MoeFunction.getMagicAmount(itemStack) * 2);
                    MoeFunction.checkTargetEnhancement(itemStack, target);
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("moe_start_time", this.startTime);
        compound.put("moe_magic_item", this.itemStack.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.startTime = compound.getInt("moe_start_time");
        this.itemStack = ItemStack.parse(this.registryAccess(), compound.getCompound("moe_magic_item")).get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public int getStartTime() {
        return startTime;
    }
}
