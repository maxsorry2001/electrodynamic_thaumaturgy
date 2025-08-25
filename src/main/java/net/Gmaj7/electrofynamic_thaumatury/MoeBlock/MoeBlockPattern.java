package net.Gmaj7.electrofynamic_thaumatury.MoeBlock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

public class MoeBlockPattern {
    public static final BlockPattern HARMONIC_SOVEREIGN_SUMMON = BlockPatternBuilder.start()
            .aisle(new String[]{"a", "b", "b"})
            .where('a', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN)))
            .where('b', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.ANCIENT_DEBRIS)))
            .build();
}
