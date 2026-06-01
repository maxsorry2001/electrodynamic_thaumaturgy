package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.*;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.function.Function;

public class MoeBlocks {
    public static final DeferredRegister.Blocks MOE_BLOCKS = DeferredRegister.createBlocks(ElectrodynamicThaumaturgy.MODID);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_ASSEMBLY_TABLE = registerBlock("electromagnetic_assembly_table",
            (properties) -> new ElectromagneticAssemblyTable(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> ELECTROMAGNETIC_MODEM_TABLE = registerBlock("electromagnetic_modem_table",
            (properties) -> new ElectromagneticModemTable(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> ENERGY_BLOCK = registerBlockNoItem("energy_block",
            (properties) ->  new EnergyBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F).noOcclusion()));

    public static final DeferredBlock<Block> TEMPERATURE_GENERATOR_BLOCK = registerBlock("temperature_generator_block",
            (properties) ->  new TemperatureGeneratorBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> THERMAL_GENERATOR_BLOCK = registerBlock("thermal_generator_block",
            (properties) ->  new ThermalGeneratorBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> BIOMASS_GENERATOR_BLOCK = registerBlock("biomass_generator_block",
            (properties) ->  new BiomassGeneratorBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> PHOTOVOLTAIC_GENERATOR_BLOCK = registerBlock("photovoltaic_generator_block",
            (properties) ->  new PhotovoltaicGeneratorBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> ENERGY_TRANSMISSION_ANTENNA_BLOCK = registerBlock("energy_transmission_antenna_block",
            (properties) ->  new EnergyTransmissionAtennaBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F).noOcclusion()), 16);

    public static final DeferredBlock<Block> MAGIC_ENCODE_TABLE = registerBlock("magic_encode_table",
            (properties) -> new MagicEncodeTableBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F)), 1);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK = registerBlock("electromagnetic_driver_machine_block",
            (properties) -> new ElectromagneticDriverBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> BIO_REPLICATION_VAT_MACHINE_BLOCK = registerBlock("bio_replication_vat_machine_block",
            (properties) -> new BioReplicationVatBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> MAGNETO_CORE_BLOCK = registerBlock("magneto_core_block",
            (properties) -> new HarmonicCoreBlock(properties.mapColor(MapColor.COLOR_BLUE).sound(SoundType.SNOW).strength(1.0F).noOcclusion().lightLevel(p -> 7)), 16);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK = registerBlock("electromagnetic_extractor_machine_block",
            (properties) -> new ElectromagneticExtractorBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> ATOMIC_RECONSTRUCTION_MACHINE_BLOCK = registerBlock("atomic_reconstruction_machine_block",
            (properties) -> new AtomicReconstructionBlock(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> MAGNETO_FUSION_MACHINE_BLOCK = registerBlock("magneto_fusion_machine_block",
            (properties -> new MagnetoFusionBlock(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK = registerBlock("electromagnetic_dissociation_machine_block",
            (properties -> new ElectromagneticDissociationBlock(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> EDDY_CURRENT_REMELTER_MACHINE_BLOCK = registerBlock("eddy_current_remelter_machine_block",
            (properties -> new EddyCurrentRemelterBlock(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> ENERGY_PIPE = registerBlock("energy_pipe",
            (properties -> new EnergyPipe(properties.noOcclusion())));

    public static final DeferredBlock<Block> ITEM_PIPE = registerBlock("item_pipe",
            (properties -> new ItemPipe(properties.noOcclusion())));

    public static final DeferredBlock<Block> LIGHT_AIR = registerBlock("light_air",
            (properties) -> new AirBlock(properties.air().lightLevel(p -> 15).replaceable().noCollision().noLootTable()), null);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function){
        return registerBlock(name, function, null);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function, @Nullable Integer maxStack){
        DeferredBlock<T> toReturn = MOE_BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn, maxStack);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, @Nullable Integer maxStack){
        MoeItems.MOE_ITEM.registerItem(name, properties -> {
            if(maxStack != null)
                return new BlockItem(block.get(), properties.useBlockDescriptionPrefix().stacksTo(maxStack));
            else
                return new BlockItem(block.get(), properties.useBlockDescriptionPrefix());
        });
    }

    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Function<BlockBehaviour.Properties, T> function){
        DeferredBlock<T> toReturn = MOE_BLOCKS.registerBlock(name, function);
        return toReturn;
    }
}
