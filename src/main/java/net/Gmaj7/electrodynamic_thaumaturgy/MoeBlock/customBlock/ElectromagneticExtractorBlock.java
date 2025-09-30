package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.ElectromagneticExtractorBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
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
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ElectromagneticExtractorBlock extends BaseEntityBlock {
    public static final MapCodec<ElectromagneticExtractorBlock> CODEC = simpleCodec(ElectromagneticExtractorBlock::new);

    public ElectromagneticExtractorBlock(Properties properties) {
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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ElectromagneticExtractorBE energyBlockEntity && !level.isClientSide()) {
                IEnergyStorage energyStorage = energyBlockEntity.getEnergy();
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(energyStorage.getEnergyStored(), energyBlockEntity.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(energyBlockEntity, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof ElectromagneticExtractorBE blockEntity) {
                blockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ElectromagneticExtractorBE(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.ELECTROMAGNETIC_EXTRACTOR_BLOCK_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.ELECTROMAGNETIC_EXTRACTOR_BLOCK_BE.get(), ElectromagneticExtractorBE::tick) : null;
    }
}
