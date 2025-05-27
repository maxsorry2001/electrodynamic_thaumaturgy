package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.WirelessEnergyBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WirelessEnergyBlock extends BaseEntityBlock {
    public static final MapCodec<WirelessEnergyBlock> CODEC = simpleCodec(WirelessEnergyBlock::new);
    protected static final VoxelShape SHAPE = Block.box(7.0, 0.0, 7.0, 10.0, 10.0, 10.0);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty SEND = BooleanProperty.create("send");
    protected static final float AABB_MIN = 6.0F;
    protected static final float AABB_MAX = 10.0F;
    protected static final VoxelShape Y_AXIS_AABB = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);
    public WirelessEnergyBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (((Direction)state.getValue(FACING)).getAxis()) {
            case X:
            default:
                return X_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
        }
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
        return new WirelessEnergyBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.WIRELESS_ENERGY_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.WIRELESS_ENERGY_BE.get(), WirelessEnergyBE::tick) : null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
        return blockstate.is(this) && blockstate.getValue(FACING) == direction ? (BlockState)this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(SEND, true) : (BlockState)this.defaultBlockState().setValue(FACING, direction).setValue(SEND, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SEND);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(state.is(MoeBlocks.WIRELESS_ENERGY_BLOCK)) {
            if(player.isShiftKeyDown())
                level.setBlockAndUpdate(pos, state.setValue(SEND, !state.getValue(SEND)));
            else {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if(blockEntity instanceof WirelessEnergyBE)
                    for (int dx = -16; dx <= 16; dx++){
                        for (int dy = -16; dy <= 16; dy++){
                            for (int dz = -16; dz <= 16; dz++){
                                BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                                BlockState blockState = level.getBlockState(blockPos);
                                if(blockState.is(MoeBlocks.WIRELESS_ENERGY_BLOCK) && !blockState.getValue(WirelessEnergyBlock.SEND))
                                    ((WirelessEnergyBE) blockEntity).getReceivePos().add(blockPos);
                            }
                        }
                    }
                }
            }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return (BlockState)state.setValue(FACING, direction.rotate((Direction)state.getValue(FACING)));
    }
}
