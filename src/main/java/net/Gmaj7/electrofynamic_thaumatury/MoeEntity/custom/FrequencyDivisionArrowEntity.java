package net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDamageType;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class FrequencyDivisionArrowEntity extends Arrow {
    private ItemStack magicItem;
    private int explodeTime = 20;
    public FrequencyDivisionArrowEntity(EntityType<? extends Arrow> entityType, Level level) {
        super(entityType, level);
    }

    public FrequencyDivisionArrowEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner){
        super(MoeEntities.FREQUENCY_DIVISION_ARROW_ENTITY.get(), level);
        this.setOwner(owner);
        this.setPos(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.inGround) explodeTime --;
        if(explodeTime <= 0){
            this.level().explode(null, new DamageSource(MoeFunction.getHolder(this.level(), Registries.DAMAGE_TYPE, MoeDamageType.origin_thaumatury), this.getOwner()), null, this.getX(), this.getY(), this.getZ(), 2.0F, false, Level.ExplosionInteraction.TNT);
            this.discard();
        }
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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    public void setMagicItem(ItemStack magicItem) {
        this.magicItem = magicItem;
    }
}
