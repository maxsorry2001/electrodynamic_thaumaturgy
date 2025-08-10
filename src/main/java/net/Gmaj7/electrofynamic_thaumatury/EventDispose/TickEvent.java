package net.Gmaj7.electrofynamic_thaumatury.EventDispose;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEffect.MoeEffects;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class TickEvent {

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event){
        if(event.getItemStack().is(MoeItems.ENERGY_TRANSMISSION_ANTENNA.get())){
            event.getToolTip().add(Component.translatable("advancements.electrofynamic_thaumatury.energy_send.description"));
        }
    }

    @SubscribeEvent
    public static void effectRemoveDeal(MobEffectEvent.Remove event){
        Holder<MobEffect> holder = event.getEffect();
        if(holder == MoeEffects.MAGNETIC_LEVITATION_EFFECT){
            event.getEntity().getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(0);
        }
    }

    @SubscribeEvent
    public static void effectExpiredDeal(MobEffectEvent.Expired event){
        MobEffectInstance effectInstance = event.getEffectInstance();
        if(effectInstance.is(MoeEffects.MAGNETIC_LEVITATION_EFFECT)){
            event.getEntity().getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(0);
        }
    }
}
