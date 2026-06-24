package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock.*;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;

import java.util.Optional;

public class ModelProvider extends net.minecraft.client.data.models.ModelProvider {
    public ModelProvider(PackOutput output) {
        super(output, ElectrodynamicThaumaturgy.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(EtItems.PRIMARY_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.RAY_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PULSED_PLASMA_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.EXCITING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PROTECTING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ELECTRIC_FIELD_DOMAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.TREE_CURRENT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ATTRACT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ELECTRIC_ENERGY_RELEASE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.REFRACTION_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ST_ELMO_S_FIRE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ELECTROMAGNETIC_ASSAULT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGMA_LIGHTING_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.HYDROGEN_BOND_FRACTURE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PRIMARY_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.INTERMEDIATE_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ADVANCED_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.SUPERCONDUCTING_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.EMPTY_POWER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PRIMARY_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.INTERMEDIATE_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ADVANCED_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.SUPERCONDUCTING_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.EMPTY_LC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.SOLUTION_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.CARROT_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.POTATO_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.POWER_BANK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ENERGY_CORE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.SUPERCONDUCTING_UPDATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.INTERMEDIATE_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ADVANCED_CODE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.COULOMB_DOMAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.DOMAIN_RECONSTRUCTION_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MIRAGE_PURSUIT_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNET_RESONANCE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNETIC_FLUX_CASCADE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.SAGE_S_MAGNETISM_SEAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.GENETIC_RECORDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PHOTOACOUSTIC_PULSE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.PHOTO_CORROSIVE_NOVA_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.LIGHTING_STRIKE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.BLOCK_NERVE_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.ENHANCE_BOARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.RADIANT_MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.STELLAR_MAGNO_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.EMPTY_MAGIC_MODULE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.IRON_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.COPPER_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.GOLD_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.NETHERITE_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNO_WRENCH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.FILTER_SETTING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.RESONANT_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.POLAR_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.GLOWING_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MAGNETIC_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MONOPOLE_N.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(EtItems.MONOPOLE_S.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(EtItems.ELECTROMAGNETIC_ROD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.createFlatItemModel(EtItems.PULSE_BOW.get(), ModelTemplates.BOW);
        itemModels.generateBow(EtItems.PULSE_BOW.get());

        itemModels.generateFlatItem(EtItems.MAGNETIC_FLUX_BUCKET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.itemModelOutput.accept(
                EtItems.FLUID_FAKE_ITEM.get(),
                new DynamicFluidContainerModel.Unbaked(
                        new DynamicFluidContainerModel.Textures(
                                Optional.empty(),
                                Optional.of(new Material(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item/fluid_outline"))),
                                Optional.of(new Material(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item/fluid_mask"))),
                                Optional.empty()
                        ),
                        Fluids.WATER,
                        true,
                        true,
                        false
                )
        );

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get(),
                getModel("electromagnetic_assembly_table")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ENERGY_BLOCK.get(),
                getModel("energy_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.FLUID_BLOCK.get(),
                getModel("fluid_block")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ELECTROMAGNETIC_MODEM_TABLE.get(),
                getModel("electromagnetic_modem_table")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(EtBlocks.ENERGY_TRANSMISSION_ANTENNA.get())
                        .with(PropertyDispatch.initial(EnergyTransmissionAtenna.SEND).select(true, getModel("energy_transmission_antenna_send")).select(false, getModel("energy_transmission_antenna_receive")))
                        .with(PropertyDispatch.modify(EnergyTransmissionAtenna.FACING).select(Direction.DOWN, BlockModelGenerators.X_ROT_180)
                                .select(Direction.UP, BlockModelGenerators.NOP)
                                .select(Direction.EAST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
                                .select(Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
                                .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
                                .select(Direction.NORTH, BlockModelGenerators.X_ROT_90)));
        itemModels.itemModelOutput.accept(EtBlocks.ENERGY_TRANSMISSION_ANTENNA.asItem(), setNormalBlockModule("energy_transmission_antenna_send"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.PHOTOVOLTAIC_GENERATOR.get(),
                getModel("photovoltaic_generator")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(EtBlocks.TEMPERATURE_GENERATOR.get())
                                .with(PropertyDispatch.initial(TemperatureGenerator.WORK_TYPE).select(TemperatureGenerator.WorkType.NORMAL, getModel("temperature_generator_normal"))
                                        .select(TemperatureGenerator.WorkType.HOT, getModel("temperature_generator_hot"))
                                        .select(TemperatureGenerator.WorkType.COLD, getModel("temperature_generator_cold"))
                                        .select(TemperatureGenerator.WorkType.WORK_A, getModel("temperature_generator_work_a"))
                                        .select(TemperatureGenerator.WorkType.WORK_B, getModel("temperature_generator_work_b"))));
        itemModels.itemModelOutput.accept(EtBlocks.TEMPERATURE_GENERATOR.asItem(), setNormalBlockModule("temperature_generator_normal"));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(EtBlocks.THERMAL_GENERATOR.get())
                                .with(PropertyDispatch.initial(ThermalGenerator.LIT).select(true, getModel("thermal_generator_true"))
                                        .select(false, getModel("thermal_generator_false"))));
        itemModels.itemModelOutput.accept(EtBlocks.THERMAL_GENERATOR.asItem(), setNormalBlockModule("thermal_generator_false"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.MAGNETO_CORE.get(),
                getModel("magneto_core")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE.get(),
                getModel("atomic_reconstruction_machine")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(EtBlocks.BIOMASS_GENERATOR.get())
                                .with(PropertyDispatch.initial(BiomassGenerator.LIT).select(true, getModel("biomass_generator_true"))
                                        .select(false, getModel("biomass_generator_false"))));
        itemModels.itemModelOutput.accept(EtBlocks.BIOMASS_GENERATOR.asItem(), setNormalBlockModule("biomass_generator_false"));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.BIO_REPLICATION_VAT_MACHINE.get(),
                getModel("bio_replication_vat_machine")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.MAGIC_ENCODE_TABLE.get(),
                getModel("magic_encode_table")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get(),
                getModel("electromagnetic_driver_machine")));
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE.get(), getModel("electromagnetic_extractor_machine"))
                                .with(PropertyDispatch.modify(BlockStateProperties.FACING).select(Direction.DOWN, BlockModelGenerators.NOP)
                                        .select(Direction.UP, BlockModelGenerators.X_ROT_180)
                                        .select(Direction.EAST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
                                        .select(Direction.WEST, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
                                        .select(Direction.NORTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
                                        .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90)
                                ));
        blockModels.createTrivialCube(EtBlocks.LIGHT_AIR.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.MAGNETO_FUSION_MACHINE.get(),
                getModel("magneto_fusion_machine")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE.get(),
                getModel("electromagnetic_dissociation_machine")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.EDDY_CURRENT_REMELTER_MACHINE.get(),
                getModel("eddy_current_remelter_machine")));
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(EtBlocks.ELECTROMAGNETIC_INFUSER_MACHINE.get(),
                getModel("electromagnetic_infuser_machine")));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(EtBlocks.ENERGY_PIPE.get()).with(getModel("energy_pipe"))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.LINK), getModel("energy_pipe_part").with(BlockModelGenerators.Y_ROT_180))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.EXTRACT), getModel("energy_pipe_extract").with(BlockModelGenerators.Y_ROT_180)));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(EtBlocks.ITEM_PIPE.get()).with(getModel("item_pipe"))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.LINK), getModel("item_pipe_part").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.LINK), getModel("item_pipe_part").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.LINK), getModel("item_pipe_part").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.LINK), getModel("item_pipe_part").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.LINK), getModel("item_pipe_part"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.LINK), getModel("item_pipe_part").with(BlockModelGenerators.Y_ROT_180))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.EXTRACT), getModel("item_pipe_extract").with(BlockModelGenerators.Y_ROT_180)));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(EtBlocks.FLUID_PIPE.get()).with(getModel("fluid_pipe"))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.LINK), getModel("fluid_pipe_part").with(BlockModelGenerators.Y_ROT_180))
                .with(new ConditionBuilder().term(AbstractPipe.UP, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract").with(BlockModelGenerators.X_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.DOWN, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract").with(BlockModelGenerators.X_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.EAST, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract").with(BlockModelGenerators.Y_ROT_90))
                .with(new ConditionBuilder().term(AbstractPipe.WEST, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract").with(BlockModelGenerators.Y_ROT_270))
                .with(new ConditionBuilder().term(AbstractPipe.NORTH, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract"))
                .with(new ConditionBuilder().term(AbstractPipe.SOUTH, AbstractPipe.LinkState.EXTRACT), getModel("fluid_pipe_extract").with(BlockModelGenerators.Y_ROT_180)));

        blockModels.createNonTemplateModelBlock(EtBlocks.MAGNETIC_FLUX_FLUID_BLOCK.get());
    }

    protected MultiVariant getModel(String name){
        return BlockModelGenerators.plainVariant(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "block/" + name));
    }

    protected ItemModel.Unbaked setNormalBlockModule(String name){
        return ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "block/" + name));
    }
}
