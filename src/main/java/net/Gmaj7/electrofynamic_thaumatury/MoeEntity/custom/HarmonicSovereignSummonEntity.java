package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class HarmonicSovereignSummonEntity extends AbstractArrow {
    private int summonTick = 100;
    public HarmonicSovereignSummonEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    public HarmonicSovereignSummonEntity(Level pLevel) {
        super(MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get(), pLevel);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        this.summonTick --;
        if(summonTick < 0){
            HarmonicSovereignEntity harmonicSovereignEntity = (HarmonicSovereignEntity) MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get().create(level());
            harmonicSovereignEntity.teleportTo(getOnPos().getX() + 0.5, getOnPos().getY() + 1, getOnPos().getZ() + 0.5);
            level().addFreshEntity(harmonicSovereignEntity);
            this.discard();
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
