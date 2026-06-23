package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity;

import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public interface IEnergyBlockEntity {

    public EnergyHandler getEnergy();

    public void setEnergy(int i);
}
