package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class EnergyMakerBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(16384) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public EnergyMakerBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_MAKER_BLOCK_BE.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyMakerBlockEntity energyMakerBlockEntity){
        energyMakerBlockEntity.getEnergy().receiveEnergy(1, false);
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
