package net.Gmaj7.electrodynamic_thaumaturgy.EventDispose;

import net.Gmaj7.electrodynamic_thaumaturgy.MagicOfElectromagnetic;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EnergyBlockEntity;
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
public class LevelBlockEvent {
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

    //@SubscribeEvent
    //public static void place(BlockEvent.EntityPlaceEvent event){
    //    if(event.getPlacedBlock().is(Blocks.CARVED_PUMPKIN)){
    //        BlockPattern.BlockPatternMatch match = MoeBlockPattern.HARMONIC_SOVEREIGN_SUMMON.find(event.getLevel(), event.getPos());
    //        if(match != null){
    //            BlockPos blockPos = match.getBlock(0, 2, 0).getPos();
    //            HarmonicSovereignSummonEntity harmonicSovereignSummonEntity = (HarmonicSovereignSummonEntity) MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get().create((Level) event.getLevel());
    //            harmonicSovereignSummonEntity.teleportTo(blockPos.getX() + 0.5, blockPos.getY() + 0.05, blockPos.getZ() + 0.5);
    //            event.getLevel().addFreshEntity(harmonicSovereignSummonEntity);
    //            for(int i = 0; i < match.getWidth(); ++i) {
    //                for(int j = 0; j < match.getHeight(); ++j) {
    //                    BlockInWorld blockinworld = match.getBlock(i, j, 0);
    //                    event.getLevel().setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
    //                    event.getLevel().levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
    //                }
    //            }
    //        }
    //    }
    //}
}
