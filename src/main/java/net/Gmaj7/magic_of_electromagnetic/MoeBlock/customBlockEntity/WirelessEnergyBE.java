package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock.WirelessEnergyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.List;


public class WirelessEnergyBE extends BlockEntity {
    private static final int maxSend = 64;
    private List<BlockPos> receivePos = new ArrayList<>();

    public WirelessEnergyBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.WIRELESS_ENERGY_BE.get(), pos, blockState);
    }


    public static void tick(Level level, BlockPos pos, BlockState state, WirelessEnergyBE wirelessEnergyBE){
        IEnergyStorage energyStorage = wirelessEnergyBE.getLinkStorage();
        if(energyStorage != null){
            if(state.getValue(WirelessEnergyBlock.SEND)){
                for (BlockPos target : wirelessEnergyBE.getReceivePos()){
                    BlockEntity blockEntity = level.getBlockEntity(target);
                    if(blockEntity instanceof WirelessEnergyBE) {
                        IEnergyStorage targetStorage = ((WirelessEnergyBE) blockEntity).getLinkStorage();
                        if (energyStorage == targetStorage) continue;
                        if (energyStorage.getEnergyStored() > 0 && targetStorage.canReceive() && energyStorage.canExtract()) {
                            targetStorage.receiveEnergy(Math.min(energyStorage.getEnergyStored(), maxSend), false);
                            energyStorage.extractEnergy(Math.min(energyStorage.getEnergyStored(), maxSend), false);
                        }
                    }
                }
            }
        }
    }

    public IEnergyStorage getLinkStorage(){
        BlockPos blockPos = null;
        Direction direction = null;
        switch (this.getBlockState().getValue(BlockStateProperties.FACING)){
            case UP -> {
                blockPos = getBlockPos().below();
                direction = Direction.DOWN;
            }
            case DOWN -> {
                blockPos = getBlockPos().above();
                direction = Direction.UP;
            }
            case EAST -> {
                blockPos = getBlockPos().west();
                direction = Direction.WEST;
            }
            case WEST -> {
                blockPos = getBlockPos().east();
                direction = Direction.EAST;
            }
            case NORTH -> {
                blockPos = getBlockPos().south();
                direction = Direction.SOUTH;
            }
            case SOUTH -> {
                blockPos = getBlockPos().north();
                direction = Direction.NORTH;
            }
        }
        return level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPos, direction);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        List<Integer> list = new ArrayList<>();
        for (BlockPos pos : receivePos){
            list.add(pos.getX());
            list.add(pos.getY());
            list.add(pos.getZ());
        }
        tag.putIntArray("receive_pos", list);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        int[] pos = tag.getIntArray("receive_pos");
        for (int i = 0; i < pos.length / 3; i++){
            BlockPos blockPos = new BlockPos(pos[i * 3], pos[i * 3 + 1], pos[i * 3 + 2]);
            receivePos.add(blockPos);
        }
    }

    public List<BlockPos> getReceivePos() {
        return this.receivePos;
    }
}
