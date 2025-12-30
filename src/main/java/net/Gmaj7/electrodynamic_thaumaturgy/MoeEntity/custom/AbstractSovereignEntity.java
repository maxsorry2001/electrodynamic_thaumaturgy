package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;

public abstract class AbstractSovereignEntity extends PathfinderMob implements RangedAttackMob {
    protected int castTick = 0;
    protected int castAnim = 0;
    public final AnimationState castAnimationState = new AnimationState();
    protected AbstractSovereignEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public abstract void setCastTick(int castTick);
}
