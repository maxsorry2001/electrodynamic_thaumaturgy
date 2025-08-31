package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeBlocks {
    public static final DeferredRegister.Blocks MOE_BLOCKS = DeferredRegister.createBlocks(EelectrodynamicThaumaturgy.MODID);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_ASSEMBLY_TABLE = MOE_BLOCKS.register("electromagnetic_assembly_table",
            () -> new ElectromagneticAssemblyTable(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> ELECTROMAGNETIC_MODEM_TABLE = MOE_BLOCKS.register("electromagnetic_modem_table",
            () -> new ElectromagneticModemTable(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> ENERGY_BLOCK = MOE_BLOCKS.register("energy_block",
            () ->  new EnergyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> TEMPERATURE_GENERATOR_BLOCK = MOE_BLOCKS.register("temperature_generator_block",
            () ->  new TemperatureGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F)));

    public static final DeferredBlock<Block> THERMAL_GENERATOR_BLOCK = MOE_BLOCKS.register("thermal_generator_block",
            () ->  new ThermalGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F)));

    public static final DeferredBlock<Block> PHOTOVOLTAIC_GENERATOR_BLOCK = MOE_BLOCKS.register("photovoltaic_generator_block",
            () ->  new PhotovoltaicGeneratorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F)));

    public static final DeferredBlock<Block> ENERGY_TRANSMISSION_ANTENNA_BLOCK = MOE_BLOCKS.register("energy_transmission_antenna_block",
            () ->  new EnergyTransmissionAtennaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F)));

    public static final DeferredBlock<Block> MAGIC_LITHOGRAPHY_TABLE = MOE_BLOCKS.register("magic_lithography_table_block",
            () -> new MagicLithographyTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F)));

    public static final DeferredBlock<Block> ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK = MOE_BLOCKS.register("electromagnetic_driver_machine_block",
            () -> new ElectromagneticDriverMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));

    public static final DeferredBlock<Block> BIO_REPLICATION_VAT_MACHINE_BLOCK = MOE_BLOCKS.register("bio_replication_vat_machine_block",
            () -> new BioReplicationVatMachineBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));

    public static final DeferredBlock<Block> HARMONIC_CORE_BLOCK = MOE_BLOCKS.register("harmonic_core_block",
            () -> new HarmonicCoreBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).sound(SoundType.SNOW).strength(1.0F)));

    public static final DeferredBlock<Block> GEOLOGICAL_METAL_EXCAVATOR_MACHINE_BLOCK = MOE_BLOCKS.register("geological_metal_excavator_machine_block",
            () -> new GeologicalMetalExcavatorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));

    public static final DeferredBlock<Block> NITROGEN_HARVESTER_BLOCK = MOE_BLOCKS.register("nitrogen_harvester_block",
            () -> new NitrogenHarvesterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));
}
