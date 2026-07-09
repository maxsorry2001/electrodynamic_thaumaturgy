package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ExplodeGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class ExplodeGenerator extends AbstractGenerator{
    public static final MapCodec<ExplodeGenerator> CODEC = simpleCodec(ExplodeGenerator::new);

    public ExplodeGenerator(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return blockEntityType == EtBlockEntities.EXPLODE_GENERATOR_BE.get() ? createTickerHelper(blockEntityType, EtBlockEntities.EXPLODE_GENERATOR_BE.get(), ExplodeGeneratorBE::tick) : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ExplodeGeneratorBE(blockPos, blockState);
    }
}
