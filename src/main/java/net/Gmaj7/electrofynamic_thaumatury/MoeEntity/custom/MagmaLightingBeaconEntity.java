package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class MagmaLightingBeaconEntity extends AbstractArrow {
    private ItemStack magicItem;
    private int opentick = 0;
    private Direction direction = Direction.UP;

    public MagmaLightingBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public MagmaLightingBeaconEntity(Level level, LivingEntity owner, ItemStack itemStack) {
        super(MoeEntities.MAGMA_LIGHTING_BEACON_ENTITY.get(), level);
        this.magicItem = itemStack.copy();
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        opentick++;
        if(opentick == 20 && !level().isClientSide()){
            level().setBlockAndUpdate(getOnPos().relative(direction), Fluids.FLOWING_LAVA.defaultFluidState().createLegacyBlock());
            LightningBolt lightningBolt1 = EntityType.LIGHTNING_BOLT.create(level());
            lightningBolt1.teleportTo(this.getX(), this.getY(), this.getZ());
            lightningBolt1.setVisualOnly(true);
            level().addFreshEntity(lightningBolt1);
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(getOnPos()).inflate(5));
            for (LivingEntity target : list){
                if(target != getOwner() && magicItem != null){
                    target.hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), getOwner()), MoeFunction.getMagicAmount(magicItem));
                    MoeFunction.checkTargetEnhancement(magicItem, target);
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level());
                    lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
                    lightningBolt.setVisualOnly(true);
                    level().addFreshEntity(lightningBolt);
                }
            }
        }
        if(opentick > 20)
            this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
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
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("open_tick", opentick);
        compound.put("moe_magic_item", this.magicItem.save(this.registryAccess()));
        compound.putString("moe_ml_direction", this.direction.toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.opentick = compound.getInt("open_tick");
        this.magicItem = ItemStack.parse(this.registryAccess(), compound.getCompound("moe_magic_item")).get();
        this.direction = Direction.byName(compound.getString("moe_ml_direction"));
    }
}
