package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
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
        if(!level().isClientSide() && this.tickCount == 80){
            ((ServerLevel) level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_SMALL.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_MIDDLE.get(), this.getX(), this.getY() + 3, this.getZ(), 1, 0, 0, 0, 0);
            ((ServerLevel) level()).sendParticles(MoeParticles.MAGMA_LIGHTING_PARTICLE_LARGE.get(), this.getX(), this.getY() + 6, this.getZ(), 1, 0, 0, 0, 0);
        }
        if(summonTick < 0){
            HarmonicSovereignEntity harmonicSovereignEntity = (HarmonicSovereignEntity) MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get().create(level());
            harmonicSovereignEntity.teleportTo(getOnPos().getX() + 0.5, getOnPos().getY() + 1, getOnPos().getZ() + 0.5);
            level().addFreshEntity(harmonicSovereignEntity);
            for (int i = 1; i <= 8; i++){
                float r = i * 2 * Mth.PI / 8;
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level());
                lightningBolt.teleportTo(getX() + 4 * Mth.sin(r), getY(), getZ() + 4 * Mth.cos(r));
                lightningBolt.setVisualOnly(true);
                level().addFreshEntity(lightningBolt);
            }
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
