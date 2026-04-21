package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ThermalGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeBiomassGeneratorMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeBlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class BiomassGeneratorBE extends AbstractGeneratorBE implements IMoeItemBlockEntity, MenuProvider {
    private int biomassTime = 0;
    private int fullBiomassTime = 0;
    private final MoeBlockEntityEnergyHandler energy = new MoeBlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    private final MoeBlockEntityItemHandler itemHandler = new MoeBlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
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
        try (Transaction transaction = Transaction.openRoot()){
            int i = blockEntity.getEnergy().insert(512, transaction);
            if(i > 0) transaction.commit();
        }
    }

    @Override
    protected boolean canEnergyMake() {
        if(!level.isClientSide()){
            if (biomassTime > 0) biomassTime--;
            if (biomassTime <= 0) {
                if (!itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).has(DataComponents.FOOD) && energy.getAmountAsInt() != energy.getCapacityAsInt()) {
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
            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(ThermalGeneratorBlock.LIT, biomassTime > 0));
            PacketDistributor.sendToAllPlayers(new MoePacket.BiomassSetPacket(biomassTime, fullBiomassTime, getBlockPos()));
        }
        return biomassTime > 0;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        energy.serialize(output);
        itemHandler.serialize(output);
        output.putInt("fullBiomassTime", fullBiomassTime);
        output.putInt("biomassTime", biomassTime);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        energy.deserialize(input);
        itemHandler.deserialize(input);
        fullBiomassTime = input.getInt("fullBiomassTime").get();
        biomassTime = input.getInt("biomassTime").get();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MoeBiomassGeneratorMenu(i, inventory, this);
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


    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.size());
        for (int i = 0; i < itemHandler.size(); i++){
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

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.drops();
    }
}
