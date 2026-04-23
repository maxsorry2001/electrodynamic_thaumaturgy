package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class LevelBlockEvent {
    @SubscribeEvent
    public static void dropEvent(BlockDropsEvent event) {
        BlockEntity blockEntity = event.getBlockEntity();
        List<ItemEntity> list = event.getDrops();
        BlockState blockState = event.getState();
        if(blockEntity instanceof EnergyBlockEntity){
            try (Transaction transaction = Transaction.openRoot()){
                ItemStack itemStack = list.get(0).getItem();
                EnergyHandler energyStorageBlock = ((EnergyBlockEntity) blockEntity).getEnergy();
                EnergyHandler energyStorageItem = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
                energyStorageItem.insert(energyStorageBlock.getAmountAsInt(), transaction);
                transaction.commit();
            }
        }
    }
}
