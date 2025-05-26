package net.Gmaj7.magic_of_electromagnetic.MoeBlock;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock.*;
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

    public static final DeferredBlock<Block> TEMPERATURE_ENERGY_MAKER_BLOCK = MOE_BLOCKS.register("temperature_energy_maker_block",
            () ->  new TemperatureEnergyMakerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(5.0F, 6.0F)));

    public static final DeferredBlock<Block> PHOTOVOLTAIC_ENERGY_MAKER_BLOCK = MOE_BLOCKS.register("photovoltaic_energy_maker_block",
            () ->  new PhotovoltaicEnergyMakerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F)));

    public static final DeferredBlock<Block> WIRELESS_ENERGY_SEND_BLOCK = MOE_BLOCKS.register("wireless_energy_send_block",
            () ->  new WirelessEnergySendBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.METAL).strength(1.0F, 6.0F)));
}
