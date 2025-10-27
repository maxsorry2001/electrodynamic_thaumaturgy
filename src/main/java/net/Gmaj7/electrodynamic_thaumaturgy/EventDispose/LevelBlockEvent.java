package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.List;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class LevelBlockEvent {
    @SubscribeEvent
    public static void dropEvent(BlockDropsEvent event) {
        BlockEntity blockEntity = event.getBlockEntity();
        List<ItemEntity> list = event.getDrops();
        BlockState blockState = event.getState();
        if(blockEntity instanceof EnergyBlockEntity){
            ItemStack itemStack = list.get(0).getItem();
            IEnergyStorage energyStorageBlock = ((EnergyBlockEntity) blockEntity).getEnergy();
            IEnergyStorage energyStorageItem = itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
            energyStorageItem.receiveEnergy(energyStorageBlock.getEnergyStored(), false);
        }
    }
}
