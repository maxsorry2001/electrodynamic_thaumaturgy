package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.IMoeEnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.IMoeItemBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = EelectrodynamicThaumaturgy.MODID)
public class DataLoadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(EelectrodynamicThaumaturgy.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(MoePacket.MoeSelectMagicPacket.TYPE, MoePacket.MoeSelectMagicPacket.STREAM_CODEC, MoePacket.MoeSelectMagicPacket::handle);

        payloadRegistrar.playToClient(MoePacket.ProtectingPacket.TYPE, MoePacket.ProtectingPacket.STREAM_CODEC, MoePacket.ProtectingPacket::handle);
        payloadRegistrar.playToClient(MoePacket.EnergySetPacket.TYPE, MoePacket.EnergySetPacket.STREAM_CODEC, MoePacket.EnergySetPacket::handle);
        payloadRegistrar.playToClient(MoePacket.ThermalSetPacket.TYPE, MoePacket.ThermalSetPacket.STREAM_CODEC, MoePacket.ThermalSetPacket::handle);
        payloadRegistrar.playToClient(MoePacket.CastTickPacket.TYPE, MoePacket.CastTickPacket.STREAM_CODEC, MoePacket.CastTickPacket::handle);
    }


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 49152)),
                MoeItems.ELECTROMAGNETIC_ROD.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 16384, 0, 16384)),
                MoeItems.POTATO_BATTERY.get(),
                MoeItems.CARROT_BATTERY.get(),
                MoeItems.SOLUTION_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65536)),
                MoeItems.POWER_BANK.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 16777216)),
                MoeItems.ENERGY_BLOCK.get());
        event.registerBlock(Capabilities.EnergyStorage.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeEnergyBlockEntity ? ((IMoeEnergyBlockEntity) blockEntity).getEnergy() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get(),
                MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get(),
                MoeBlocks.THERMAL_GENERATOR_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(),
                MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get(),
                MoeBlocks.GEOLOGICAL_METAL_EXCAVATOR_MACHINE_BLOCK.get(),
                MoeBlocks.NITROGEN_HARVESTER_BLOCK.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeItemBlockEntity ? ((IMoeItemBlockEntity) blockEntity).getItemHandler() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.THERMAL_GENERATOR_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(),
                MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get(),
                MoeBlocks.GEOLOGICAL_METAL_EXCAVATOR_MACHINE_BLOCK.get(),
                MoeBlocks.NITROGEN_HARVESTER_BLOCK.get());
    }
}
