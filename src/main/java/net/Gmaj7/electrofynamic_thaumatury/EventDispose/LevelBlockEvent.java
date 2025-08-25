package net.Gmaj7.electrofynamic_thaumatury.EventDispose;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockPattern;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.HarmonicSovereignEntity;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.HarmonicSovereignSummonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

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

    @SubscribeEvent
    public static void place(BlockEvent.EntityPlaceEvent event){
        if(event.getPlacedBlock().is(Blocks.CARVED_PUMPKIN)){
            BlockPattern.BlockPatternMatch match = MoeBlockPattern.HARMONIC_SOVEREIGN_SUMMON.find(event.getLevel(), event.getPos());
            if(match != null){
                BlockPos blockPos = match.getBlock(0, 2, 0).getPos();
                HarmonicSovereignSummonEntity harmonicSovereignSummonEntity = (HarmonicSovereignSummonEntity) MoeEntities.HARMONIC_SOVEREIGN_SUMMON_ENTITY.get().create((Level) event.getLevel());
                harmonicSovereignSummonEntity.teleportTo(blockPos.getX() + 0.5, blockPos.getY() + 0.05, blockPos.getZ() + 0.5);
                event.getLevel().addFreshEntity(harmonicSovereignSummonEntity);
                for(int i = 0; i < match.getWidth(); ++i) {
                    for(int j = 0; j < match.getHeight(); ++j) {
                        BlockInWorld blockinworld = match.getBlock(i, j, 0);
                        event.getLevel().setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        event.getLevel().levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                    }
                }
            }
        }
    }
}
