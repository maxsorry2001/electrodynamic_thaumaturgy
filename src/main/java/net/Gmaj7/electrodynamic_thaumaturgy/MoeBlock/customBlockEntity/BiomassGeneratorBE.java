package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeBiomassGeneratorMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class BiomassGeneratorBE extends AbstractGeneratorBE implements IMoeItemBlockEntity, MenuProvider {
    private int biomassTime = 0;
    private int fullBiomassTime = 0;
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
        @Override
        public void change(int i) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    private final ItemStackHandler itemHandler = new ItemStackHandler(1){

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public BiomassGeneratorBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.BIOMASS_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        blockEntity.getEnergy().receiveEnergy(512, false);
    }

    @Override
    protected boolean canEnergyMake() {
        if(!level.isClientSide()){
            if (biomassTime > 0) biomassTime--;
            if (biomassTime <= 0) {
                if (!itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).has(DataComponents.FOOD) && energy.getEnergyStored() != energy.getMaxEnergyStored()) {
                    FoodProperties foodProperties = itemHandler.getStackInSlot(0).get(DataComponents.FOOD);
                    int time = (int) (10 * foodProperties.nutrition() + 20 * foodProperties.saturation());
                    if (time > 0) {
                        biomassTime = time;
                        fullBiomassTime = time;
                        itemHandler.getStackInSlot(0).shrink(1);
                    } else {
                        fullBiomassTime = 0;
                    }
                }
                else fullBiomassTime = 0;
            }
            PacketDistributor.sendToAllPlayers(new MoePacket.BiomassSetPacket(biomassTime, fullBiomassTime, getBlockPos()));
        }
        return biomassTime > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
        tag.putInt("fullBiomassTime", fullBiomassTime);
        tag.putInt("biomassTime", biomassTime);
        tag.put("item_handler", itemHandler.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
        fullBiomassTime = tag.getInt("fullBiomassTime");
        biomassTime = tag.getInt("biomassTime");
        itemHandler.deserializeNBT(registries, tag.getCompound("item_handler"));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeBiomassGeneratorMenu(i, inventory, this);
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


    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.electrodynamic_thaumaturgy.thermal_generator_block");
    }

    public int getBiomassTime() {
        return biomassTime;
    }

    public void setBiomassTime(int biomassTime) {
        this.biomassTime = biomassTime;
    }

    public int getFullBiomassTime() {
        return fullBiomassTime;
    }

    public void setFullBiomassTime(int fullBiomassTime) {
        this.fullBiomassTime = fullBiomassTime;
    }
}
