package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.Nullable;

import java.util.*;

public abstract class AbstractPipe extends Block {

    public static final EnumProperty<LinkState> UP = EnumProperty.create("link_state_up", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    public static final EnumProperty<LinkState> DOWN = EnumProperty.create("link_state_down", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    public static final EnumProperty<LinkState> EAST = EnumProperty.create("link_state_east", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    public static final EnumProperty<LinkState> WEST = EnumProperty.create("link_state_west", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    public static final EnumProperty<LinkState> NORTH = EnumProperty.create("link_state_north", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    public static final EnumProperty<LinkState> SOUTH = EnumProperty.create("link_state_south", LinkState.class, LinkState.NULL_AUTO, LinkState.LINK, LinkState.EXTRACT, LinkState.NULL_PLAYER);
    protected static final VoxelShape CORE = Block.box(6.0, 6.0, 6.0, 10.0, 10.0, 10.0);
    protected static final VoxelShape SHAPE_NORTH = Block.box(6.0, 6.0, 0.0, 10.0, 10.0, 6.0);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(6.0, 6.0, 10.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape SHAPE_EAST  = Block.box(10.0, 6.0, 6.0, 16.0, 10.0, 10.0);
    protected static final VoxelShape SHAPE_WEST  = Block.box(0.0, 6.0, 6.0, 6.0, 10.0, 10.0);
    protected static final VoxelShape SHAPE_UP    = Block.box(6.0, 10.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape SHAPE_DOWN  = Block.box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
    private static final Map<Direction, EnumProperty<LinkState>> DIR_TO_PROP = Map.of(
            Direction.UP, UP,
            Direction.DOWN, DOWN,
            Direction.NORTH, NORTH,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST,
            Direction.EAST, EAST
    );
    private static final List<ShapeEntry> SHAPES = List.of(
            new ShapeEntry(SHAPE_NORTH, NORTH, Direction.NORTH),
            new ShapeEntry(SHAPE_SOUTH, SOUTH, Direction.SOUTH),
            new ShapeEntry(SHAPE_WEST,  WEST,  Direction.WEST),
            new ShapeEntry(SHAPE_EAST,  EAST,  Direction.EAST),
            new ShapeEntry(SHAPE_UP,    UP,    Direction.UP),
            new ShapeEntry(SHAPE_DOWN,  DOWN,  Direction.DOWN)
    );
    public AbstractPipe(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(UP, LinkState.NULL_AUTO)
                .setValue(DOWN, LinkState.NULL_AUTO)
                .setValue(EAST, LinkState.NULL_AUTO)
                .setValue(WEST, LinkState.NULL_AUTO)
                .setValue(NORTH, LinkState.NULL_AUTO)
                .setValue(SOUTH, LinkState.NULL_AUTO));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = CORE;
        // 辅助判断：是否为连接状态（LINK 或 EXTRACT）
        for (Direction dir : Direction.values()) {
            LinkState linkState = state.getValue(DIR_TO_PROP.get(dir));
            if (linkState == LinkState.LINK || linkState == LinkState.EXTRACT) {
                // 根据方向选择对应的形状
                VoxelShape part = switch (dir) {
                    case NORTH -> SHAPE_NORTH;
                    case SOUTH -> SHAPE_SOUTH;
                    case EAST  -> SHAPE_EAST;
                    case WEST  -> SHAPE_WEST;
                    case UP    -> SHAPE_UP;
                    case DOWN  -> SHAPE_DOWN;
                };
                shape = Shapes.or(shape, part);
            }
        }
        return shape;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide()) return InteractionResult.CONSUME;
        if(!itemStack.isEmpty() && !itemStack.is(Tags.Items.TOOLS_WRENCH)) return useWithoutItem(state, level, pos, player, hitResult);
        Direction direction = getSelection(state, level, pos, player);
        if (direction == null) {
            direction = hitResult.getDirection(); // 回退
        }
        PipeNetSaveData data = getPipeNetSaveData((ServerLevel) level);
        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);
        if(itemStack.isEmpty() && state.getValue(DIR_TO_PROP.get(direction)) == LinkState.EXTRACT)
            data.loopTransferModOfPos(pos, direction);
        else if (isSamePipe(neighborState)) {
            // 切换连接状态
            boolean current = getConnection(direction, state);
            if (!current) {
                // 尚未连接，建立连接
                // 1. 更新网络图（添加边）
                int netA = data.getNetIdOfPos(pos);
                int netB = data.getNetIdOfPos(neighborPos);
                if (netA != -1 && netB != -1 ) {
                    if(netA != netB){
                        List<Integer> nets = List.of(netA, netB);
                        Set<BlockPos> links = Set.of(neighborPos);
                        data.linkNetsOfPos(pos, nets, links);
                    }
                    else data.link2PosInNet(netA, pos, neighborPos);
                }
            }
            else {
                // 断开连接
                // 1. 更新网络图（删除边）
                data.removeConnection(pos, neighborPos);
            }
            level.setBlock(pos, changeDirection(direction, state, state.getValue(DIR_TO_PROP.get(direction)).changeLinkNull()), 2);
            level.setBlock(neighborPos, changeDirection(direction.getOpposite(), neighborState, neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite())).changeLinkNull()), 2);
        }
        else if(hasCapability(level, neighborPos, direction.getOpposite())) {
            LinkState linkState = state.getValue(DIR_TO_PROP.get(direction)).cycleLinkState();
            BlockState newState = state.setValue(DIR_TO_PROP.get(direction), linkState);
            level.setBlock(pos, newState, 2);
            dealCapabilityChange((ServerLevel) level, pos, direction, linkState);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide()) return InteractionResult.CONSUME;
        PipeNetSaveData data = getPipeNetSaveData((ServerLevel) level);
        player.openMenu((data.getNetOfPos(pos)));
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private boolean getConnection(Direction direction, BlockState state) {
        return state.getValue(DIR_TO_PROP.get(direction)) == LinkState.LINK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP).add(DOWN).add(EAST).add(WEST).add(NORTH).add(SOUTH);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(level.isClientSide()) return;
        List<Direction> directionLinks = checkLinkDirection(level, pos);
        PipeNetSaveData pipeNetSaveData = getPipeNetSaveData((ServerLevel) level);
        if (directionLinks.isEmpty()) {
            if (pipeNetSaveData.getPipeNets().isEmpty() || pipeNetSaveData.getNetIdOfPos(pos) == -1) {
                pipeNetSaveData.createNet().addPos(pos, new HashSet<>());
                for (Direction direction : Direction.values())
                    if(state.getValue(DIR_TO_PROP.get(direction)) == LinkState.LINK)
                        pipeNetSaveData.addInsert((ServerLevel) level, pos, direction);
            }
        } else {
            List<Integer> netLinks = checkLinkNet((ServerLevel) level, pos, directionLinks);
            boolean isReplace = !isSamePipe(oldState);
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
                if(!(isSamePipe(neighborState))) {
                    if(state.getValue(DIR_TO_PROP.get(direction)) == LinkState.LINK)
                        pipeNetSaveData.addInsert((ServerLevel)level, pos, direction);
                    continue;
                }
                LinkState linkState = state.getValue((DIR_TO_PROP.get(direction))), neighborLinkState = neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite()));
                if ((isReplace && (neighborLinkState == LinkState.NULL_AUTO) || (neighborLinkState != LinkState.NULL_PLAYER && linkState != neighborLinkState)))
                    level.setBlock(neighbor, changeDirection(direction.getOpposite(), neighborState, LinkState.LINK), 2);
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.isClientSide()) return;
        BlockState newState = state;
        PipeNetSaveData data = getPipeNetSaveData((ServerLevel) level);
        for (Direction direction : Direction.values()){
            BlockPos neighbor = pos.relative(direction);
            LinkState linkState = state.getValue(DIR_TO_PROP.get(direction));
            if(linkState == LinkState.NULL_AUTO && hasCapability(level, neighbor, direction.getOpposite())) {
                newState = newState.setValue(DIR_TO_PROP.get(direction), LinkState.LINK);
                data.addInsert((ServerLevel)level, pos, direction);
            }
            else if (linkState != LinkState.NULL_PLAYER && !isSamePipe(level.getBlockState(neighbor)) && !hasCapability(level, neighbor, direction.getOpposite())) {
                newState = newState.setValue(DIR_TO_PROP.get(direction), LinkState.NULL_AUTO);
                switch (state.getValue(DIR_TO_PROP.get(direction))) {
                    case LINK ->  data.removeInsert((ServerLevel)level, pos, direction);
                    case EXTRACT -> data.removeExtract((ServerLevel)level, pos, direction);
                }
            }
        }
        if(newState != state)
            level.setBlock(pos, newState, 2);
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState defaultState = defaultBlockState();
        for (Direction direction : Direction.values()){
            BlockPos neighbor = pos.relative(direction);
            Level level = context.getLevel();
            if(hasCapability(level, neighbor, direction.getOpposite()))
                defaultState = defaultState.setValue(DIR_TO_PROP.get(direction), LinkState.LINK);
        }
        return defaultState;
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if(level.isClientSide()) return;
        getPipeNetSaveData((ServerLevel) level).breakPipe(pos);
        super.destroy(level, pos, state);
        for (Direction direction : Direction.values()){
            BlockPos neighbor = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighbor);
            if(isSamePipe(neighborState) && neighborState.getValue(DIR_TO_PROP.get(direction.getOpposite())) == LinkState.LINK)
                level.setBlock(neighbor, changeDirection(direction.getOpposite(), neighborState, LinkState.NULL_AUTO), 2);
        }
    }

    private List<Direction> checkLinkDirection(Level level, BlockPos pos){
        List<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()){
            BlockPos neighborPos = pos.relative(direction);
            if (isSamePipe(level.getBlockState(neighborPos)) && !(level.getBlockState(neighborPos).getValue(DIR_TO_PROP.get(direction.getOpposite())) == LinkState.NULL_PLAYER))
                list.add(direction);
        }
        return list;
    }

    private List<Integer> checkLinkNet(ServerLevel level, BlockPos pos, List<Direction> directions){
        List<Integer> list = new ArrayList<>();
        PipeNetSaveData pipeNetSaveData = getPipeNetSaveData(level);
        for (Direction direction : directions){
            BlockPos blockPos = pos.relative(direction);
            int i = pipeNetSaveData.getNetIdOfPos(blockPos);
            if((list.isEmpty() || !list.contains(i)) && i != -1) list.add(i);
        }
        return list;
    }

    public BlockState changeDirection(Direction direction, BlockState state, LinkState linkState){
        return state.setValue(DIR_TO_PROP.get(direction), linkState);
    }

    private double rayTraceShape(VoxelShape shape, BlockState state, BlockGetter level, BlockPos pos, Vec3 start, Vec3 end) {
        BlockHitResult hit = level.clipWithInteractionOverride(start, end, pos, shape, state);
        return hit == null ? Double.MAX_VALUE : hit.getLocation().distanceTo(start);
    }

    private Direction getSelection(BlockState state, Level level, BlockPos pos, Player player) {
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 end = start.add(player.getLookAngle().normalize().scale(4.5)); // 玩家触及距离
        Direction bestDir = null;
        double bestDist = Double.MAX_VALUE;

        // 检查核心（可选，如果不想让玩家点击核心，可以跳过）
        double coreDist = rayTraceShape(CORE, state, level, pos, start, end);
        if (coreDist < bestDist) {
            bestDist = coreDist;
            // 核心不返回方向，但保留距离，以便优先于臂（或者你可以让核心返回 null，让臂有机会）
        }

        // 检查各个方向的臂（只检查当前状态为 LINK 或 EXTRACT 的方向）
        for (ShapeEntry entry : SHAPES) {
            LinkState linkState = state.getValue(entry.property);
            if (linkState == LinkState.LINK || linkState == LinkState.EXTRACT) {
                double dist = rayTraceShape(entry.shape, state, level, pos, start, end);
                if (dist < bestDist) {
                    bestDist = dist;
                    bestDir = entry.direction;
                }
            }
        }
        return bestDir;
    }

    public abstract boolean isSamePipe(BlockState state);
    public abstract PipeNetSaveData getPipeNetSaveData(ServerLevel level);
    public abstract boolean hasCapability(Level level, BlockPos pos, Direction direction);
    public abstract void dealCapabilityChange(ServerLevel level, BlockPos pos, Direction direction, LinkState newState);

    public enum LinkState implements StringRepresentable {
        NULL_AUTO("null_auto"),
        NULL_PLAYER("null_player"),
        EXTRACT("extract"),
        LINK("link");

        private final String name;

        LinkState(String name){
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public LinkState changeLinkNull(){
            if(this == NULL_AUTO || this == NULL_PLAYER)
                return LINK;
            else return NULL_PLAYER;
        }

        public LinkState cycleLinkState(){
            return switch (this){
                case EXTRACT -> NULL_PLAYER;
                case NULL_AUTO, NULL_PLAYER -> LINK;
                case LINK -> EXTRACT;
            };
        }

        public boolean isNull(){
            return this == NULL_AUTO || this == NULL_PLAYER;
        }
    }

    private record ShapeEntry(VoxelShape shape, EnumProperty<LinkState> property, Direction direction) {}
}