package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MagneticFluxCascadeEntity extends AbstractArrow {
    ItemStack magicItem;
    private final int hitTick = 10;
    private int hitTime = 5;
    protected LivingEntity target;
    public MagneticFluxCascadeEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MagneticFluxCascadeEntity(Level level, LivingEntity owner, ItemStack itemStack) {
        super(MoeEntities.MAGNETIC_FLUX_CASCADE_ENTITY.get(), level);
        this.magicItem = itemStack.copy();
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount % hitTick == 0 && magicItem != null){
            target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury)), MoeFunction.getMagicAmount(magicItem));
            hitTime --;
            if(hitTime == 0){
                this.discard();
                return;
            }
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(target.getOnPos()).inflate(7, 2, 7));
            LivingEntity newTarget = list.get(RandomSource.create().nextInt(list.size()));
            while (newTarget == getOwner() && !newTarget.isAlive()) newTarget = list.get(RandomSource.create().nextInt(list.size()));
            this.target = newTarget;
            this.teleportTo(newTarget.getX(), newTarget.getY(), newTarget.getZ());
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

    @Override
    public boolean isPickable() {
        return false;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }
}
