package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class TemperatureEnergyMakerBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(16384) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public TemperatureEnergyMakerBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_MAKER_BLOCK_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TemperatureEnergyMakerBlockEntity temperatureEnergyMakerBlockEntity){
        if(canEnergyMake(level, pos))
            temperatureEnergyMakerBlockEntity.getEnergy().receiveEnergy(1, false);
    }

    private static boolean canEnergyMake(Level level, BlockPos pos) {
        BlockState blockStateUp = level.getBlockState(pos.above());
        BlockState blockStateDown = level.getBlockState(pos.below());
        return (isCold(blockStateUp) && isHot(blockStateDown)) || (isHot(blockStateUp) && isCold(blockStateDown));
    }

    private static boolean isHot(BlockState blockState) {
        boolean flag = false;
        if(blockState.is(MoeTags.moeBlockTags.HOT_BLOCK)) flag = true;
        else if(blockState.getBlock() instanceof AbstractFurnaceBlock && blockState.getValue(AbstractFurnaceBlock.LIT)) flag = true;
        return flag;
    }

    private static boolean isCold(BlockState blockState) {
        return blockState.is(MoeTags.moeBlockTags.COLD_BLOCK);
    }

    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }
}
