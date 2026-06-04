package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.CastTickPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeTabs;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinitionLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class MagnetoEntropyWitchEntity extends AbstractSovereignEntity implements Enemy {
    private final ServerBossEvent bossEvent = new ServerBossEvent(Mth.createInsecureUUID(this.level().getRandom()), Component.translatable("entity.electrodynamic_thaumaturgy.magneto_entropy_witch_entity"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_10);
    protected final RandomSource random = RandomSource.create();
    protected int[] coolDown = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
    private final List<String> magic = new ArrayList<>(){{
        add("ray");
        add("pulsed_plasma");
        add("lighting_strike");
        add("attract");
        add("magnetic_flux_cascade");
        add("magnetic_recombination_cannon");
        add("frequency_division_arrow_rain");
    }};
    private RandomSource randomSource = RandomSource.create();
    public MagnetoEntropyWitchEntity(EntityType<? extends AbstractSovereignEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MagnetoEntropyWitchEntity(Level pLevel) {
        super(MoeEntities.MAGNETO_ENTROPY_WITCH_ENTITY.get(), pLevel);
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
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("cast_tick", this.castTick);
        output.putIntArray("cool_down", this.coolDown);
        output.putInt("cast_anim", this.castAnim);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.castTick = input.getInt("cast_tick").get();
        this.coolDown = input.getIntArray("cool_down").get();
        this.castAnim = input.getInt("cast_anim").get();
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
        MagicDefinition magicDefinition = MagicDefinitionLoader.get(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, magic.get(magicSelect)));
        if(magicDefinition != null)
            level().registryAccess().lookupOrThrow(MoeRegistries.MAGIC_KEY).getOrThrow(magicDefinition.behaviorKey()).value().mobCast(this, target, MoeTabs.getCopperRod(), magicDefinition);
        this.castTick = 10;
        this.castAnim = random.nextInt(2);
        PacketDistributor.sendToAllPlayers(new CastTickPacket(this.getId(), this.castTick));
        this.coolDown[magicSelect] = magicDefinition.baseCooldown();
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
}
