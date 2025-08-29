package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.Gmaj7.electrofynamic_thaumatury.MoeTabs;
import net.Gmaj7.electrofynamic_thaumatury.magic.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SummonedHarmonicSovereignEntity extends HarmonicSovereignEntity {
    private Entity master;
    protected UUID masterUUID;
    private final List<IMoeMagic> magic = new ArrayList<>(){{
        add(new ElectromagneticRay());
        add(new PulsedPlasma());
        add(new LightingStrike());
        add(new Attract());
        add(new MagneticFluxCascade());
        add(new MagneticRecombinationCannon());
        add(new FrequencyDivisionArrowRain());
        add(new Attract());
    }};
    private RandomSource randomSource = RandomSource.create();
    public SummonedHarmonicSovereignEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public SummonedHarmonicSovereignEntity(Level pLevel) {
        super(MoeEntities.SUMMONED_HARMONIC_SOVEREIGN_ENTITY.get(), pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 40, 12F));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Mob.class, 5, false, false, (p_28879_) -> {
            return p_28879_ instanceof Enemy || ((Mob)p_28879_).getTarget() == this.getMaster();
        }));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 512D)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 5F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.masterUUID != null) {
            compound.putUUID("master", this.masterUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("master")) {
            this.masterUUID = compound.getUUID("master");
            this.master = null;
        }
    }

    public Entity getMaster() {
        if (this.master != null && !this.master.isRemoved()) {
            return this.master;
        } else {
            if (this.masterUUID != null) {
                Level var2 = this.level();
                if (var2 instanceof ServerLevel serverlevel) {
                    this.master = serverlevel.getEntity(this.masterUUID);
                    return this.master;
                }
            }
            return null;
        }
    }

    public void setMaster(Entity master) {
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
        magic.get(magicSelect).mobCast(this, target, MoeTabs.getCopperRod());
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

    @Override
    protected boolean isSummoned() {
        return true;
    }
}
