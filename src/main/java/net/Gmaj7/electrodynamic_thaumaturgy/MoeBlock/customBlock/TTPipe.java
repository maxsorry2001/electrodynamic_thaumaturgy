package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TTPipe extends Block {

    protected static final VoxelShape AABB = Block.box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0);
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
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
        if(!level.isClientSide()) {
            if(player.isShiftKeyDown()) {
                PipeNetSaveData pipeNetSaveData = ((ServerLevel) level).getDataStorage().get(PipeNetSaveData.PIPE_NETS);
                int i = 1;
            }
            else {
                level.setBlock(pos, changeDirection(hitResult.getDirection(), state), 2);
                BlockPos blockPos = pos.relative(hitResult.getDirection());
                if(level.getBlockState(blockPos).getBlock() instanceof TTPipe) {
                    level.setBlock(blockPos, changeDirection(hitResult.getDirection().getOpposite(), level.getBlockState(blockPos)), 2);
                }
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
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
        if(directionLinks.isEmpty()){
            ((ServerLevel)level).getDataStorage().computeIfAbsent(PipeNetSaveData.PIPE_NETS).createNet().addPos(pos, new HashSet<>());
        }
        else{
            List<Integer> netLinks = checkLinkNet((ServerLevel)level, pos, directionLinks);
            if(netLinks.size() == 1){
                Set<BlockPos> set = new HashSet<>();
                for (Direction direction : directionLinks)
                    set.add(pos.relative(direction));
                ((ServerLevel)level).getDataStorage().get(PipeNetSaveData.PIPE_NETS).addPosToNet(netLinks.get(0), pos, set);
            }
            else if(netLinks.size() > 1){
                Set<BlockPos> set = new HashSet<>();
                for (Direction direction : directionLinks)
                    set.add(pos.relative(direction));
                ((ServerLevel)level).getDataStorage().get(PipeNetSaveData.PIPE_NETS).linkNetsOfPos(pos, netLinks, set);
            }
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if(level.isClientSide()) return;
        ((ServerLevel)level).getDataStorage().get(PipeNetSaveData.PIPE_NETS).breakPipe(pos);
        super.destroy(level, pos, state);
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
        BlockState newState = state;
        switch (direction){
            case UP -> newState = state.setValue(UP, !state.getValue(UP));
            case DOWN -> newState = state.setValue(DOWN, !state.getValue(DOWN));
            case EAST -> newState = state.setValue(EAST, !state.getValue(EAST));
            case WEST -> newState = state.setValue(WEST, !state.getValue(WEST));
            case NORTH -> newState = state.setValue(NORTH, !state.getValue(NORTH));
            case SOUTH -> newState = state.setValue(SOUTH, !state.getValue(SOUTH));
        }
        return newState;
    }
}
