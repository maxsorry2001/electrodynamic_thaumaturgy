package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.AtomicReconstructionBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.BioReplicationVatBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AtomicReconstructionBlock extends BaseEntityBlock {
    public static final MapCodec<AtomicReconstructionBlock> CODEC = simpleCodec(AtomicReconstructionBlock::new);

    public AtomicReconstructionBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.ATOMIC_RECONSTRUCTION_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.ATOMIC_RECONSTRUCTION_BE.get(), AtomicReconstructionBE::tick) : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AtomicReconstructionBE(blockPos, blockState);
    }
}
