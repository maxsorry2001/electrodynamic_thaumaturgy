package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeBlockEnergyStorage;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;

public class PhotovoltaicEnergyMakerBE extends AbstractEnergyMakerBE {
    private final MoeBlockEnergyStorage energy = new MoeBlockEnergyStorage(1048576) {
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
        blockEntity.getEnergy().receiveEnergy(i * 8, false);
    }

    protected boolean canEnergyMake() {
        int blockLight = level.getBrightness(LightLayer.BLOCK, getBlockPos().above());
        return (level.isDay() && level.canSeeSky(this.getBlockPos().above())) || blockLight > 0;
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
