package net.Gmaj7.electrodynamic_thaumaturgy.init.mixinData;

public class Protective {
    private float protecting;

    public Protective(float num){
        this.protecting = num;
    }

    public float getProtecting() {
        return protecting;
    }

    public void setProtecting(float protecting) {
        this.protecting = protecting > 0 ? protecting : 0;
    }
}
