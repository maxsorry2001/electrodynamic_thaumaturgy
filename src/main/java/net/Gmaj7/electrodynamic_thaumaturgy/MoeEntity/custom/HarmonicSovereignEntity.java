package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class HarmonicSovereignEntity extends AbstractSovereignEntity implements Enemy {
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.electrodynamic_thaumaturgy.harmonic_sovereign"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_10);
    protected final RandomSource random = RandomSource.create();
    protected int[] coolDown = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
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
    public HarmonicSovereignEntity(EntityType<? extends AbstractSovereignEntity> entityType, Level level) {
        super(entityType, level);
    }

    public HarmonicSovereignEntity(Level pLevel) {
        super(MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get(), pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 40, 12F));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Wolf.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
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
        compound.putInt("cast_tick", this.castTick);
        compound.putIntArray("cool_down", this.coolDown);
        compound.putInt("cast_anim", this.castAnim);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.castTick = compound.getInt("cast_tick");
        this.coolDown = compound.getIntArray("cool_down");
        this.castAnim = compound.getInt("cast_anim");
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
        if(this.level().getDifficulty() == Difficulty.PEACEFUL) this.discard();
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
        this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
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

}
