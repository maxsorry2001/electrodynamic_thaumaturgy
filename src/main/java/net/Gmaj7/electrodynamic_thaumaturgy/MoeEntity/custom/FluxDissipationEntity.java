package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class FluxDissipationEntity extends Projectile {
    public FluxDissipationEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(MoeEntities.FLUX_DISSIPATION_ENTITY.get(), level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        this.getBoundingBox().inflate(1.1);
        super.tick();
    }
}
