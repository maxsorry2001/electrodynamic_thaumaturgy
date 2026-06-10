package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class MoeBlockLootTableProvider extends BlockLootSubProvider {
    protected MoeBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
        dropSelf(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get());
        dropSelf(MoeBlocks.ENERGY_BLOCK.get());
        dropSelf(MoeBlocks.PHOTOVOLTAIC_GENERATOR.get());
        dropSelf(MoeBlocks.TEMPERATURE_GENERATOR.get());
        dropSelf(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA.get());
        dropSelf(MoeBlocks.MAGIC_ENCODE_TABLE.get());
        dropSelf(MoeBlocks.THERMAL_GENERATOR.get());
        dropSelf(MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get());
        dropSelf(MoeBlocks.BIO_REPLICATION_VAT_MACHINE.get());
        dropSelf(MoeBlocks.MAGNETO_CORE.get());
        dropSelf(MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get());
        dropSelf(MoeBlocks.BIOMASS_GENERATOR.get());
        dropSelf(MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get());
        dropSelf(MoeBlocks.MAGNETO_FUSION_MACHINE.get());
        dropSelf(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get());
        dropSelf(MoeBlocks.EDDY_CURRENT_REMELTER_MACHINE.get());
        dropSelf(MoeBlocks.ITEM_PIPE.get());
        dropSelf(MoeBlocks.ENERGY_PIPE.get());
        dropSelf(MoeBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get());
        //add(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), block -> createOreDrop(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), MoeItems.EMPTY_MODULE.get()))
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return MoeBlocks.MOE_BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
