package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;


public class WirelessEnergySendBE extends BlockEntity {
    public WirelessEnergySendBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.WIRELESS_ENERGY_SEND_BE.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, WirelessEnergySendBE energyBlockEntity){
        BlockPos blockPos = pos;
        Direction direction = Direction.NORTH;
        switch (state.getValue(BlockStateProperties.FACING)){
            case UP -> {
                blockPos = pos.below();
                direction = Direction.DOWN;
            }
            case DOWN -> {
                blockPos = pos.above();
                direction = Direction.UP;
            }
            case EAST -> {
                blockPos = pos.west();
                direction = Direction.WEST;
            }
            case WEST -> {
                blockPos = pos.east();
                direction = Direction.EAST;
            }
            case NORTH -> {
                blockPos = pos.south();
                direction = Direction.SOUTH;
            }
            case SOUTH -> {
                blockPos = pos.north();
                direction = Direction.NORTH;
            }
        }
        IEnergyStorage energyStorage = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPos, direction);
        if(energyStorage != null){
            System.out.println("111");
        }
    }
}
