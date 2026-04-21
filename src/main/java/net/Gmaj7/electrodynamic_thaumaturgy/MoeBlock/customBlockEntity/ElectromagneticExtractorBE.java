package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeElectromagneticExtractorBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElectromagneticExtractorBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private static final int fullTick = 100;
    private int excavatorTick = 0;
    private static final int extractUse = 8192;
    private static final ItemStack AXE = new ItemStack(Items.NETHERITE_AXE);
    private static final ItemStack PICK_AXE = new ItemStack(Items.NETHERITE_PICKAXE);
    private static final ItemStack SWORD = new ItemStack(Items.NETHERITE_SWORD);
    private static final ItemStack SHOVE = new ItemStack(Items.NETHERITE_SHOVEL);
    private static final ItemStack HOE = new ItemStack(Items.NETHERITE_HOE);
    private static final ItemStack SHEARS = new ItemStack(Items.SHEARS);
    public int width = 1;
    public int depth = 1;
    private static final int MAX_WIDTH = 5;
    private static final int MIN_WIDTH = 1;
    private static final int MAX_DEPTH = 60;
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };

    private final MoeBlockEntityItemHandler itemHandler = new MoeBlockEntityItemHandler(27){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public ElectromagneticExtractorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ELECTROMAGNETIC_EXTRACTOR_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticExtractorBE electromagneticExtractorBE){
        if(level.isClientSide()) return;
        electromagneticExtractorBE.excavatorTick ++;
        if(electromagneticExtractorBE.excavatorTick < fullTick) return;
        electromagneticExtractorBE.excavatorTick = 0;
        BlockPos targetPos = pos.relative(state.getValue(BlockStateProperties.FACING));
        BlockState blockState = level.getBlockState(targetPos);
        while (blockState.isAir() && electromagneticExtractorBE.isMaxDepth(state, pos, targetPos)){
            targetPos = targetPos.relative(state.getValue(BlockStateProperties.FACING));
            blockState = level.getBlockState(targetPos);
            Vec3 vec3 = targetPos.getCenter();
            ((ServerLevel)level).sendParticles(ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFF), vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0, 0);
        }
        if(blockState.getBlock() instanceof LiquidBlock || blockState.getBlock().defaultDestroyTime() <= 0) return;
        boolean flag = electromagneticExtractorBE.width % 2 == 0;
        int i, j, j0, mi, mj;
        if(flag){
            i = - electromagneticExtractorBE.width / 2;
            j0 = - electromagneticExtractorBE.width / 2;
            mi = -i - 1;
            mj = -j0 - 1;
        }
        else {
            i = - (electromagneticExtractorBE.width - 1) / 2;
            j0 = - (electromagneticExtractorBE.width - 1) / 2;
            mi = -i;
            mj = -j0;
        }
        try (Transaction transaction = Transaction.openRoot()){
            List<ItemStack> drops = new ArrayList<>(), others = new ArrayList<>();
            List<BlockPos> destroyPoses = new ArrayList<>();
            for (; i <= mi; i++){
                for (j = j0; j <= mj; j++){
                    BlockPos destroyPos = electromagneticExtractorBE.getDestroyPos(state, i, j, targetPos);
                    BlockState destroyState = level.getBlockState(destroyPos);
                    if(destroyState.isAir()) continue;
                    drops.addAll(Block.getDrops(destroyState, (ServerLevel) level, destroyPos, level.getBlockEntity(destroyPos), null, electromagneticExtractorBE.getDigTool(destroyState)));
                    destroyPoses.add(destroyPos);
                }
            }
            Iterator<ItemStack> iterator = drops.iterator();
            while (iterator.hasNext()) {
                ItemStack itemStack = iterator.next().copy();
                for (int k = 0; k < electromagneticExtractorBE.itemHandler.size(); k++) {
                    int result = electromagneticExtractorBE.getItemHandler().insert(ItemResource.of(itemStack), itemStack.count(), transaction);
                    if (result != itemStack.count()) {
                        itemStack.setCount(itemStack.count() - result);
                        if (k == electromagneticExtractorBE.itemHandler.size() - 1) others.add(itemStack);
                    } else break;
                }
            }
            int energyUse = electromagneticExtractorBE.getEnergy().extract(extractUse * destroyPoses.size(), transaction);
            if(energyUse == extractUse * destroyPoses.size()) {
                transaction.commit();
                for (BlockPos blockPos : destroyPoses)
                    level.destroyBlock(blockPos, false);
                for (ItemStack itemStack : others)
                    level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack));
            }
        }
    }

    public BlockPos getDestroyPos(BlockState blockState, int i, int j, BlockPos targetPos){
        BlockPos result;
        switch (blockState.getValue(BlockStateProperties.FACING)){
            case Direction.SOUTH , Direction.NORTH -> {
                result = new BlockPos(targetPos.getX() + i, targetPos.getY() + j, targetPos.getZ());
            }
            case Direction.WEST, Direction.EAST -> {
                result = new BlockPos(targetPos.getX(), targetPos.getY() + i, targetPos.getZ() + j);
            }
            default -> {
                result = new BlockPos(targetPos.getX() + i, targetPos.getY(), targetPos.getZ() + j);
            }
        }
        return result;
    }

    private boolean isMaxDepth(BlockState blockState, BlockPos startPos, BlockPos endPos){
        int start, end;
        switch (blockState.getValue(BlockStateProperties.FACING)) {
            case Direction.SOUTH, Direction.NORTH -> {
                start = startPos.getZ();
                end = endPos.getZ();
            }
            case Direction.WEST, Direction.EAST -> {
                start = startPos.getX();
                end = endPos.getX();
            }
            default -> {
                start = startPos.getY();
                end = endPos.getY();
            }
        }
        return Mth.abs(start - end) < this.depth;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
        output.putInt("width", width);
        output.putInt("depth", depth);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
        width = input.getInt("width").get();
        depth = input.getInt("depth").get();
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        PacketDistributor.sendToAllPlayers(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
        return new MoeElectromagneticExtractorBlockMenu(i, inventory, this);
    }

    private ItemStack getDigTool(BlockState blockState){
        if(blockState.is(Blocks.COBWEB)) return SWORD;
        if(blockState.is(BlockTags.LEAVES)) return SHEARS;
        if(blockState.is(BlockTags.MINEABLE_WITH_SHOVEL)) return SHOVE;
        if(blockState.is(BlockTags.MINEABLE_WITH_AXE)) return AXE;
        if(blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)) return PICK_AXE;
        if(blockState.is(BlockTags.MINEABLE_WITH_HOE)) return HOE;
        return PICK_AXE;
    }

    public void addWidth(boolean quick){
        this.width = quick ? Math.min(width + 5, MAX_WIDTH) : Math.min(width + 1, MAX_WIDTH);
        ClientPacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void reduceWidth(boolean quick){
        this.width = quick ? Math.max(width - 5, MIN_WIDTH) : Math.max(width - 1, MIN_WIDTH);
        ClientPacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void addDepth(boolean quick){
        this.depth = quick ?  Math.min(depth + 10, MAX_DEPTH) : Math.min(depth + 1, MAX_DEPTH);
        ClientPacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void reduceDepth(boolean quick){
        this.depth = quick ? Math.max(depth - 10, 1) : Math.max(depth - 1, 1);
        ClientPacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    private void dealChange(){
        ClientPacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
}
