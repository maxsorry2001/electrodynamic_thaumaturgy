package net.Gmaj7.electrodynamic_thaumaturgy.block;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EtBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOE_BLOCK_ENTITY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<BlockEntityType<EnergyBlockEntity>> ENERGY_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("energy_block_be", () -> new BlockEntityType<>(
                    EnergyBlockEntity::new, EtBlocks.ENERGY_BLOCK.get()));


    public static final Supplier<BlockEntityType<FluidBlockEntity>> FLUID_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("fluid_block_be", () -> new BlockEntityType<>(
                    FluidBlockEntity::new, EtBlocks.FLUID_BLOCK.get()));

    public static final Supplier<BlockEntityType<TemperatureGeneratorBE>> TEMPERATURE_GENERATOR_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("temperature_generator_be", () -> new BlockEntityType<>(
                    TemperatureGeneratorBE::new, EtBlocks.TEMPERATURE_GENERATOR.get()));

    public static final Supplier<BlockEntityType<PhotovoltaicGeneratorBE>> PHOTOVOLTAIC_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("photovoltaic_generator_be", () -> new BlockEntityType<>(
                    PhotovoltaicGeneratorBE::new, EtBlocks.PHOTOVOLTAIC_GENERATOR.get()));

    public static final Supplier<BlockEntityType<ThermalGeneratorBE>> THERMAL_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("thermal_generator_be", () -> new BlockEntityType<>(
                    ThermalGeneratorBE::new, EtBlocks.THERMAL_GENERATOR.get()));

    public static final Supplier<BlockEntityType<BiomassGeneratorBE>> BIOMASS_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("biomass_generator_be", () -> new BlockEntityType<>(
                    BiomassGeneratorBE::new, EtBlocks.BIOMASS_GENERATOR.get()));

    public static final Supplier<BlockEntityType<EnergyTransmissionAntennaBE>> ENERGY_TRANSMISSION_ANTENNA_BE =
            MOE_BLOCK_ENTITY.register("energy_transmission_antenna_be", () -> new BlockEntityType<>(
                    EnergyTransmissionAntennaBE::new, EtBlocks.ENERGY_TRANSMISSION_ANTENNA.get()));

    public static final Supplier<BlockEntityType<ElectromagneticDriverBE>> ELECTROMAGNETIC_DRIVER_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_driver_be", () -> new BlockEntityType<>(
                    ElectromagneticDriverBE::new, EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get()));

    public static final Supplier<BlockEntityType<BioReplicationVatBE>> BIO_REPLICATION_VAT_BE =
            MOE_BLOCK_ENTITY.register("bio_replication_vat_be", () -> new BlockEntityType<>(
                    BioReplicationVatBE::new, EtBlocks.BIO_REPLICATION_VAT_MACHINE.get()));

    public static final Supplier<BlockEntityType<ElectromagneticExtractorBE>> ELECTROMAGNETIC_EXTRACTOR_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_extractor_be", () -> new BlockEntityType<>(
                    ElectromagneticExtractorBE::new, EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get()));

    public static final Supplier<BlockEntityType<AtomicReconstructionBE>> ATOMIC_RECONSTRUCTION_BE =
            MOE_BLOCK_ENTITY.register("atomic_reconstruction_be", () -> new BlockEntityType<>(
                    AtomicReconstructionBE::new, EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get()));

    public static final Supplier<BlockEntityType<MagnetoFusionBE>> MAGNETO_FUSION_BE =
            MOE_BLOCK_ENTITY.register("magneto_fusion_be", () -> new BlockEntityType<>(
                    MagnetoFusionBE::new, EtBlocks.MAGNETO_FUSION_MACHINE.get()));

    public static final Supplier<BlockEntityType<ElectromagneticDissociationBE>> ELECTROMAGNETIC_DISSOCIATION_BE =
            MOE_BLOCK_ENTITY.register("electromagnetic_dissociation_be", () -> new BlockEntityType<>(
                    ElectromagneticDissociationBE::new, EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get()));

    public static final Supplier<BlockEntityType<EddyCurrentRemelterBE>> EDDY_CURRENT_REMELTER_BE =
            MOE_BLOCK_ENTITY.register("eddy_current_remelter_be", () -> new BlockEntityType<>(
                    EddyCurrentRemelterBE::new, EtBlocks.EDDY_CURRENT_REMELTER_MACHINE.get()));

    public static final Supplier<BlockEntityType<ElectromagneticInfuserBE>> ELECTROMAGNETIC_INFUSER_BE =
            MOE_BLOCK_ENTITY.register("eddy_infuser_be", () -> new BlockEntityType<>(
                    ElectromagneticInfuserBE::new, EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get()));

    public static final Supplier<BlockEntityType<MagneticDissolverBE>> MAGNETIC_DISSOLVER_BE =
            MOE_BLOCK_ENTITY.register("magnetic_dissolver_be", () -> new BlockEntityType<>(
                    MagneticDissolverBE::new, EtBlocks.MAGNETIC_DISSOLVER_MACHINE.get()));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
