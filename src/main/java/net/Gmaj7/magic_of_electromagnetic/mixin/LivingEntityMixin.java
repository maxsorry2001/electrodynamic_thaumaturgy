package net.Gmaj7.magic_of_electromagnetic.mixin;

import net.Gmaj7.magic_of_electromagnetic.MoeEffect.MoeEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.neoforged.neoforge.common.extensions.ILivingEntityExtension{
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    public void isCurrentlyGlowingMixin(CallbackInfoReturnable<Boolean> info){
        if(!this.level().isClientSide() && this.hasEffect(MoeEffects.EXCITING))
            info.setReturnValue(true);
    }
}
