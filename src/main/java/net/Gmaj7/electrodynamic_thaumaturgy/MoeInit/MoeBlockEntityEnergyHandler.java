package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;

public abstract class MoeBlockEntityEnergyHandler extends SimpleEnergyHandler {
    public MoeBlockEntityEnergyHandler(int capacity) {
        super(capacity);
    }

    public MoeBlockEntityEnergyHandler(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public MoeBlockEntityEnergyHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public MoeBlockEntityEnergyHandler(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int i){
        this.energy = i;
    }
}
