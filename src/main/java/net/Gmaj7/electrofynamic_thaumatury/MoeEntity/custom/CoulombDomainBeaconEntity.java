package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.Gmaj7.electrofynamic_thaumatury.MoeParticle.MoeParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class CoulombDomainBeaconEntity extends AbstractArrow {
    private ItemStack magicItem;
    private int liveTick = 101;


    public CoulombDomainBeaconEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public CoulombDomainBeaconEntity(Level level, LivingEntity owner, ItemStack itemStack) {
        super(MoeEntities.COULOMB_DOMAIN_BEACON_ENTITY.get(), level);
        this.magicItem = itemStack.copy();
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        liveTick--;
        if(liveTick <= 0){
            this.discard();
            return;
        }
        if(liveTick % 20 == 0 && magicItem != null){
            if(!level().isClientSide())
                ((ServerLevel) level()).sendParticles(MoeParticles.WILD_MAGIC_CIRCLE_PARTICLE.get(), getX(), getY()+ 1, getZ(), 1, 0, 0, 0, 0);
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(getOnPos()).inflate(7));
            for (LivingEntity target : list){
                if(target != getOwner() && magicItem != null){
                    target.hurt(new DamageSource(MoeFunction.getHolder(level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), getOwner()), MoeFunction.getMagicAmount(magicItem) / 3);
                    MoeFunction.checkTargetEnhancement(magicItem, target);
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level());
                    lightningBolt.teleportTo(target.getX(), target.getY(), target.getZ());
                    lightningBolt.setVisualOnly(true);
                    level().addFreshEntity(lightningBolt);
                }
            }
        }
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

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("live_tick", liveTick);
        compound.put("moe_magic_item", this.magicItem.save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.liveTick = compound.getInt("live_tick");
        this.magicItem = ItemStack.parse(this.registryAccess(), compound.getCompound("moe_magic_item")).get();
    }
}
