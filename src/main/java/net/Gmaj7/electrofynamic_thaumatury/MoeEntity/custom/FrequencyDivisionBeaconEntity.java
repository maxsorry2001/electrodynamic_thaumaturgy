package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class FrequencyDivisionBeaconEntity extends Arrow {
    private ItemStack magicItem;
    private static final int maxTime = 50;
    private int left = -4, right = 3, top = -3, bottom = 3;
    private int dx = -3, dz = -3;
    public FrequencyDivisionBeaconEntity(EntityType<? extends Arrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public FrequencyDivisionBeaconEntity(Level level){
        super(MoeEntities.FREQUENCY_DIVISION_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public FrequencyDivisionBeaconEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner){
        super(MoeEntities.FREQUENCY_DIVISION_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.magicItem = itemStack;
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        FrequencyDivisionArrowEntity frequencyDivisionArrowEntity = new FrequencyDivisionArrowEntity(level(), this.getX() + dx, this.getY() + 10, this.getZ() + dz, magicItem, (LivingEntity) getOwner(), 60 - this.tickCount);
        frequencyDivisionArrowEntity.setDeltaMovement(0.0, -0.001, 0.0);
        this.level().addFreshEntity(frequencyDivisionArrowEntity);
        if(this.tickCount < maxTime){
            if (dx < right && dz == top) {
                dx++;
                if (dx == right) left++;
            } else if (dx == right && dz < bottom) {
                dz++;
                if (dz == bottom) top++;
            } else if (dx > left && dz == bottom) {
                dx--;
                if (dx == left) right--;
            } else if (dx == left && dz > top) {
                dz--;
                if (dz == top) bottom--;
            }
        }
        if(this.tickCount == maxTime){
            this.shoot(0, -1, 0, 2, 0);
        }
        if(this.tickCount > maxTime){
            this.discard();
        }
    }

    @Override
    public boolean isPickable() {
        return false;
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
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
