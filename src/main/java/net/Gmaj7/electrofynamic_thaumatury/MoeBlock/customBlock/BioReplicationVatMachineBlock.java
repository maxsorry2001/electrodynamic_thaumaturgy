package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.BioReplicationVatBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class BioReplicationVatMachineBlock extends BaseEntityBlock {
    public static final MapCodec<BioReplicationVatMachineBlock> CODEC = simpleCodec(BioReplicationVatMachineBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public BioReplicationVatMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
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
            if (blockEntity instanceof BioReplicationVatBE bioReplicationVatBE && !level.isClientSide()) {
                IEnergyStorage energyStorage = bioReplicationVatBE.getEnergy();
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(energyStorage.getEnergyStored(), bioReplicationVatBE.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(bioReplicationVatBE, Component.translatable("block.electrofynamic_thaumatury.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof BioReplicationVatBE blockEntity) {
                blockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.BIO_REPLICATION_VAT_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.BIO_REPLICATION_VAT_BE.get(), BioReplicationVatBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BioReplicationVatBE(blockPos, blockState);
    }
}
