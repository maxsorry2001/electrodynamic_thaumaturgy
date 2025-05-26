package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.WirelessEnergySendBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WirelessEnergySendBlock extends BaseEntityBlock {
    public static final MapCodec<WirelessEnergySendBlock> CODEC = simpleCodec(WirelessEnergySendBlock::new);
    protected static final VoxelShape SHAPE = Block.box(7.0, 0.0, 7.0, 10.0, 10.0, 10.0);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public WirelessEnergySendBlock(Properties properties) {
        super(properties);
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WirelessEnergySendBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.WIRELESS_ENERGY_SEND_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.WIRELESS_ENERGY_SEND_BE.get(), WirelessEnergySendBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction ? (BlockState)this.defaultBlockState().setValue(FACING, direction.getOpposite()) : (BlockState)this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }
}
