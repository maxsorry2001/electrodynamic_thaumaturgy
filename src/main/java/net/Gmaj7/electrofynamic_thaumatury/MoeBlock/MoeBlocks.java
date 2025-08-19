package net.Gmaj7.electrofynamic_thaumatury.MoeBlock;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlock.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeBlocks {
    public static final DeferredRegister.Blocks MOE_BLOCKS = DeferredRegister.createBlocks(MagicOfElectromagnetic.MODID);

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

    public static final DeferredBlock<Block> MAGIC_CAST_BLOCK = MOE_BLOCKS.register("magic_cast_block",
            () -> new MagicCastBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));

    public static final DeferredBlock<Block> LIVING_ENTITY_CLONE_BLOCK = MOE_BLOCKS.register("living_entity_clone_block",
            () -> new LivingEntityCloneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F)));
}
