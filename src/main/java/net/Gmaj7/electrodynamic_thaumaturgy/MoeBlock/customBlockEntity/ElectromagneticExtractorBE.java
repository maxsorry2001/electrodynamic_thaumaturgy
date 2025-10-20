package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ElectromagneticExtractorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeElectromagneticExtractorBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

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
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(27){

        @Override
        protected void onContentsChanged(int slot) {
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
        if(level.isClientSide() || electromagneticExtractorBE.getEnergy().getEnergyStored() < extractUse * Math.pow(electromagneticExtractorBE.width, 2)) return;
        electromagneticExtractorBE.excavatorTick ++;
        if(electromagneticExtractorBE.excavatorTick < fullTick) return;
        electromagneticExtractorBE.excavatorTick = 0;
        BlockPos targetPos = pos.relative(state.getValue(ElectromagneticExtractorBlock.FACING));
        BlockState blockState = level.getBlockState(targetPos);
        while (blockState.isAir() && electromagneticExtractorBE.isMaxDepth(state, pos, targetPos)){
            targetPos = targetPos.relative(state.getValue(ElectromagneticExtractorBlock.FACING));
            blockState = level.getBlockState(targetPos);
            Vec3 vec3 = targetPos.getCenter();
            ((ServerLevel)level).sendParticles(ParticleTypes.FLASH, vec3.x(), vec3.y(), vec3.z(), 1, 0, 0, 0, 0);
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
        for (; i <= mi; i++){
            for (j = j0; j <= mj; j++){
                BlockPos destroyPos = electromagneticExtractorBE.getDestroyPos(state, i, j, targetPos);
                BlockState destroyState = level.getBlockState(destroyPos);
                if(destroyState.isAir()) continue;
                List<ItemStack> list = Block.getDrops(destroyState, (ServerLevel) level, destroyPos, level.getBlockEntity(destroyPos), null, electromagneticExtractorBE.getDigTool(destroyState));
                Iterator<ItemStack> iterator = list.iterator();
                while(iterator.hasNext()){
                    ItemStack itemStack = iterator.next().copy();
                    for (int k = 0; k < electromagneticExtractorBE.itemHandler.getSlots(); k++) {
                        ItemStack result = electromagneticExtractorBE.getItemHandler().insertItem(k, itemStack, false);
                        if(!result.isEmpty()) itemStack = result.copy();
                        else break;
                    }
                    if(!itemStack.isEmpty()) level.addFreshEntity(new ItemEntity(level, electromagneticExtractorBE.getBlockPos().getX(), electromagneticExtractorBE.getBlockPos().getY(), electromagneticExtractorBE.getBlockPos().getZ(), itemStack));
                }
                level.destroyBlock(destroyPos, false);
                electromagneticExtractorBE.getEnergy().extractEnergy(extractUse, false);
            }
        }
    }

    public BlockPos getDestroyPos(BlockState blockState, int i, int j, BlockPos targetPos){
        BlockPos result;
        switch (blockState.getValue(ElectromagneticExtractorBlock.FACING)){
            case SOUTH, NORTH -> {
                result = new BlockPos(targetPos.getX() + i, targetPos.getY() + j, targetPos.getZ());
            }
            case WEST, EAST -> {
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
        switch (blockState.getValue(ElectromagneticExtractorBlock.FACING)) {
            case SOUTH, NORTH -> {
                start = startPos.getZ();
                end = endPos.getZ();
            }
            case WEST, EAST -> {
                start = startPos.getX();
                end = endPos.getX();
            }
            default -> {
                start = startPos.getY();
                end = endPos.getZ();
            }
        }
        return Mth.abs(start - end) < this.depth;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
        tag.putInt("radius", width);
        tag.putInt("depth", depth);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
        width = tag.getInt("radius");
        depth = tag.getInt("depth");
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    public IItemHandler getItemHandler() {
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
        PacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void reduceWidth(boolean quick){
        this.width = quick ? Math.max(width - 5, MIN_WIDTH) : Math.max(width - 1, MIN_WIDTH);
        PacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void addDepth(boolean quick){
        this.depth = quick ?  Math.min(depth + 10, MAX_DEPTH) : Math.min(depth + 1, MAX_DEPTH);
        PacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void reduceDepth(boolean quick){
        this.depth = quick ? Math.max(depth - 10, 1) : Math.max(depth - 1, 1);
        PacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    private void dealChange(){
        PacketDistributor.sendToServer(new MoePacket.ExtractorPacket(this.width, this.depth, this.getBlockPos()));
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
