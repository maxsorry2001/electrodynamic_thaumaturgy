package net.Gmaj7.electrofynamic_thaumatury.MoeBlock;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOE_BLOCK_ENTITY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MagicOfElectromagnetic.MODID);

    public static final Supplier<BlockEntityType<EnergyBlockEntity>> ENERGY_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("energy_block_be", () -> BlockEntityType.Builder.of(
                    EnergyBlockEntity::new, MoeBlocks.ENERGY_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<TemperatureGeneratorBE>> TEMPERATURE_GENERATOR_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("temperature_generator_block_be", () -> BlockEntityType.Builder.of(
                    TemperatureGeneratorBE::new, MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<PhotovoltaicGeneratorBE>> PHOTOVOLTAIC_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("photovoltaic_generator_be", () -> BlockEntityType.Builder.of(
                    PhotovoltaicGeneratorBE::new, MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ThermalGeneratorBE>> THERMAL_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("thermal_generator_be", () -> BlockEntityType.Builder.of(
                    ThermalGeneratorBE::new, MoeBlocks.THERMAL_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyTransmissionAntennaBE>> ENERGY_TRANSMISSION_ANTENNA_BE =
            MOE_BLOCK_ENTITY.register("energy_transmission_antenna_be", () -> BlockEntityType.Builder.of(
                    EnergyTransmissionAntennaBE::new, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ElectromagneticDriverBE>> ELECTROMAGNETIC_DRIVER_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_driver_be", () -> BlockEntityType.Builder.of(
                    ElectromagneticDriverBE::new, MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BioReplicationVatBE>> BIO_REPLICATION_VAT_BE =
            MOE_BLOCK_ENTITY.register("bio_replication_vat_be", () -> BlockEntityType.Builder.of(
                    BioReplicationVatBE::new, MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<GeologicalMetalExcavatorBE>> GEOLOGICAL_METAL_EXCAVATOR_BE =
            MOE_BLOCK_ENTITY.register("geological_metal_excavator_be", () -> BlockEntityType.Builder.of(
                    GeologicalMetalExcavatorBE::new, MoeBlocks.GEOLOGICAL_METAL_EXCAVATOR_MACHINE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
