package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class PhotovoltaicEnergyMakerBE extends AbstractEnergyMakerBE {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(16384) {
        @Override
        public void change(int i) {
            if(!level.isClientSide()){
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(i, getBlockPos()));
            }
        }
    };
    public PhotovoltaicEnergyMakerBE(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.PHOTOVOLTAIC_ENERGY_MAKER_BE.get(), pos, blockState);
    }

    @Override
    protected void energyMake(AbstractEnergyMakerBE blockEntity) {
        int i = Math.max( level.getBrightness(LightLayer.SKY, getBlockPos().above()) - level.getSkyDarken(),  level.getBrightness(LightLayer.BLOCK, getBlockPos().above()));
        blockEntity.getEnergy().receiveEnergy(i, false);
    }

    protected boolean canEnergyMake() {
        int blockLight = level.getBrightness(LightLayer.BLOCK, getBlockPos().above());
        return level.isDay() || blockLight > 0;
    }



    @Override
    public IEnergyStorage getEnergy() {
        return energy;
    }

    @Override
    public void setEnergy(int i) {
        energy.setEnergy(i);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energy.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energy.setEnergy(tag.getInt("energy"));
    }
}
