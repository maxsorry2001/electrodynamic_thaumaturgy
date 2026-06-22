package net.Gmaj7.electrodynamic_thaumaturgy.Entity.custom;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.Gmaj7.electrodynamic_thaumaturgy.Entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDamageType;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class PulseArrowEntity extends Arrow {
    private float damage;
    private int pattern;
    private float count = 4;
    private boolean startCount = false;
    private @Nullable IntOpenHashSet piercingIgnoreEntityIds;
    public PulseArrowEntity(EntityType<? extends Arrow> entityType, Level level) {
        super(entityType, level);
        this.pickup = Pickup.DISALLOWED;
        piercingIgnoreEntityIds = new IntOpenHashSet();
    }

    public PulseArrowEntity(Level level){
        super(EtEntities.PULSE_ARROW_ENTITY.get(), level);
        this.pickup = Pickup.DISALLOWED;
        piercingIgnoreEntityIds = new IntOpenHashSet();
    }

    public PulseArrowEntity(Level level, LivingEntity owner, float damage, int pattern){
        this(level, owner.getX(), owner.getEyeY() - 0.1, owner.getZ(), owner, damage, pattern);
    }

    public PulseArrowEntity(Level level, double x, double y, double z, LivingEntity owner, float damage, int pattern){
        super(EtEntities.PULSE_ARROW_ENTITY.get(), level);
        this.setOwner(owner);
        this.damage = damage;
        this.setPos(x, y, z);
        this.pickup = Pickup.DISALLOWED;
        this.pattern = pattern;
        piercingIgnoreEntityIds = new IntOpenHashSet();
    }

    @Override
    public void tick() {
        super.tick();
        if(startCount) count -= 0.2F;
        if(count <= 0) this.discard();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.BEACON_ACTIVATE;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putFloat("count", count);
        output.putFloat("damage", damage);
        output.putInt("pattern", pattern);
        output.putBoolean("start_count", startCount);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        count = input.getIntOr("count", 0);
        damage = input.getFloatOr("damage", 2F);
        pattern = input.getIntOr("pattern", 0);
        startCount = input.getBooleanOr("start_count", false);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if(pattern == 1) {
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(result.getBlockPos()).inflate(4));
            if(getOwner() instanceof LivingEntity) list.remove(getOwner());
            if(getDamageOwner() instanceof LivingEntity) list.remove(getDamageOwner());
            for (LivingEntity target : list){
                target.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage * 0.8F);
            }
        }
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if(entity instanceof LivingEntity livingEntity && livingEntity != getOwner()){
            switch (pattern) {
                case 0 -> {
                    livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage * 2);
                    this.discard();
                }
                case 1 -> {
                    List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getOnPos()).inflate(4));
                    if(getOwner() instanceof LivingEntity) list.remove(getOwner());
                    if(getDamageOwner() instanceof LivingEntity) list.remove(getDamageOwner());
                    for (LivingEntity target : list){
                        target.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage * 0.7F);
                    }
                    this.discard();
                }
                case 2 -> {
                    livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage);
                    if(count > 0 && !piercingIgnoreEntityIds.contains(livingEntity.getId())){
                        count --;
                        piercingIgnoreEntityIds.add(livingEntity.getId());
                        this.damage = Math.max(2, damage * 0.75F);
                        if(!startCount) startCount = true;
                    }
                }
                default -> livingEntity.hurt(new DamageSource(Function.getHolder(level(), Registries.DAMAGE_TYPE, EtDamageType.origin_thaumaturgy), getDamageOwner()), damage);
            }
        }
    }

    private Entity getDamageOwner(){
        return this.getOwner() instanceof OwnableEntity ? ((OwnableEntity) this.getOwner()).getOwner() : this.getOwner();
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }
}
