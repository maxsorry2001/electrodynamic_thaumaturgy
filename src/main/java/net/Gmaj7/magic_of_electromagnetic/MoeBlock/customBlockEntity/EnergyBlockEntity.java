package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class EnergyBlockEntity extends BlockEntity implements IMoeEnergyBlockEntity {

    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(65536) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public EnergyBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setEnergy(tag.getInt("energy"));
    }

    public IEnergyStorage getEnergy() {
        return energy;
    }

    public void setEnergy(int i){
        energy.setEnergy(i);
    }
}
