package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity;

import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public interface IMoeEnergyBlockEntity {

    public EnergyHandler getEnergy();

    public void setEnergy(int i);
}
