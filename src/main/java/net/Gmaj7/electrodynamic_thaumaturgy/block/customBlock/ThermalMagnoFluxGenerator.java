package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ThermalMagnoFluxGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class ThermalMagnoFluxGenerator extends AbstractGenerator{
    public static final MapCodec<ThermalMagnoFluxGenerator> CODEC = simpleCodec(ThermalMagnoFluxGenerator::new);
    public ThermalMagnoFluxGenerator(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return blockEntityType == EtBlockEntities.THERMAL_MAGNO_FLUX_GENERATOR_BE.get() ? createTickerHelper(blockEntityType, EtBlockEntities.THERMAL_MAGNO_FLUX_GENERATOR_BE.get(), ThermalMagnoFluxGeneratorBE::tick) : null;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ThermalMagnoFluxGeneratorBE(blockPos, blockState);
    }
}
