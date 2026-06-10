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

    public static final DeferredBlock<Block> TEMPERATURE_GENERATOR = registerBlock("temperature_generator",
            (properties) ->  new TemperatureGenerator(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> THERMAL_GENERATOR = registerBlock("thermal_generator",
            (properties) ->  new ThermalGenerator(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> BIOMASS_GENERATOR = registerBlock("biomass_generator",
            (properties) ->  new BiomassGenerator(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F).noOcclusion()), 1);

    public static final DeferredBlock<Block> PHOTOVOLTAIC_GENERATOR = registerBlock("photovoltaic_generator",
            (properties) ->  new PhotovoltaicGenerator(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> ENERGY_TRANSMISSION_ANTENNA = registerBlock("energy_transmission_antenna",
            (properties) ->  new EnergyTransmissionAtenna(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F).noOcclusion()), 16);

    public static final DeferredBlock<Block> MAGIC_ENCODE_TABLE = registerBlock("magic_encode_table",
            (properties) -> new MagicEncodeTable(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(2.5F)), 1);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_DRIVER_MACHINE = registerBlock("electromagnetic_driver_machine",
            (properties) -> new ElectromagneticDriver(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> BIO_REPLICATION_VAT_MACHINE = registerBlock("bio_replication_vat_machine",
            (properties) -> new BioReplicationVat(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> MAGNETO_CORE = registerBlock("magneto_core",
            (properties) -> new HarmonicCore(properties.mapColor(MapColor.COLOR_BLUE).sound(SoundType.SNOW).strength(1.0F).noOcclusion().lightLevel(p -> 7)), 16);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_EXTRACTOR_MACHINE = registerBlock("electromagnetic_extractor_machine",
            (properties) -> new ElectromagneticExtractor(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> ATOMIC_RECONSTRUCTION_MACHINE = registerBlock("atomic_reconstruction_machine",
            (properties) -> new AtomicReconstruction(properties.mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F).noOcclusion()), 1);

    public static final DeferredBlock<Block> MAGNETO_FUSION_MACHINE = registerBlock("magneto_fusion_machine",
            (properties -> new MagnetoFusion(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_DISSOCIATION_MACHINE = registerBlock("electromagnetic_dissociation_machine",
            (properties -> new ElectromagneticDissociation(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> EDDY_CURRENT_REMELTER_MACHINE = registerBlock("eddy_current_remelter_machine",
            (properties -> new EddyCurrentRemelter(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> ELECTROMAGNETIC_INFUSER_MACHINE = registerBlock("electromagnetic_infuser_machine",
            (properties -> new ElectromagneticInfuser(properties.noOcclusion().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F))), 1);

    public static final DeferredBlock<Block> ENERGY_PIPE = registerBlock("energy_pipe",
            (properties -> new EnergyPipe(properties.noOcclusion())));

    public static final DeferredBlock<Block> ITEM_PIPE = registerBlock("item_pipe",
            (properties -> new ItemPipe(properties.noOcclusion())));

    public static final DeferredBlock<Block> FLUID_PIPE = registerBlock("fluid_pipe",
            (properties -> new FluidPipe(properties.noOcclusion())));

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
