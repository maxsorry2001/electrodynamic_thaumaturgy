package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.TemperatureEnergyMakerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TemperatureEnergyMakerBlock extends BaseEntityBlock {
    public static final MapCodec<TemperatureEnergyMakerBlock> CODEC = simpleCodec(TemperatureEnergyMakerBlock::new);
    public TemperatureEnergyMakerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.ENERGY_MAKER_BLOCK_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.ENERGY_MAKER_BLOCK_BE.get(), TemperatureEnergyMakerBlockEntity::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TemperatureEnergyMakerBlockEntity(blockPos, blockState);
    }
}
