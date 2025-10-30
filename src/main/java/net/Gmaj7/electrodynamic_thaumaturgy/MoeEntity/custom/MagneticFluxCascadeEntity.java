package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class MagneticFluxCascadeEntity extends AbstractArrow {
    ItemStack magicItem;
    private final int hitTick = 10;
    private int hitTime = 5;
    protected LivingEntity target;
    public MagneticFluxCascadeEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MagneticFluxCascadeEntity(Level level){
        super(MoeEntities.MAGNETIC_FLUX_CASCADE_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public MagneticFluxCascadeEntity(Level level, LivingEntity owner, ItemStack itemStack) {
        super(MoeEntities.MAGNETIC_FLUX_CASCADE_ENTITY.get(), level);
        this.magicItem = itemStack.copy();
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.tickCount % hitTick == 5 && !this.level().isClientSide()){
            Thread thread = new Thread(() -> makeParticle(false));
            thread.start();
        }
        if(this.tickCount % hitTick == 0 && magicItem != null){
            float damage = MoeFunction.getMagicAmount(magicItem);
            target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumaturgy), this.getOwner()), damage);
            hitTime --;
            if(hitTime == 0){
                this.discard();
                return;
            }
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(target.getOnPos()).inflate(7, 2, 7));
            LivingEntity newTarget = list.get(RandomSource.create().nextInt(list.size()));
            while (newTarget == getOwner() || !newTarget.isAlive() || (this.getOwner() instanceof HarmonicSaintEntity && newTarget == ((HarmonicSaintEntity) this.getOwner()).getOwner())) {
                list.remove(newTarget);
                if(list.isEmpty()){
                    this.discard();
                    return;
                }
                newTarget = list.get(RandomSource.create().nextInt(list.size()));
            }
            this.target = newTarget;
            this.teleportTo(newTarget.getX(), newTarget.getY(), newTarget.getZ());
            if(this.level() instanceof ServerLevel){
                Thread thread = new Thread(() -> makeParticle(true));
                thread.start();
            }
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

    public void makeParticle(boolean out){
        if(this.level().isClientSide()) return;
        List<Vec3> point = MoeFunction.rotatePointsYX(MoeFunction.getCirclePoints(30, 2), Mth.PI / 2, 0);
        Vec3 center = new Vec3(target.getX(), (target.getY() + target.getEyeY()) / 2, target.getZ());
        for (int i = 0; i < point.size(); i++){
            Vec3 pos = center.add(point.get(i));
            if(out)
                ((ServerLevel)this.level()).sendParticles(new PointLineParticleOption(pos.toVector3f(), new Vector3f(128, 255, 128), point.get(i).scale(0.2).toVector3f(), 5), center.x(), center.y(), center.z(), 1, 0 ,0 ,0 ,0);
            else
                ((ServerLevel)this.level()).sendParticles(new PointLineParticleOption(center.toVector3f(), new Vector3f(255, 128, 128), point.get(i).scale(-0.2).toVector3f(), 5), pos.x(), pos.y(), pos.z(), 1, 0 ,0 ,0 ,0);
        }
    }
}
