package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MirageEntity extends AbstractArrow {
    private int livetick = 400;
    List<CastTarget> list = new ArrayList<>();
    private int castTick = 10;
    private RandomSource randomSource = RandomSource.create();


    public MirageEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MirageEntity(Level level, LivingEntity owner) {
        super(MoeEntities.MIRAGE_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
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
                CopyOnWriteArrayList<CastTarget> list1 = new CopyOnWriteArrayList<>(list);
                Iterator<CastTarget> iterator = list1.iterator();
                while (iterator.hasNext()){
                    CastTarget castTarget = iterator.next();
                    castTarget.getTarget().hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.mirage), getOwner()), castTarget.getDamage());
                }
                list = new ArrayList<>();
                castTick = 10;
            }
        }
        if(!level().isClientSide()){
                ((ServerLevel) level()).sendParticles(ParticleTypes.CLOUD, getX() + (randomSource.nextFloat() - 0.5), getY() + randomSource.nextFloat(), getZ() + (randomSource.nextFloat() - 0.5), 7, 0, 0, 0, 0);
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

        public double getX(){
            return target.getX();
        }

        public double getY(){
            return target.getY();
        }

        public double getZ(){
            return target.getZ();
        }
    }
}
