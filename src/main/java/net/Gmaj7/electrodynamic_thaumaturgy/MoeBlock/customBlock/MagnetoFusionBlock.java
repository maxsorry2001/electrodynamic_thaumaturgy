package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.MagnetoFusionBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
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
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return type == MoeBlockEntities.MAGNETO_FUSION_BE.get() ? createTickerHelper(type, MoeBlockEntities.MAGNETO_FUSION_BE.get(), MagnetoFusionBE::tick) : null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MagnetoFusionBE magnetoFusionBE && !level.isClientSide()) {
                EnergyHandler energyStorage = magnetoFusionBE.getEnergy();
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(energyStorage.getAmountAsInt(), magnetoFusionBE.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(magnetoFusionBE, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MagnetoFusionBE(blockPos, blockState);
    }
}
