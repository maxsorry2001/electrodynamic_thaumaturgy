package net.Gmaj7.magic_of_electromagnetic.mixin;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MirageEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeProtective;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension, MoeDataGet {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique private MoeProtective protective = new MoeProtective(0);
    @Unique private MirageEntity mirageEntity = null;

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    public void isCurrentlyGlowingMixin(CallbackInfoReturnable<Boolean> info){
        if(!this.level().isClientSide() && this.hasEffect(MoeEffects.EXCITING))
            info.setReturnValue(true);
    }

    @Unique
    public MoeProtective getProtective() {
        return this.protective;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    public void addAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        compound.putFloat("moe_protecting", protective.getProtecting());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    public void readAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci){
        if (compound.contains("moe_protecting")) {
            this.protective.setProtecting(compound.getFloat("moe_protecting"));
        }
    }

    @Override
    public void setMirageEntity(MirageEntity mirageEntity) {
        this.mirageEntity = mirageEntity;
    }

    @Override
    public boolean hasMirageEntity() {
        return this.mirageEntity != null;
    }

    @Override
    public MirageEntity getMirageEntity() {
        return mirageEntity;
    }
}
