package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.PhotovoltaicEnergyMakerBE;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.TemperatureEnergyMakerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PhotovoltaicEnergyMakerBlock extends AbstractEnergyMakerBlock {
    public static final MapCodec<PhotovoltaicEnergyMakerBlock> CODEC = simpleCodec(PhotovoltaicEnergyMakerBlock::new);
    public PhotovoltaicEnergyMakerBlock(Properties properties) {
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
        return blockEntityType == MoeBlockEntities.PHOTOVOLTAIC_ENERGY_MAKER_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.PHOTOVOLTAIC_ENERGY_MAKER_BE.get(), PhotovoltaicEnergyMakerBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PhotovoltaicEnergyMakerBE(blockPos, blockState);
    }
}
