package net.Gmaj7.electrofynamic_thaumatury.datagen;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlocks;
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
        dropSelf(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get());
        dropSelf(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get());
        dropSelf(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get());
        dropSelf(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get());
        dropSelf(MoeBlocks.THERMAL_GENERATOR_BLOCK.get());
        dropSelf(MoeBlocks.MAGIC_CAST_BLOCK.get());
        dropSelf(MoeBlocks.LIVING_ENTITY_CLONE_BLOCK.get());
        //add(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), block -> createOreDrop(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), MoeItems.EMPTY_MODULE.get()))
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return MoeBlocks.MOE_BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
