package net.Gmaj7.electrodynamic_thaumaturgy.init;

import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

public abstract class BlockEntityEnergyHandler extends SimpleEnergyHandler {
    public BlockEntityEnergyHandler(int capacity) {
        super(capacity);
    }

    public BlockEntityEnergyHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public BlockEntityEnergyHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public BlockEntityEnergyHandler(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int i){
        this.energy = i;
    }
}
