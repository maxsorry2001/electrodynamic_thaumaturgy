package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MagicOfElectromagnetic.MODID)
public class PayLoadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(MagicOfElectromagnetic.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(MoePacket.MoeSelectMagicPacket.TYPE, MoePacket.MoeSelectMagicPacket.STREAM_CODEC, MoePacket.MoeSelectMagicPacket::handle);
        payloadRegistrar.playToServer(MoePacket.LightingPacket.TYPE, MoePacket.LightingPacket.STREAM_CODEC, MoePacket.LightingPacket::handle);
    }
}
