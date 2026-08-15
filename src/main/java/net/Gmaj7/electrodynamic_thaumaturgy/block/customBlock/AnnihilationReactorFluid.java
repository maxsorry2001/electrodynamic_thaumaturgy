package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AnnihilationReactorEnergyBE;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AnnihilationReactorFluidBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class AnnihilationReactorFluid extends BaseEntityBlock {
    public static final MapCodec<AnnihilationReactorFluid> CODEC = simpleCodec(AnnihilationReactorFluid::new);
    public AnnihilationReactorFluid(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AnnihilationReactorFluidBE(blockPos, blockState);
    }
}
