package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public abstract class AbstractSovereignEntity extends PathfinderMob implements RangedAttackMob {
    protected int castTick = 0;
    protected int castAnim = 0;
    protected AbstractSovereignEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public abstract int getCastAnim();

    public abstract int getCastTick();

    public abstract boolean isCasting();

    public abstract void setCastTick(int castTick);

    public abstract void setCastAnim(int castAnim);
}
