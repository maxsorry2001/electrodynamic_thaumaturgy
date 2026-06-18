package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class BlockLootTableProvider extends BlockLootSubProvider {
    protected BlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
        dropSelf(EtBlocks.ELECTROMAGNETIC_MODEM_TABLE.get());
        dropSelf(EtBlocks.ENERGY_BLOCK.get());
        dropSelf(EtBlocks.FLUID_BLOCK.get());
        dropSelf(EtBlocks.PHOTOVOLTAIC_GENERATOR.get());
        dropSelf(EtBlocks.TEMPERATURE_GENERATOR.get());
        dropSelf(EtBlocks.ENERGY_TRANSMISSION_ANTENNA.get());
        dropSelf(EtBlocks.MAGIC_ENCODE_TABLE.get());
        dropSelf(EtBlocks.THERMAL_GENERATOR.get());
        dropSelf(EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get());
        dropSelf(EtBlocks.BIO_REPLICATION_VAT_MACHINE.get());
        dropSelf(EtBlocks.MAGNETO_CORE.get());
        dropSelf(EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get());
        dropSelf(EtBlocks.BIOMASS_GENERATOR.get());
        dropSelf(EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get());
        dropSelf(EtBlocks.MAGNETO_FUSION_MACHINE.get());
        dropSelf(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get());
        dropSelf(EtBlocks.EDDY_CURRENT_REMELTER_MACHINE.get());
        dropSelf(EtBlocks.ITEM_PIPE.get());
        dropSelf(EtBlocks.ENERGY_PIPE.get());
        dropSelf(EtBlocks.FLUID_PIPE.get());
        dropSelf(EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get());
        //add(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), block -> createOreDrop(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), EtItems.EMPTY_MODULE.get()))
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EtBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
