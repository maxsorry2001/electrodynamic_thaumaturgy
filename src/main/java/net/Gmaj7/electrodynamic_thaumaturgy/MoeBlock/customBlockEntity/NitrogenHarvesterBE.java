package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeNitrogenHarvesterBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NitrogenHarvesterBE extends BlockEntity implements IMoeEnergyBlockEntity,IMoeItemBlockEntity, MenuProvider {
    private static final int growUse = 131072;
    private static final int harvestUse = 16384;
    private int checkTick = 0;
    private static final int workTick = 100;
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
        protected void onContentsChanged(int index, ItemStack previousContents) {setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public NitrogenHarvesterBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.NITROGEN_HARVESTER_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NitrogenHarvesterBE nitrogenHarvesterBE){
        if(level.isClientSide()) return;
        nitrogenHarvesterBE.checkTick ++;
        if(nitrogenHarvesterBE.checkTick < workTick) return;
        try (Transaction transaction = Transaction.openRoot()){
            boolean flag = nitrogenHarvesterBE.dealCrops(pos, transaction);
            if(flag) {
                transaction.commit();
                nitrogenHarvesterBE.dealAge();
            }
        }
        nitrogenHarvesterBE.checkTick = 0;
    }

    private boolean dealCrops(BlockPos pos, Transaction transaction) {
        boolean commit = true;
        for (int dx = -1; dx < 2; dx++){
            if(!commit) break;
            for (int dz = -1; dz < 2; dz++){
                BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY(), pos.getZ() + dz);
                BlockState blockState = this.getLevel().getBlockState(blockPos);
                if(!this.getLevel().isClientSide() && blockState.getBlock() instanceof CropBlock cropBlock){
                    commit = growCrops(blockPos, transaction);
                    if(cropBlock.getAge(blockState) == cropBlock.getMaxAge()){
                        List<ItemStack> list = Block.getDrops(blockState, (ServerLevel) this.getLevel(), blockPos, null);
                        for (int i = 0; i < list.size(); i++) {
                            ItemStack itemStack = list.get(i).copy();
                            itemStack.setCount(itemStack.count() * 2);
                            for (int j = 0; j < this.itemHandler.size(); j++) {
                                int result = this.getItemHandler().insert(ItemResource.of(itemStack), itemStack.count(), transaction);
                                if (result != itemStack.count()) itemStack.setCount(itemStack.count() - result);
                                else break;
                            }
                        }
                    }
                }
            }
        }
        return commit;
    }

    private boolean growCrops(BlockPos pos, Transaction transaction) {
        boolean commit = true;
        BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        BlockState blockState = this.getLevel().getBlockState(blockPos);
        if(blockState.getBlock() instanceof CropBlock cropBlock){
            if(cropBlock.getAge(blockState) < cropBlock.getMaxAge() && this.getEnergy().getAmountAsInt() >= growUse) {
                int i = this.getEnergy().extract(growUse, transaction);
                if(i < growUse){
                    commit = false;
                }
            }
            else if(cropBlock.getAge(blockState) == cropBlock.getMaxAge() && this.getEnergy().getAmountAsInt() >= harvestUse) {
                int i = this.energy.extract(harvestUse, transaction);
                if(i < harvestUse){
                    commit = false;
                }
            }
        }
        return commit;
    }

    private void dealAge(){
        for (int dx = -1; dx < 2; dx++){
            for (int dz = -1; dz < 2; dz++){
                BlockPos cropPos = new BlockPos(worldPosition.getX() + dx, worldPosition.getY(), worldPosition.getZ() + dz);
                BlockState blockState = level.getBlockState(cropPos);
                if(blockState.getBlock() instanceof CropBlock cropBlock) {
                    if(cropBlock.getAge(blockState) != cropBlock.getMaxAge()) cropBlock.growCrops(level, cropPos, blockState);
                    else {
                        this.getLevel().destroyBlock(cropPos, false);
                        this.getLevel().setBlock(cropPos, cropBlock.getStateForAge(0), 2);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
        output.putInt("check_tick", checkTick);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
        checkTick = input.getInt("check_tick").get();
    }

    @Override
    public EnergyHandler getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        this.energy.setEnergy(i);
    }

    @Override
    public MoeBlockEntityItemHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.energy_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeNitrogenHarvesterBlockMenu(i, inventory, this);
    }
}
