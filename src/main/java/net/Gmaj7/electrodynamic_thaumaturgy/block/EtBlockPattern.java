package net.Gmaj7.electrodynamic_thaumaturgy.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

public class EtBlockPattern {
    public static final BlockPattern HARMONIC_SOVEREIGN_SUMMON = BlockPatternBuilder.start()
            .aisle(new String[]{"a", "b", "b"})
            .where('a', BlockInWorld.hasState(BlockStatePredicate.forBlock(EtBlocks.MAGNETO_CORE.get())))
            .where('b', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.EMERALD_BLOCK)))
            .build();
}
