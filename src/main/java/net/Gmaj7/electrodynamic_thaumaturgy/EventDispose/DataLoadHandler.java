package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.IMoeDirectionItemBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.IMoeEnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.IMoeItemBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.ItemAccessEnergyHandler;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class DataLoadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(ElectrodynamicThaumaturgy.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(MoeSelectMagicPacket.TYPE, MoeSelectMagicPacket.STREAM_CODEC, MoeSelectMagicPacket::handle);
        payloadRegistrar.playToServer(DirectionSetPacket.TYPE, DirectionSetPacket.STREAM_CODEC, DirectionSetPacket::handle);
        payloadRegistrar.playToServer(NetChangePacket.TYPE, NetChangePacket.STREAM_CODEC, NetChangePacket ::handle);
        payloadRegistrar.playToServer(ItemPipeNetFilterPacket.TYPE, ItemPipeNetFilterPacket.STREAM_CODEC, ItemPipeNetFilterPacket::handle);
        payloadRegistrar.playToServer(FilterSettingItemPacket.TYPE, FilterSettingItemPacket.STREAM_CODEC, FilterSettingItemPacket::handle);
        payloadRegistrar.playToServer(FilterSettingWhitePacket.TYPE, FilterSettingWhitePacket.STREAM_CODEC, FilterSettingWhitePacket::handle);

        payloadRegistrar.playToClient(ProtectingPacket.TYPE, ProtectingPacket.STREAM_CODEC, ProtectingPacket::handle);
        payloadRegistrar.playToClient(EnergySetPacket.TYPE, EnergySetPacket.STREAM_CODEC, EnergySetPacket::handle);
        payloadRegistrar.playToClient(ThermalSetPacket.TYPE, ThermalSetPacket.STREAM_CODEC, ThermalSetPacket::handle);
        payloadRegistrar.playToClient(CastTickPacket.TYPE, CastTickPacket.STREAM_CODEC, CastTickPacket::handle);
        payloadRegistrar.playToClient(BiomassSetPacket.TYPE, BiomassSetPacket.STREAM_CODEC, BiomassSetPacket::handle);
        payloadRegistrar.playToClient(AtomicPacket.TYPE, AtomicPacket.STREAM_CODEC, AtomicPacket::handle);
        payloadRegistrar.playToClient(ItemPipeNetSynPacket.TYPE, ItemPipeNetSynPacket.STREAM_CODEC, ItemPipeNetSynPacket::handle);
        payloadRegistrar.playToClient(PipeNetSynPacket.TYPE, PipeNetSynPacket.STREAM_CODEC, PipeNetSynPacket::handle);

        payloadRegistrar.playBidirectional(ExtractorPacket.TYPE, ExtractorPacket.STREAM_CODEC, ExtractorPacket::handle, ExtractorPacket::handle);
    }


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, unused) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), MoeDataComponentTypes.MOE_ENERGY.get(), 49152)),
                MoeItems.ELECTROMAGNETIC_ROD.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, unused) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), MoeDataComponentTypes.MOE_ENERGY.get(), 16384, 0, 16384)),
                MoeItems.POTATO_BATTERY.get(),
                MoeItems.CARROT_BATTERY.get(),
                MoeItems.SOLUTION_BATTERY.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, unused) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), MoeDataComponentTypes.MOE_ENERGY.get(), 65536)),
                MoeItems.POWER_BANK.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, unused) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), MoeDataComponentTypes.MOE_ENERGY.get(), 536870912)),
                MoeBlocks.ENERGY_BLOCK.get());
        event.registerBlock(Capabilities.Energy.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeEnergyBlockEntity ? ((IMoeEnergyBlockEntity) blockEntity).getEnergy() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get(),
                MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get(),
                MoeBlocks.THERMAL_GENERATOR_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(),
                MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get(),
                MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get(),
                MoeBlocks.MAGNETO_FUSION_MACHINE_BLOCK.get(),
                MoeBlocks.EDDY_CURRENT_REMELTER_MACHINE_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get());
        event.registerBlock(Capabilities.Item.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeItemBlockEntity ? ((IMoeItemBlockEntity) blockEntity).getItemHandler() : null),
                MoeBlocks.ENERGY_BLOCK.get(),
                MoeBlocks.THERMAL_GENERATOR_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(),
                MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get(),
                MoeBlocks.BIOMASS_GENERATOR_BLOCK.get());
        event.registerBlock(Capabilities.Item.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IMoeDirectionItemBlockEntity ? ((IMoeDirectionItemBlockEntity) blockEntity).getItemHandlerWithDirection(direction) : null),
                MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get(),
                MoeBlocks.MAGNETO_FUSION_MACHINE_BLOCK.get(),
                MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get(),
                MoeBlocks.EDDY_CURRENT_REMELTER_MACHINE_BLOCK.get());
    }
}
