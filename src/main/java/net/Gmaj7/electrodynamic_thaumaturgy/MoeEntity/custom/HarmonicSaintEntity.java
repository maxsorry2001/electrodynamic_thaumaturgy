package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class HarmonicSaintEntity extends AbstractSovereignEntity implements OwnableEntity {
    private LivingEntity master;
    protected UUID masterUUID;
    private int liveTick;
    protected int[] coolDown = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
    private final List<IMoeMagic> magic = new ArrayList<>(){{
        add(new ElectromagneticRay());
        add(new PulsedPlasma());
        add(new LightingStrike());
        add(new Attract());
        add(new MagneticFluxCascade());
        add(new MagneticRecombinationCannon());
        add(new HydrogenBondFracture());
    }};
    private RandomSource randomSource = RandomSource.create();
    public HarmonicSaintEntity(EntityType<? extends AbstractSovereignEntity> entityType, Level level) {
        super(entityType, level);
    }

    public HarmonicSaintEntity(Level pLevel) {
        super(MoeEntities.HARMONIC_SAINT_ENTITY.get(), pLevel);
    }

    public HarmonicSaintEntity(Level level, LivingEntity master, int liveTick){
        super(MoeEntities.HARMONIC_SAINT_ENTITY.get(), level);
        this.setOwner(master);
        this.teleportRelative(master.getX(), master.getY(), master.getZ());
        this.liveTick = liveTick;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 40, 12F));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(4, new followGoal(this, 1.0, 10.0F, 5.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Mob.class, 5, false, false, (p_28879_) -> {
            return p_28879_ instanceof Enemy || ((Mob)p_28879_).getTarget() == this.getOwner();
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 512D)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 5F);
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return masterUUID;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        if (this.master != null && !this.master.isRemoved()) {
            return this.master;
        } else {
            if (this.masterUUID != null) {
                Level var2 = this.level();
                if (var2 instanceof ServerLevel serverlevel) {
                    this.master = (LivingEntity) serverlevel.getEntity(this.masterUUID);
                    return this.master;
                }
            }
            return null;
        }
    }

    public void setOwner(LivingEntity master) {
        if(master != null) {
            this.master = master;
            this.masterUUID = master.getUUID();
        }
    }

    public void setCastTick(int castTick) {
        this.castTick = castTick;
    }

    public int getCastTick() {
        return castTick;
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide()) {
            this.liveTick--;
            if (this.liveTick <= 0) this.discard();
        }
        if(this.castTick > 0) this.castTick--;
        if(coolDown.length < 8) this.coolDown = new int[]{0,0,0,0,0,0,0};
        for (int i = 0; i < coolDown.length; i++){
            if(coolDown[i] > 0) coolDown[i]--;
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float v) {
        int magicSelect = randomSource.nextInt(magic.size());
        while (coolDown[magicSelect] > 0) magicSelect = randomSource.nextInt(magic.size());
        magic.get(magicSelect).mobCast(this, target, MoeTabs.getSuperconductingRod());
        this.castTick = 10;
        this.castAnim = random.nextInt(2);
        PacketDistributor.sendToAllPlayers(new MoePacket.CastTickPacket(this.getId(), this.castTick, this.castAnim));
        this.coolDown[magicSelect] = magic.get(magicSelect).getBaseCooldown();
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    public boolean isCasting(){
        return this.castTick > 0;
    }

    public void setCastAnim(int castAnim) {
        this.castAnim = castAnim;
    }

    public int getCastAnim() {
        return castAnim;
    }

    public final boolean unableToMoveToOwner() {
        return this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public boolean shouldTryTeleportToOwner() {
        LivingEntity livingentity = this.getOwner();
        return livingentity != null && this.distanceToSqr(this.getOwner()) >= 144.0;
    }

    public void tryToTeleportToOwner() {
        LivingEntity livingentity = this.getOwner();
        if (livingentity != null) {
            this.teleportToAroundBlockPos(livingentity.blockPosition());
        }
    }

    private void teleportToAroundBlockPos(BlockPos pos) {
        for(int i = 0; i < 10; ++i) {
            int j = this.random.nextIntBetweenInclusive(-3, 3);
            int k = this.random.nextIntBetweenInclusive(-3, 3);
            if (Math.abs(j) >= 2 || Math.abs(k) >= 2) {
                int l = this.random.nextIntBetweenInclusive(-1, 1);
                if (this.maybeTeleportTo(pos.getX() + j, pos.getY() + l, pos.getZ() + k)) {
                    return;
                }
            }
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.moveTo((double)x + 0.5, (double)y, (double)z + 0.5, this.getYRot(), this.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos pos) {
        PathType pathtype = WalkNodeEvaluator.getPathTypeStatic(this, pos);
        if (pathtype != PathType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level().getBlockState(pos.below());
            if (blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pos.subtract(this.blockPosition());
                return this.level().noCollision(this, this.getBoundingBox().move(blockpos));
            }
        }
    }

    private class followGoal extends Goal{
        private final HarmonicSaintEntity harmonicSaintEntity;
        @javax.annotation.Nullable
        private LivingEntity owner;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;

        public followGoal(HarmonicSaintEntity harmonicSaintEntity, double speedModifier, float startDistance, float stopDistance) {
            this.harmonicSaintEntity = harmonicSaintEntity;
            this.speedModifier = speedModifier;
            this.navigation = harmonicSaintEntity.getNavigation();
            this.startDistance = startDistance;
            this.stopDistance = stopDistance;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            if (!(harmonicSaintEntity.getNavigation() instanceof GroundPathNavigation) && !(harmonicSaintEntity.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canUse() {
            LivingEntity livingentity = this.harmonicSaintEntity.getOwner();
            if (livingentity == null) {
                return false;
            } else if (this.harmonicSaintEntity.unableToMoveToOwner()) {
                return false;
            } else if (this.harmonicSaintEntity.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = livingentity;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else {
                return this.harmonicSaintEntity.unableToMoveToOwner() ? false : !(this.harmonicSaintEntity.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.harmonicSaintEntity.getPathfindingMalus(PathType.WATER);
            this.harmonicSaintEntity.setPathfindingMalus(PathType.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.harmonicSaintEntity.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
        }

        public void tick() {
            boolean flag = this.harmonicSaintEntity.shouldTryTeleportToOwner();
            if (!flag) {
                this.harmonicSaintEntity.getLookControl().setLookAt(this.owner, 10.0F, (float)this.harmonicSaintEntity.getMaxHeadXRot());
            }

            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (flag) {
                    this.harmonicSaintEntity.tryToTeleportToOwner();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }
            }
        }

    }
}
