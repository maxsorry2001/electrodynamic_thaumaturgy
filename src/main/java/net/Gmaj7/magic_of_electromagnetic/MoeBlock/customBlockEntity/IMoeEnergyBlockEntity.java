package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IMoeEnergyBlockEntity {

    public IEnergyStorage getEnergy();

    public void setEnergy(int i);
}
