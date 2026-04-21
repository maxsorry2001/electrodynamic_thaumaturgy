package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.PhotovoltaicGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PhotovoltaicGeneratorBlock extends AbstractGeneratorBlock {
    public static final MapCodec<PhotovoltaicGeneratorBlock> CODEC = simpleCodec(PhotovoltaicGeneratorBlock::new);
    public PhotovoltaicGeneratorBlock(Properties properties) {
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.PHOTOVOLTAIC_GENERATOR_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.PHOTOVOLTAIC_GENERATOR_BE.get(), PhotovoltaicGeneratorBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PhotovoltaicGeneratorBE(blockPos, blockState);
    }
}
