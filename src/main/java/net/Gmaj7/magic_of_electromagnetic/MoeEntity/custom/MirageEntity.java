package net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeEntity.MoeEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDamageType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MirageEntity extends AbstractArrow {
    private int livetick;
    List<CastTarget> list = new ArrayList<CastTarget>();
    private int castTick = 5;


    public MirageEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MirageEntity(Level level, LivingEntity owner) {
        super(MoeEntities.MAGMA_LIGHTING_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        livetick--;
        if(livetick <= 20) this.discard();
        this.teleportTo(getOwner().getX(), getOwner().getEyeY() + 1, getOwner().getZ());
        if(!list.isEmpty()) {
            castTick--;
            if(castTick <= 0) {
                castTick = 5;
                for (CastTarget castTarget : list) {
                    castTarget.getTarget().hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.magnet_resonance), getOwner()), castTarget.getDamage());
                }
            }
            list.clear();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {

    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void addTarget(CastTarget target) {
        list.add(target);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public static class CastTarget {
        float damage ;
        LivingEntity target;

        public CastTarget(float damage, LivingEntity target) {
            this.damage = damage;
            this.target = target;
        }

        public LivingEntity getTarget() {
            return target;
        }

        public float getDamage() {
            return damage;
        }
    }
}
