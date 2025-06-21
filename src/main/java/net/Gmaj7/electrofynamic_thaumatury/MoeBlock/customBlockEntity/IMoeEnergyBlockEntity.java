package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface IMoeEnergyBlockEntity {

    public IEnergyStorage getEnergy();

    public void setEnergy(int i);
}
