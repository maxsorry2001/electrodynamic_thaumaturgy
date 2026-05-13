package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagnetoFusionBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class MagnetoFusionBlock extends BaseEntityBlock {
    public static final MapCodec<MagnetoFusionBlock> CODEC = simpleCodec(MagnetoFusionBlock::new);

    public MagnetoFusionBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagnetoFusionBE(blockPos, blockState);
    }
}
