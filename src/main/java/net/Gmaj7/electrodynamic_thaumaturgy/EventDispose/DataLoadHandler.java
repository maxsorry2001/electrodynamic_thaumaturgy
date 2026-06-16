package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.IDirectionFluidBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.IDirectionItemBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.IEnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.IItemBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.*;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.ItemAccessEnergyHandler;
import net.neoforged.neoforge.transfer.fluid.ItemAccessFluidHandler;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class DataLoadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar payloadRegistrar = event.registrar(ElectrodynamicThaumaturgy.MODID).versioned("1.0.0").optional();

        payloadRegistrar.playToServer(SelectMagicPacket.TYPE, SelectMagicPacket.STREAM_CODEC, SelectMagicPacket::handle);
        payloadRegistrar.playToServer(DirectionSetPacket.TYPE, DirectionSetPacket.STREAM_CODEC, DirectionSetPacket::handle);
        payloadRegistrar.playToServer(NetChangePacket.TYPE, NetChangePacket.STREAM_CODEC, NetChangePacket ::handle);
        payloadRegistrar.playToServer(ItemPipeNetFilterPacket.TYPE, ItemPipeNetFilterPacket.STREAM_CODEC, ItemPipeNetFilterPacket::handle);
        payloadRegistrar.playToServer(FluidPipeNetFilterPacket.TYPE, FluidPipeNetFilterPacket.STREAM_CODEC, FluidPipeNetFilterPacket::handle);
        payloadRegistrar.playToServer(FilterSettingItemPacket.TYPE, FilterSettingItemPacket.STREAM_CODEC, FilterSettingItemPacket::handle);
        payloadRegistrar.playToServer(FilterSettingWhitePacket.TYPE, FilterSettingWhitePacket.STREAM_CODEC, FilterSettingWhitePacket::handle);

        payloadRegistrar.playToClient(ProtectingPacket.TYPE, ProtectingPacket.STREAM_CODEC, ProtectingPacket::handle);
        payloadRegistrar.playToClient(EnergySetPacket.TYPE, EnergySetPacket.STREAM_CODEC, EnergySetPacket::handle);
        payloadRegistrar.playToClient(ThermalSetPacket.TYPE, ThermalSetPacket.STREAM_CODEC, ThermalSetPacket::handle);
        payloadRegistrar.playToClient(CastTickPacket.TYPE, CastTickPacket.STREAM_CODEC, CastTickPacket::handle);
        payloadRegistrar.playToClient(BiomassSetPacket.TYPE, BiomassSetPacket.STREAM_CODEC, BiomassSetPacket::handle);
        payloadRegistrar.playToClient(AtomicPacket.TYPE, AtomicPacket.STREAM_CODEC, AtomicPacket::handle);
        payloadRegistrar.playToClient(ItemPipeNetSynPacket.TYPE, ItemPipeNetSynPacket.STREAM_CODEC, ItemPipeNetSynPacket::handle);
        payloadRegistrar.playToClient(FluidPipeNetSynPacket.TYPE, FluidPipeNetSynPacket.STREAM_CODEC, FluidPipeNetSynPacket::handle);
        payloadRegistrar.playToClient(PipeNetSynPacket.TYPE, PipeNetSynPacket.STREAM_CODEC, PipeNetSynPacket::handle);

        payloadRegistrar.playBidirectional(ExtractorPacket.TYPE, ExtractorPacket.STREAM_CODEC, ExtractorPacket::handle, ExtractorPacket::handle);
    }


    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, access) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), EtDataComponentTypes.ET_ENERGY.get(), 49152)),
                EtItems.ELECTROMAGNETIC_ROD.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, access) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), EtDataComponentTypes.ET_ENERGY.get(), 16384, 0, 16384)),
                EtItems.POTATO_BATTERY.get(),
                EtItems.CARROT_BATTERY.get(),
                EtItems.SOLUTION_BATTERY.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, access) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), EtDataComponentTypes.ET_ENERGY.get(), 65536)),
                EtItems.POWER_BANK.get());
        event.registerItem(Capabilities.Energy.ITEM, ((itemStack, access) -> new ItemAccessEnergyHandler(ItemAccess.forStack(itemStack), EtDataComponentTypes.ET_ENERGY.get(), 536870912)),
                EtBlocks.ENERGY_BLOCK.get());
        event.registerItem(Capabilities.Fluid.ITEM,((itemStack, access) -> new ItemAccessFluidHandler(ItemAccess.forStack(itemStack), EtDataComponentTypes.FLUID_FILTER.get(), 1)),
                EtItems.FLUID_FILTER_FAKE_ITEM.get());
        event.registerBlock(Capabilities.Energy.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IEnergyBlockEntity ? ((IEnergyBlockEntity) blockEntity).getEnergy() : null),
                EtBlocks.ENERGY_BLOCK.get(),
                EtBlocks.TEMPERATURE_GENERATOR.get(),
                EtBlocks.PHOTOVOLTAIC_GENERATOR.get(),
                EtBlocks.THERMAL_GENERATOR.get(),
                EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get(),
                EtBlocks.BIO_REPLICATION_VAT_MACHINE.get(),
                EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get(),
                EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get(),
                EtBlocks.MAGNETO_FUSION_MACHINE.get(),
                EtBlocks.EDDY_CURRENT_REMELTER_MACHINE.get(),
                EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get(),
                EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get());
        event.registerBlock(Capabilities.Item.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IItemBlockEntity ? ((IItemBlockEntity) blockEntity).getItemHandler() : null),
                EtBlocks.ENERGY_BLOCK.get(),
                EtBlocks.THERMAL_GENERATOR.get(),
                EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get(),
                EtBlocks.BIO_REPLICATION_VAT_MACHINE.get(),
                EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get(),
                EtBlocks.BIOMASS_GENERATOR.get());
        event.registerBlock(Capabilities.Item.BLOCK, ((level, blockPos, blockState, blockEntity, direction) ->
                        blockEntity instanceof IDirectionItemBlockEntity ? ((IDirectionItemBlockEntity) blockEntity).getItemHandlerWithDirection(direction) : null),
                EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get(),
                EtBlocks.MAGNETO_FUSION_MACHINE.get(),
                EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get(),
                EtBlocks.EDDY_CURRENT_REMELTER_MACHINE.get());
        event.registerBlock(Capabilities.Fluid.BLOCK, ((level, pos, state, blockEntity, direction) ->
                        blockEntity instanceof IDirectionFluidBlockEntity ? ((IDirectionFluidBlockEntity) blockEntity).getFluidHandlerWithDirection(direction) : null),
                EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get());
    }
}
