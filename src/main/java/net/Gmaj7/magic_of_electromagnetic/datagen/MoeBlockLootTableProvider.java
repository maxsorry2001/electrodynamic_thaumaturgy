package net.Gmaj7.magic_of_electromagnetic.datagen;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
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
        //add(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), block -> createOreDrop(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(), MoeItems.EMPTY_MODULE.get()))
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return MoeBlocks.MOE_BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
