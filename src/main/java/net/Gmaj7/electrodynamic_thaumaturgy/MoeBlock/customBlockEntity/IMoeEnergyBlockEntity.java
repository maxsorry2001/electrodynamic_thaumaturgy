package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IMoeEnergyBlockEntity {

    public IEnergyStorage getEnergy();

    public void setEnergy(int i);
}
