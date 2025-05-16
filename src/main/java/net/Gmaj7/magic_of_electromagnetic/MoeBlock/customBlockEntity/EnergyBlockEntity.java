package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class EnergyBlockEntity extends BlockEntity {

    private final EnergyStorage ENERGY = new EnergyStorage(65535);
    public EnergyBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoeBlockEntities.ENERGY_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", ENERGY.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ENERGY.extractEnergy(ENERGY.getEnergyStored(), true);
        ENERGY.receiveEnergy(tag.getInt("energy"), true);
    }

    public EnergyStorage getEnergy() {
        return ENERGY;
    }
}
