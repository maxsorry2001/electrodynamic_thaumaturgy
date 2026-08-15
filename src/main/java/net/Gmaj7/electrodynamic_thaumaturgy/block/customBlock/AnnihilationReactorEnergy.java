package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AnnihilationReactorCoreBE;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AnnihilationReactorEnergyBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class AnnihilationReactorEnergy extends BaseEntityBlock {
    public static final MapCodec<AnnihilationReactorEnergy> CODEC = simpleCodec(AnnihilationReactorEnergy::new);
    public AnnihilationReactorEnergy(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AnnihilationReactorEnergyBE(blockPos, blockState);
    }
}
