package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOE_BLOCK_ENTITY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EelectrodynamicThaumaturgy.MODID);

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

    public static final Supplier<BlockEntityType<BiomassGeneratorBE>> BIOMASS_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("biomass_generator_be", () -> BlockEntityType.Builder.of(
                    BiomassGeneratorBE::new, MoeBlocks.BIOMASS_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyTransmissionAntennaBE>> ENERGY_TRANSMISSION_ANTENNA_BE =
            MOE_BLOCK_ENTITY.register("energy_transmission_antenna_be", () -> BlockEntityType.Builder.of(
                    EnergyTransmissionAntennaBE::new, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ElectromagneticDriverBE>> ELECTROMAGNETIC_DRIVER_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_driver_be", () -> BlockEntityType.Builder.of(
                    ElectromagneticDriverBE::new, MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<BioReplicationVatBE>> BIO_REPLICATION_VAT_BE =
            MOE_BLOCK_ENTITY.register("bio_replication_vat_be", () -> BlockEntityType.Builder.of(
                    BioReplicationVatBE::new, MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ElectromagneticExtractorBE>> ELECTROMAGNETIC_EXTRACTOR_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_extractor_be", () -> BlockEntityType.Builder.of(
                    ElectromagneticExtractorBE::new, MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<AtomicReconstructionBE>> ATOMIC_RECONSTRUCTION_BE =
            MOE_BLOCK_ENTITY.register("atomic_reconstruction_be", () -> BlockEntityType.Builder.of(
                    AtomicReconstructionBE::new, MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<NitrogenHarvesterBE>> NITROGEN_HARVESTER_BE =
            MOE_BLOCK_ENTITY.register("nitrogen_harvester_be", () -> BlockEntityType.Builder.of(
                    NitrogenHarvesterBE::new, MoeBlocks.NITROGEN_HARVESTER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
