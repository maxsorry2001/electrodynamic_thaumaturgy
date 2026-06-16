package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity;

import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public interface IEnergyBlockEntity {

    public EnergyHandler getEnergy();

    public void setEnergy(int i);
}
