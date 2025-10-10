package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MoeItemModelProvider extends ItemModelProvider {
    public MoeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EelectrodynamicThaumaturgy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MoeItems.EMPTY_PRIMARY_MODULE.get());
        basicItem(MoeItems.RAY_MODULE.get());
        basicItem(MoeItems.PULSED_PLASMA_MODULE.get());
        basicItem(MoeItems.EXCITING_MODULE.get());
        basicItem(MoeItems.PROTECTING_MODULE.get());
        basicItem(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get());
        basicItem(MoeItems.TREE_CURRENT_MODULE.get());
        basicItem(MoeItems.ATTRACT_MODULE.get());
        basicItem(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get());
        basicItem(MoeItems.REFRACTION_MODULE.get());
        basicItem(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get());
        basicItem(MoeItems.ST_ELMO_S_FIRE_MODULE.get());
        basicItem(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get());
        basicItem(MoeItems.MAGMA_LIGHTING_MODULE.get());
        basicItem(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE.get());
        basicItem(MoeItems.IRON_POWER.get());
        basicItem(MoeItems.GOLD_POWER.get());
        basicItem(MoeItems.COPPER_POWER.get());
        basicItem(MoeItems.SUPERCONDUCTING_POWER.get());
        basicItem(MoeItems.EMPTY_POWER.get());
        basicItem(MoeItems.IRON_LC.get());
        basicItem(MoeItems.GOLD_LC.get());
        basicItem(MoeItems.COPPER_LC.get());
        basicItem(MoeItems.SUPERCONDUCTING_LC.get());
        basicItem(MoeItems.EMPTY_LC.get());
        basicItem(MoeItems.SOLUTION_BATTERY.get());
        basicItem(MoeItems.CARROT_BATTERY.get());
        basicItem(MoeItems.POTATO_BATTERY.get());
        basicItem(MoeItems.POWER_BANK.get());
        basicItem(MoeItems.IRON_SHEET.get());
        basicItem(MoeItems.COPPER_SHEET.get());
        basicItem(MoeItems.ENERGY_CORE.get());
        basicItem(MoeItems.CAPACITOR.get());
        basicItem(MoeItems.INDUCTANCE.get());
        basicItem(MoeItems.BJT.get());
        basicItem(MoeItems.SI.get());
        basicItem(MoeItems.SUPERCONDUCTING_UPDATE.get());
        basicItem(MoeItems.EMPTY_INTERMEDIATE_MODULE.get());
        basicItem(MoeItems.EMPTY_ADVANCED_MODULE.get());
        basicItem(MoeItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE.get());
        basicItem(MoeItems.COULOMB_DOMAIN_MODULE.get());
        basicItem(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get());
        basicItem(MoeItems.MIRAGE_PURSUIT_MODULE.get());
        basicItem(MoeItems.MAGNET_RESONANCE_MODULE.get());
        basicItem(MoeItems.LIFE_EXTRACTION_ENHANCE.get());
        basicItem(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get());
        basicItem(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get());
        basicItem(MoeItems.SAINT_SUMMON_MODULE.get());
        basicItem(MoeItems.GENETIC_RECORDER.get());
        basicItem(MoeItems.PHOTOACOUSTIC_PULSE_MODULE.get());

        handheldItem(MoeItems.ELECTROMAGNETIC_ROD.get());

        withExistingParent(MoeItems.HARMONIC_SOVEREIGN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
