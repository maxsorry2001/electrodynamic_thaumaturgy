package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.MoeEffects;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = EelectrodynamicThaumaturgy.MODID)
public class TickEvent {

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event){
        if(event.getItemStack().is(MoeItems.ENERGY_TRANSMISSION_ANTENNA.get())){
            event.getToolTip().add(Component.translatable("advancements.electrodynamic_thaumaturgy.energy_send.description"));
        }
        if(event.getItemStack().is(MoeItems.GENETIC_RECORDER.get()) && event.getItemStack().get(MoeDataComponentTypes.ENTITY_TYPE) != null && event.getItemStack().get(MoeDataComponentTypes.ENTITY_DATA) != null)
            event.getToolTip().add(BuiltInRegistries.ENTITY_TYPE.getOptional(event.getItemStack().get(MoeDataComponentTypes.ENTITY_TYPE)).get().getDescription());
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
