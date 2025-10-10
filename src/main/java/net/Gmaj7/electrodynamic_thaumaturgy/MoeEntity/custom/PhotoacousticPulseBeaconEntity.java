package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PhotoacousticPulseBeaconEntity extends AbstractArrow {
    private ItemStack magicItem;
    private static final int maxTime = 30;
    public PhotoacousticPulseBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
    }

    protected PhotoacousticPulseBeaconEntity(Level level) {
        super(MoeEntities.PHOTOACOUSTIC_PULSE_BEACON_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
    }

    public PhotoacousticPulseBeaconEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner){
        super(MoeEntities.PHOTOACOUSTIC_PULSE_BEACON_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.magicItem = itemStack.copy();
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide())
            ((ServerLevel)level()).sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY() + 1, this.getZ(), 30, 0.2, 0.2, 0.2, 0);
        if(this.tickCount == maxTime){
            level().playSound(null, blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.BLOCKS, 3.0F, 1.0F);
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(6D));
            list.remove(this.getOwner());
            Vec3 vec3Start = new Vec3(this.getX(), this.getY() + 1, this.getZ());
            if(magicItem != null && !this.level().isClientSide()){
                for (LivingEntity target : list) {
                    target.hurt(new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, DamageTypes.SONIC_BOOM)), MoeFunction.getMagicAmount(this.magicItem));
                    Vec3 vec3End = target.getEyePosition();
                    Vec3 vec3Throw = vec3Start.vectorTo(vec3End);
                    Vec3 vec3Per = vec3Throw.normalize();
                    int x = Mth.floor(vec3Throw.length()) + 3;
                    for (int j = 1; j < x; j++) {
                        Vec3 vec3Point = vec3Start.add(vec3Per.scale(j));
                        ((ServerLevel)level()).sendParticles(ParticleTypes.SONIC_BOOM, vec3Point.x, vec3Point.y, vec3Point.z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
        if(this.tickCount > maxTime) this.discard();
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
    public boolean isPickable() {
        return false;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
