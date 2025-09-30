package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeGeologicalMetalExcavatorBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
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
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class ElectromagneticExtractorBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private static int fullTick = 20;
    private int excavatorTick = 0;
    private static int tickUse =1024;
    private static ItemStack AXE = new ItemStack(Items.NETHERITE_AXE);
    private static ItemStack PICE_AXE = new ItemStack(Items.NETHERITE_PICKAXE);
    private static ItemStack SWORD = new ItemStack(Items.NETHERITE_SWORD);
    private static ItemStack SHOVE = new ItemStack(Items.NETHERITE_SHOVEL);
    private static ItemStack HOE = new ItemStack(Items.NETHERITE_HOE);
    private static ItemStack SHEARS = new ItemStack(Items.SHEARS);
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
        super(MoeBlockEntities.ELECTROMAGNETIC_EXTRACTOR_BLOCK_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectromagneticExtractorBE electromagneticExtractorBE){
        if(level.isClientSide() || electromagneticExtractorBE.getEnergy().getEnergyStored() < tickUse) return;
        electromagneticExtractorBE.excavatorTick ++;
        electromagneticExtractorBE.getEnergy().extractEnergy(tickUse, false);
        if(electromagneticExtractorBE.excavatorTick < fullTick) return;
        electromagneticExtractorBE.excavatorTick = 0;
        BlockPos blockPos = pos.below();
        BlockState blockState = level.getBlockState(blockPos);
        while (blockState.isAir()){
            blockPos = blockPos.below();
            blockState = level.getBlockState(blockPos);
        }
        if(blockState.getBlock() instanceof LiquidBlock || blockState.getBlock().defaultDestroyTime() <= 0) return;
        List<ItemStack> list = Block.getDrops(blockState, (ServerLevel) level, blockPos, level.getBlockEntity(blockPos), null, electromagneticExtractorBE.getDigTool(blockState));
        Iterator<ItemStack> iterator = list.iterator();
        while(iterator.hasNext()){
            ItemStack itemStack = iterator.next().copy();
            for (int i = 0; i < electromagneticExtractorBE.itemHandler.getSlots(); i++) {
                ItemStack result = electromagneticExtractorBE.getItemHandler().insertItem(i, itemStack, false);
                if(!result.isEmpty()) itemStack = result.copy();
                else break;
            }
        }
        level.destroyBlock(blockPos, false);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
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
        return new MoeGeologicalMetalExcavatorBlockMenu(i, inventory, this);
    }

    private ItemStack getDigTool(BlockState blockState){
        if(blockState.is(Blocks.COBWEB)) return SWORD;
        if(blockState.is(BlockTags.LEAVES)) return SHEARS;
        if(blockState.is(BlockTags.MINEABLE_WITH_SHOVEL)) return SHOVE;
        if(blockState.is(BlockTags.MINEABLE_WITH_AXE)) return AXE;
        if(blockState.is(BlockTags.MINEABLE_WITH_PICKAXE)) return PICE_AXE;
        if(blockState.is(BlockTags.MINEABLE_WITH_HOE)) return HOE;
        return PICE_AXE;
    }
}
