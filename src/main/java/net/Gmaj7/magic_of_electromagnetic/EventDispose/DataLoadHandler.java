package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.IMoeEnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.IMoeItemBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = MagicOfElectromagnetic.MODID)
public class DataLoadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(MagicOfElectromagnetic.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(MoePacket.MoeSelectMagicPacket.TYPE, MoePacket.MoeSelectMagicPacket.STREAM_CODEC, MoePacket.MoeSelectMagicPacket::handle);

        payloadRegistrar.playToClient(MoePacket.ProtectingPacket.TYPE, MoePacket.ProtectingPacket.STREAM_CODEC, MoePacket.ProtectingPacket::handle);
        payloadRegistrar.playToClient(MoePacket.EnergySetPacket.TYPE, MoePacket.EnergySetPacket.STREAM_CODEC, MoePacket.EnergySetPacket::handle);
    }


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 32768)),
                MoeItems.ELECTROMAGNETIC_ROD.get(),
                MoeItems.ELECTROMAGNETIC_BOOK.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 16384, 0, 16384)),
                MoeItems.FE_CU_POTATO_BATTERY.get(),
                MoeItems.FE_CU_CARROT_BATTERY.get(),
                MoeItems.FE_CU_SOLUTION_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM, ((itemStack, unused) -> new ComponentEnergyStorage(itemStack, MoeDataComponentTypes.MOE_ENERGY.get(), 65536)),
                MoeItems.ENERGY_BLOCK.get());
        event.registerBlock(Capabilities.EnergyStorage.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeEnergyBlockEntity ? ((IMoeEnergyBlockEntity) blockEntity).getEnergy() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK.get(),
                MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK.get(),
                MoeBlocks.THERMAL_ENERGY_MAKER_BLOCK.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeItemBlockEntity ? ((IMoeItemBlockEntity) blockEntity).getItemHandler() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.THERMAL_ENERGY_MAKER_BLOCK.get());
    }
}
