package net.Gmaj7.electrodynamic_thaumaturgy.mixin;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.PulseBow;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UseDuration.class)
public abstract class UseDurationMixin implements RangeSelectItemModelProperty {

    @Inject(method = "get", at = @At(value = "RETURN"), cancellable = true)
    public void getMixin(ItemStack itemStack, ClientLevel level, ItemOwner owner, int seed, CallbackInfoReturnable<Float> cir){
        float original = cir.getReturnValueF();
        if (original <= 0) return;

        // 只对弓生效（可选）
        if (!(itemStack.getItem() instanceof PulseBow)) return;

        float speedMultiplier = itemStack.get(EtDataComponentTypes.ENHANCEMENT_DATA).coolDown();
        float accelerated = original * speedMultiplier;
        cir.setReturnValue(accelerated);
    }
}
