package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AnnihilationReactorCoreBE;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class AnnihilationReactorCore extends BaseEntityBlock {
    public static final MapCodec<AnnihilationReactorCore> CODEC = simpleCodec(AnnihilationReactorCore::new);
    public AnnihilationReactorCore(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AnnihilationReactorCoreBE(blockPos, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(itemStack.is(EtItems.MAGNO_WRENCH) && level.getBlockEntity(pos) instanceof  AnnihilationReactorCoreBE annihilationReactorCoreBE){
            annihilationReactorCoreBE.startCheckLink();
            return InteractionResult.SUCCESS;
        }
        else return InteractionResult.CONSUME;
    }
}
