package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityEnergyHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.BlockEntityItemHandler;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.Map;

public class ExplodeGeneratorBE extends AbstractGeneratorBE implements IItemBlockEntity{
    private final BlockEntityEnergyHandler energy = new BlockEntityEnergyHandler(1048576) {

        @Override
        protected void onEnergyChanged(int previousAmount) {
            setChanged();
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(previousAmount, getBlockPos()));
            }
        }
    };
    private final BlockEntityItemHandler itemHandler = new BlockEntityItemHandler(1){

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final Map<Item, Integer> source = Map.of(Items.TNT, 4096, Items.END_CRYSTAL, 8192);
    public ExplodeGeneratorBE(BlockPos pos, BlockState blockState) {
        super(EtBlockEntities.EXPLODE_GENERATOR_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractGeneratorBE blockEntity) {
        try (Transaction transaction = Transaction.openRoot()){
            int expect = source.get(itemHandler.getStackInSlot(0).getItem());
            int insert = energy.insert(expect, transaction);
            if(insert == expect){
                itemHandler.extract(itemHandler.getResource(0), 1, transaction);
                transaction.commit();
            }
        }
    }

    @Override
    protected boolean canEnergyMake() {
        return source.containsKey(itemHandler.getStackInSlot(0).getItem());
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
    public StacksResourceHandler<ItemStack, ItemResource> getItemHandler() {
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
}
