package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class MoeModelProvider extends ModelProvider {
    public MoeModelProvider(PackOutput output) {
        super(output, ElectrodynamicThaumaturgy.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(MoeItems.EMPTY_PRIMARY_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.RAY_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PULSED_PLASMA_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EXCITING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PROTECTING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.TREE_CURRENT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ATTRACT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.REFRACTION_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ST_ELMO_S_FIRE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGMA_LIGHTING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PRIMARY_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.INTERMEDIATE_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ADVANCED_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SUPERCONDUCTING_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EMPTY_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PRIMARY_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.INTERMEDIATE_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ADVANCED_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SUPERCONDUCTING_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EMPTY_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SOLUTION_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.CARROT_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.POTATO_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.POWER_BANK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ENERGY_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SUPERCONDUCTING_UPDATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EMPTY_INTERMEDIATE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EMPTY_ADVANCED_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.COULOMB_DOMAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MIRAGE_PURSUIT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNET_RESONANCE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.LIFE_EXTRACTION_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SAINT_SUMMON_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.GENETIC_RECORDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PHOTOACOUSTIC_PULSE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.PHOTO_CORROSIVE_NOVA_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.LIGHTING_STRIKE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.BLOCK_NERVE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.STRENGTH_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.COOLDOWN_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.EFFICIENCY_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ENTROPY_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ENHANCE_MODEM_BASEBOARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.HARMONIC_SOVEREIGN_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(MoeItems.ELECTROMAGNETIC_ROD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);


        blockModels.createTrivialCube(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get());
        blockModels.createTrivialCube(MoeBlocks.ENERGY_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get());
        blockModels.createTrivialCube(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.THERMAL_GENERATOR_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.HARMONIC_CORE_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.BIOMASS_GENERATOR_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get());
        blockModels.createTrivialCube(MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.NITROGEN_HARVESTER_BLOCK.get());
        blockModels.createTrivialCube(MoeBlocks.LIGHT_AIR.get());
    }
}
