package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

public class TTPipe extends Block {

    protected static final VoxelShape AABB = Block.box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0);
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    private static final Map<Direction, BooleanProperty> DIR_TO_PROP = Map.of(
            Direction.UP, UP,
            Direction.DOWN, DOWN,
            Direction.NORTH, NORTH,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST,
            Direction.EAST, EAST
    );
    public TTPipe(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(UP, false));
        this.registerDefaultState(this.defaultBlockState().setValue(DOWN, false));
        this.registerDefaultState(this.defaultBlockState().setValue(EAST, false));
        this.registerDefaultState(this.defaultBlockState().setValue(WEST, false));
        this.registerDefaultState(this.defaultBlockState().setValue(NORTH, false));
        this.registerDefaultState(this.defaultBlockState().setValue(SOUTH, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide()) return InteractionResult.CONSUME;
        ServerLevel serverLevel = (ServerLevel) level;
        PipeNetSaveData data = serverLevel.getDataStorage().computeIfAbsent(PipeNetSaveData.PIPE_NETS);
        Direction clickedFace = hitResult.getDirection();
        BlockPos neighborPos = pos.relative(clickedFace);
        BlockState neighborState = level.getBlockState(neighborPos);
        if(player.isShiftKeyDown()) {
            PipeNetSaveData pipeNetSaveData = ((ServerLevel) level).getDataStorage().get(PipeNetSaveData.PIPE_NETS);
            int i = 1;
        }
        else if (neighborState.getBlock() instanceof TTPipe) {
            level.setBlock(pos, changeDirection(clickedFace, state), 2);
            level.setBlock(neighborPos, changeDirection(clickedFace.getOpposite(), neighborState), 2);
            // 切换连接状态
            boolean current = getConnection(clickedFace, state);
            if (!current) {
                // 尚未连接，建立连接
                // 1. 更新网络图（添加边）
                int netA = data.getNetIdOfPos(pos);
                int netB = data.getNetIdOfPos(neighborPos);
                if (netA != -1 && netB != -1 && netA != netB) {
                    // 两个不同网络，需要合并
                    List<Integer> nets = List.of(netA, netB);
                    Set<BlockPos> links = Set.of(neighborPos);
                    data.linkNetsOfPos(pos, nets, links);
                }
            }
            else {
                // 断开连接
                // 1. 更新网络图（删除边）
                data.removeConnection(pos, neighborPos);
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private boolean getConnection(Direction direction, BlockState state) {
        return state.getValue(DIR_TO_PROP.get(direction));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP).add(DOWN).add(EAST).add(WEST).add(NORTH).add(SOUTH);
    }

    @Override
    public StateDefinition<Block, BlockState> getStateDefinition() {
        return super.getStateDefinition();
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(level.isClientSide()) return;
        List<Direction> directionLinks = checkLinkDirection(level, pos);
        PipeNetSaveData pipeNetSaveData = ((ServerLevel)level).getDataStorage().computeIfAbsent(PipeNetSaveData.PIPE_NETS);
        if (directionLinks.isEmpty()) {
            if (pipeNetSaveData.getPipeNets().isEmpty() || pipeNetSaveData.getNetIdOfPos(pos) == -1)
                pipeNetSaveData.createNet().addPos(pos, new HashSet<>());
        } else {
            List<Integer> netLinks = checkLinkNet((ServerLevel) level, pos, directionLinks);
            boolean isReplace = !(oldState.getBlock() instanceof TTPipe);
            if (netLinks.size() == 1 && isReplace) {
                Set<BlockPos> set = new HashSet<>();
                for (Direction direction : directionLinks)
                    set.add(pos.relative(direction));
                pipeNetSaveData.addPosToNet(netLinks.get(0), pos, set);
            } else if (netLinks.size() > 1 && isReplace) {
                Set<BlockPos> set = new HashSet<>();
                for (Direction direction : directionLinks)
                    set.add(pos.relative(direction));
                pipeNetSaveData.linkNetsOfPos(pos, netLinks, set);
            }
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighbor);
                if (neighborState.getBlock() instanceof TTPipe && (isReplace && (!neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite()))) || (state.getValue((DIR_TO_PROP.get(direction))) != neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite())))))
                    level.setBlock(neighbor, changeDirection(direction.getOpposite(), neighborState), 2);
            }
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if(level.isClientSide()) return;
        ((ServerLevel)level).getDataStorage().get(PipeNetSaveData.PIPE_NETS).breakPipe(pos);
        super.destroy(level, pos, state);
        for (Direction direction : Direction.values()){
            BlockPos neighbor = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighbor);
            if(neighborState.getBlock() instanceof TTPipe && neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite())))
                level.setBlock(neighbor, changeDirection(direction.getOpposite(), neighborState), 2);
        }
    }

    private List<Direction> checkLinkDirection(Level level, BlockPos pos){
        List<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()){
            if (level.getBlockState(pos.relative(direction)).getBlock() instanceof TTPipe)
                list.add(direction);
        }
        return list;
    }

    private List<Integer> checkLinkNet(ServerLevel level, BlockPos pos, List<Direction> directions){
        List<Integer> list = new ArrayList<>();
        PipeNetSaveData pipeNetSaveData = level.getDataStorage().get(PipeNetSaveData.PIPE_NETS);
        for (Direction direction : directions){
            BlockPos blockPos = pos.relative(direction);
            int i = pipeNetSaveData.getNetIdOfPos(blockPos);
            if((list.isEmpty() || !list.contains(i)) && i != -1) list.add(i);
        }
        return list;
    }

    public BlockState changeDirection(Direction direction, BlockState state){
        return state.setValue(DIR_TO_PROP.get(direction), !state.getValue(DIR_TO_PROP.get(direction)));
    }
}
