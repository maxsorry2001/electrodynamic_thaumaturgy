package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.FluidBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.EnergySetPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.FluidSetPacket;
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
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import org.jspecify.annotations.Nullable;

public class FluidBlock extends BaseEntityBlock {
    public static final MapCodec<FluidBlock> CODEC = simpleCodec(FluidBlock::new);
    public FluidBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidBlockEntity(blockPos, blockState);
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
            if (blockEntity instanceof FluidBlockEntity fluidBlockEntity && !level.isClientSide()) {
                ResourceHandler<FluidResource> fluidHandler = fluidBlockEntity.getFluidHandler();
                PacketDistributor.sendToAllPlayers(new FluidSetPacket(fluidHandler.getResource(0).toStack(fluidHandler.getAmountAsInt(0)), fluidBlockEntity.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(fluidBlockEntity, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == EtBlockEntities.FLUID_BLOCK_BE.get() ? createTickerHelper(blockEntityType, EtBlockEntities.FLUID_BLOCK_BE.get(), FluidBlockEntity::tick) : null;
    }
}
