package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.BiomassGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.TemperatureGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ThermalGeneratorBlock;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class MoeModelProvider extends ModelProvider {
    public MoeModelProvider(PackOutput output) {
        super(output, ElectrodynamicThaumaturgy.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(MoeItems.PRIMARY_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
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
        itemModels.generateFlatItem(MoeItems.INTERMEDIATE_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.ADVANCED_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.COULOMB_DOMAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MIRAGE_PURSUIT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNET_RESONANCE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.LIFE_EXTRACTION_ENHANCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.SAGE_S_MAGNETISM_SEAL.get(), ModelTemplates.FLAT_ITEM);
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
        itemModels.generateFlatItem(MoeItems.RADIANT_MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.STELLAR_MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(MoeItems.MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(MoeItems.ELECTROMAGNETIC_ROD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(),
                getModel("electromagnetic_assembly_table")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ENERGY_BLOCK.get(),
                getModel("energy_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get(),
                getModel("electromagnetic_modem_table")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get(),
                getModel("energy_transmission_antenna_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get(),
                getModel("photovoltaic_generator_block")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get())
                                .with(PropertyDispatch.initial(TemperatureGeneratorBlock.WORK_TYPE).select(TemperatureGeneratorBlock.WorkType.NORMAL, getModel("temperature_generator_block_normal"))
                                        .select(TemperatureGeneratorBlock.WorkType.HOT, getModel("temperature_generator_block_hot"))
                                        .select(TemperatureGeneratorBlock.WorkType.COLD, getModel("temperature_generator_block_cold"))
                                        .select(TemperatureGeneratorBlock.WorkType.WORK_A, getModel("temperature_generator_block_work_a"))
                                        .select(TemperatureGeneratorBlock.WorkType.WORK_B, getModel("temperature_generator_block_work_b"))));
        itemModels.itemModelOutput.accept(MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.asItem(), setNormalBlockModule("temperature_generator_block_normal"));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(MoeBlocks.THERMAL_GENERATOR_BLOCK.get())
                                .with(PropertyDispatch.initial(ThermalGeneratorBlock.LIT).select(true, getModel("thermal_generator_block_true"))
                                        .select(false, getModel("thermal_generator_block_false"))));
        itemModels.itemModelOutput.accept(MoeBlocks.THERMAL_GENERATOR_BLOCK.asItem(), setNormalBlockModule("thermal_generator_block_false"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.MAGNETO_CORE_BLOCK.get(),
                getModel("magneto_core_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get(),
                getModel("atomic_reconstruction_machine_block")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(MoeBlocks.BIOMASS_GENERATOR_BLOCK.get())
                                .with(PropertyDispatch.initial(BiomassGeneratorBlock.LIT).select(true, getModel("biomass_generator_block_true"))
                                        .select(false, getModel("biomass_generator_block_false"))));
        itemModels.itemModelOutput.accept(MoeBlocks.BIOMASS_GENERATOR_BLOCK.asItem(), setNormalBlockModule("biomass_generator_block_false"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get(),
                getModel("bio_replication_vat_machine_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.MAGIC_ENCODE_TABLE.get(),
                getModel("magic_encode_table")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(),
                getModel("electromagnetic_driver_machine_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get(),
                getModel("electromagnetic_extractor_machine_block")));
        blockModels.createTrivialCube(MoeBlocks.LIGHT_AIR.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(MoeBlocks.MAGNETO_FUSION_MACHINE_BLOCK.get(),
                getModel("magneto_fusion_machine_block")));
    }

    private MultiVariant getModel(String name){
        return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "block/" + name));
    }

    private ItemModel.Unbaked setNormalBlockModule(String name){
        return ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "block/" + name));
    }
}
