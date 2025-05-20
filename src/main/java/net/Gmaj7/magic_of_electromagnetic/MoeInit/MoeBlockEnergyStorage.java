package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.neoforged.neoforge.energy.EnergyStorage;

public abstract class MoeBlockEnergyStorage extends EnergyStorage {
    public MoeBlockEnergyStorage(int capacity) {
        super(capacity);
    }

    public MoeBlockEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public MoeBlockEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public MoeBlockEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        int i = super.receiveEnergy(toReceive, simulate);
        if(i > 0) change(energy);
        return i;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        int i = super.extractEnergy(toExtract, simulate);
        if(i > 0) change(energy);
        return i;
    }

    public void setEnergy(int i){
        this.energy = i;
    }

    public abstract void change(int i);
}
