package net.Gmaj7.magic_of_electromagnetic.EventDispose;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.List;

@EventBusSubscriber(modid = MagicOfElectromagnetic.MODID)
public class BlockEvent {
    @SubscribeEvent
    public static void dropEvent(BlockDropsEvent event) {
        BlockEntity blockEntity = event.getBlockEntity();
        List<ItemEntity> list = event.getDrops();
        if(blockEntity instanceof EnergyBlockEntity){
            ItemStack itemStack = list.get(0).getItem();
            IEnergyStorage energyStorageBlock = ((EnergyBlockEntity) blockEntity).getEnergy();
            IEnergyStorage energyStorageItem = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
            energyStorageItem.receiveEnergy(energyStorageBlock.getEnergyStored(), false);
        }
    }
}
