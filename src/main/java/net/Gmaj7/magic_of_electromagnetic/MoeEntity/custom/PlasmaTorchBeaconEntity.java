package net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.Gmaj7.magic_of_electromagnetic.MoeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class PlasmaTorchBeaconEntity extends AbstractArrow {
    private int startTime;
    private ItemStack itemStack;
    public PlasmaTorchBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public PlasmaTorchBeaconEntity(Level level){
        super(MoeEntities.PLASMA_TORCH_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = MoeTabs.getDefaultMagicUse(MoeItems.ELECTROMAGNETIC_ROD.get());
    }

    public PlasmaTorchBeaconEntity(Level level, LivingEntity owner, ItemStack itemStack){
        super(MoeEntities.PLASMA_TORCH_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
        this.itemStack = itemStack.copy();
    }

    @Override
    public void tick() {
        super.tick();
        this.startTime++;
        if(startTime == 100){
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(5));
            for (LivingEntity target : list){
                if(target != this.getOwner() && Math.sqrt(Math.pow(target.getX() - this.getX(), 2) + Math.pow(target.getZ() - this.getZ(), 2)) <= 4.5 && target.getY() >= this.getBlockY()) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.LIGHTNING_BOLT), this.getOwner()), (int) MoeFunction.getMagicAmount(itemStack) * 10);
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
