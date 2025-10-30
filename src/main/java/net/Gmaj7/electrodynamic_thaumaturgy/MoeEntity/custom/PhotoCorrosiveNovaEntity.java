package net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PhotoCorrosiveNovaEntity extends Entity {
    public final AnimationState animationState = new AnimationState();
    public int animationTime = 10;
    public LivingEntity owner;
    public int amplifier;
    public PhotoCorrosiveNovaEntity(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    public PhotoCorrosiveNovaEntity(Level level, double x, double y, double z, ItemStack itemStack, LivingEntity owner){
        super(MoeEntities.PHOTO_CORROSIVE_NOVA_ENTITY.get(), level);
        this.setOwner(owner);
        this.amplifier = Math.max ((int) MoeFunction.getMagicAmount(itemStack) / 2, 1);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    private void setAnimationStates(){
        if(this.animationTime <= 1){
            this.animationTime = 10;
            this.animationState.start(this.tickCount);
        }
        else {
            this.animationTime --;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            this.setAnimationStates();
            return;
        }
        if(!this.level().getBlockState(this.blockPosition()).is(MoeBlocks.LIGHT_AIR)){
            this.level().setBlockAndUpdate(this.blockPosition(), MoeBlocks.LIGHT_AIR.get().defaultBlockState());
        }
        if(this.tickCount % 20 == 0){
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(10));
            if(owner != null)
                list.remove(owner);
            for (LivingEntity target : list)
                target.addEffect(new MobEffectInstance(MoeEffects.PHOTO_CORROSIVE, 100, amplifier));
        }
        if(this.tickCount >= 160) {
            this.level().setBlockAndUpdate(this.blockPosition(), Blocks.AIR.defaultBlockState());
            this.discard();
        }
    }
}
