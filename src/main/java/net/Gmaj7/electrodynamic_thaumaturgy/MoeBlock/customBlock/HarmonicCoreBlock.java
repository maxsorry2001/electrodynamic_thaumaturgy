package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockPattern;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.MoeEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEntity.custom.HarmonicSovereignSummonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

public class HarmonicCoreBlock extends Block {
    public static final MapCodec<HarmonicCoreBlock> CODEC = simpleCodec(HarmonicCoreBlock::new);
    public HarmonicCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        BlockPattern.BlockPatternMatch match = MoeBlockPattern.HARMONIC_SOVEREIGN_SUMMON.find(level, pos);
        if(match != null){
            BlockPos blockPos = match.getBlock(0, 2, 0).getPos();
            HarmonicSovereignSummonEntity harmonicSovereignSummonEntity = (HarmonicSovereignSummonEntity) MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get().create(level);
            harmonicSovereignSummonEntity.teleportTo(blockPos.getX() + 0.5, blockPos.getY() + 0.05, blockPos.getZ() + 0.5);
            level.addFreshEntity(harmonicSovereignSummonEntity);
            for(int i = 0; i < match.getWidth(); ++i) {
                for(int j = 0; j < match.getHeight(); ++j) {
                    BlockInWorld blockinworld = match.getBlock(i, j, 0);
                    level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                }
            }
        }
    }
}
