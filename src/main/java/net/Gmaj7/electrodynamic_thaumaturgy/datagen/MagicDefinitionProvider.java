package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.MagicDefinition;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.custom.IMoeMagic;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

import java.util.concurrent.CompletableFuture;

public class MagicDefinitionProvider extends JsonCodecProvider<MagicDefinition> {
    /**
     * @param output         {@linkplain PackOutput} provided by the {}.
     * @param lookupProvider
     */
    public MagicDefinitionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, PackOutput.Target.DATA_PACK, "magic_definition", MagicDefinition.CODEC, lookupProvider, ElectrodynamicThaumaturgy.MODID);
    }

    @Override
    protected void gather() {
        register("ray", 128, 50);
        register("pulsed_plasma", 128, 50);
        register("protecting", 128, 50);
        register("exciting", 128, 50);
        register("electric_field_domain", 128, 50);
        register("attract", 128, 50);
        register("tree_current", 128, 50);
        register("refraction", 128, 50);
        register("electric_energy_release", 128, 50);
        register("magnetic_recombination_cannon", 128, 50);
        register("electromagnetic_assault", 128, 50);
        register("magma_lighting", 128, 50);
        register("st_elmo_s_fire", 128, 50);
        register("hydrogen_bond_fracture", 128, 50);
        register("lighting_strike", 128, 50);
        register("magnet_resonance", 128, 50);
        register("block_nerve", 128, 50);
        register("disturbing_by_high_intensity_magnetic", 128, 50);
        register("coulomb_domain", 128, 50);
        register("domain_reconstruction", 128, 50);
        register("mirage_pursuit", 128, 50);
        register("magnetic_flux_cascade", 128, 50);
        register("frequency_division_arrow_rain", 128, 50);
        register("sage_s_magnetism_seal", 128, 50);
        register("photoacoustic_pulse", 128, 50);
        register("photo_corrosive_nova", 128, 50);
    }

    private void register(String name, int energyCost, int coolDown){
        Identifier identifier = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name);
        ResourceKey<IMoeMagic> resourceKey = ResourceKey.create(MoeRegistries.MAGIC_KEY, identifier);
        MagicDefinition magicDefinition = new MagicDefinition(resourceKey, energyCost, coolDown, "item." + ElectrodynamicThaumaturgy.MODID + "." + name + "_module");
        unconditional(identifier, magicDefinition);
    }
}
