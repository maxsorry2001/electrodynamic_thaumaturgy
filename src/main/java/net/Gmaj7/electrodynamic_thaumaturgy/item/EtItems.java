package net.Gmaj7.electrodynamic_thaumaturgy.item;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.fluid.EtFluids;
import net.Gmaj7.electrodynamic_thaumaturgy.init.ElectromagneticLevel;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EtItems {
    public static final DeferredRegister.Items ITEM = DeferredRegister.createItems(ElectrodynamicThaumaturgy.MODID);

    public static final DeferredItem<BlockItem> ENERGY_BLOCK = ITEM.registerItem("energy_block",
            (properties -> new EnergyBlockItem(EtBlocks.ENERGY_BLOCK.get(), properties.stacksTo(1)
                    .component(EtDataComponentTypes.ET_ENERGY.get(), 0))));
    public static final DeferredItem<BlockItem> FLUID_BLOCK = ITEM.registerItem("fluid_block",
            (properties -> new FluidBlockItem(EtBlocks.FLUID_BLOCK.get(), properties.stacksTo(1)
                    .component(EtDataComponentTypes.FLUID_CONTAINER.get(), SimpleFluidContent.EMPTY))));

    public static final DeferredItem<Item> RAY_MODULE = registerMagicModule("ray");
    public static final DeferredItem<Item> PULSED_PLASMA_MODULE = registerMagicModule("pulsed_plasma");
    public static final DeferredItem<Item> PROTECTING_MODULE = registerMagicModule("protecting");
    public static final DeferredItem<Item> EXCITING_MODULE = registerMagicModule("exciting");
    public static final DeferredItem<Item> ELECTRIC_FIELD_DOMAIN_MODULE = registerMagicModule("electric_field_domain");
    public static final DeferredItem<Item> ATTRACT_MODULE = registerMagicModule("attract");
    public static final DeferredItem<Item> TREE_CURRENT_MODULE = registerMagicModule("tree_current");
    public static final DeferredItem<Item> REFRACTION_MODULE = registerMagicModule("refraction");
    public static final DeferredItem<Item> ELECTRIC_ENERGY_RELEASE_MODULE = registerMagicModule("electric_energy_release");
    public static final DeferredItem<Item> MAGNETIC_RECOMBINATION_CANNON_MODULE = registerMagicModule("magnetic_recombination_cannon");
    public static final DeferredItem<Item> ELECTROMAGNETIC_ASSAULT_MODULE = registerMagicModule("electromagnetic_assault");
    public static final DeferredItem<Item> MAGMA_LIGHTING_MODULE = registerMagicModule("magma_lighting");
    public static final DeferredItem<Item> ST_ELMO_S_FIRE_MODULE = registerMagicModule("st_elmo_s_fire");
    public static final DeferredItem<Item> HYDROGEN_BOND_FRACTURE_MODULE = registerMagicModule("hydrogen_bond_fracture");
    public static final DeferredItem<Item> LIGHTING_STRIKE_MODULE = registerMagicModule("lighting_strike");
    public static final DeferredItem<Item> MAGNET_RESONANCE_MODULE = registerMagicModule("magnet_resonance");
    public static final DeferredItem<Item> BLOCK_NERVE_MODULE = registerMagicModule("block_nerve");
    public static final DeferredItem<Item> DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE = registerMagicModule("disturbing_by_high_intensity_magnetic");
    public static final DeferredItem<Item> COULOMB_DOMAIN_MODULE = registerMagicModule("coulomb_domain");
    public static final DeferredItem<Item> DOMAIN_RECONSTRUCTION_MODULE = registerMagicModule("domain_reconstruction");
    public static final DeferredItem<Item> MIRAGE_PURSUIT_MODULE = registerMagicModule("mirage_pursuit");
    public static final DeferredItem<Item> MAGNETIC_FLUX_CASCADE_MODULE = registerMagicModule("magnetic_flux_cascade");
    public static final DeferredItem<Item> FREQUENCY_DIVISION_ARROW_RAIN_MODULE = registerMagicModule("frequency_division_arrow_rain");
    public static final DeferredItem<Item> SAGE_S_MAGNETISM_SEAL = registerMagicModule("sage_s_magnetism_seal");
    public static final DeferredItem<Item> PHOTOACOUSTIC_PULSE_MODULE = registerMagicModule("photoacoustic_pulse");
    public static final DeferredItem<Item> PHOTO_CORROSIVE_NOVA_MODULE = registerMagicModule("photo_corrosive_nova");
    public static final DeferredItem<Item> EMPTY_MAGIC_MODULE = ITEM.registerItem("empty_module",
            (properties -> new EtMagicTypeModuleItem(properties.stacksTo(1), true)));
    public static final DeferredItem<Item> PRIMARY_CODE_MODULE = ITEM.registerItem("primary_code_module",
            (properties) -> new Item(properties.stacksTo(16)));
    public static final DeferredItem<Item> INTERMEDIATE_CODE_MODULE = ITEM.registerItem("intermediate_code_module",
            (properties) -> new Item(properties.stacksTo(16)));
    public static final DeferredItem<Item> ADVANCED_CODE_MODULE = ITEM.registerItem("advanced_code_module",
            (properties) -> new Item(properties.stacksTo(16)));

    public static final DeferredItem<Item> PRIMARY_LC = ITEM.registerItem("primary_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.PRIMARY, properties.stacksTo(1)));
    public static final DeferredItem<Item> INTERMEDIATE_LC = ITEM.registerItem("intermediate_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.INTERMEDIATE, properties.stacksTo(1)));
    public static final DeferredItem<Item> ADVANCED_LC = ITEM.registerItem("advanced_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.ADVANCED, properties.stacksTo(1)));
    public static final DeferredItem<Item> SUPERCONDUCTING_LC = ITEM.registerItem("superconducting_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.SUPERCONDUCTING, properties.stacksTo(1)));
    public static final DeferredItem<Item> EMPTY_LC = ITEM.registerItem("empty_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.EMPTY, properties.stacksTo(1)));

    public static final DeferredItem<Item> PRIMARY_POWER = ITEM.registerItem("primary_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.PRIMARY, properties.stacksTo(1)));
    public static final DeferredItem<Item> INTERMEDIATE_POWER = ITEM.registerItem("intermediate_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.INTERMEDIATE, properties.stacksTo(1)));
    public static final DeferredItem<Item> ADVANCED_POWER = ITEM.registerItem("advanced_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.ADVANCED, properties.stacksTo(1)));
    public static final DeferredItem<Item> SUPERCONDUCTING_POWER = ITEM.registerItem("superconducting_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.SUPERCONDUCTING, properties.stacksTo(1)));
    public static final DeferredItem<Item> EMPTY_POWER = ITEM.registerItem("empty_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.EMPTY, properties.stacksTo(1)));

    public static final DeferredItem<Item> ENHANCE_CHIP = ITEM.registerItem("enhance_chip",
            (properties -> new EnhancementChipItem(properties.component(EtDataComponentTypes.ENHANCEMENT_DATA, EnhancementData.savvedData).stacksTo(1))));

    public static final DeferredItem<Item> ENERGY_CORE = ITEM.registerSimpleItem("energy_core");
    public static final DeferredItem<Item> SUPERCONDUCTING_UPDATE = ITEM.registerItem("superconducting_update",
            (properties) -> new SuperconductingUpdateItem(properties));
    public static final DeferredItem<Item> POTATO_BATTERY = ITEM.registerItem("potato_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(EtDataComponentTypes.ET_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> CARROT_BATTERY = ITEM.registerItem("carrot_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(EtDataComponentTypes.ET_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> SOLUTION_BATTERY = ITEM.registerItem("solution_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(EtDataComponentTypes.ET_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> POWER_BANK = ITEM.registerItem("power_bank",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(EtDataComponentTypes.ET_ENERGY.get(), 0)));
    public static final DeferredItem<Item> GENETIC_RECORDER = ITEM.registerItem("genetic_recorder",
            (properties) -> new Item(properties.stacksTo(1)));
    public static final DeferredItem<Item> FILTER_SETTING = ITEM.registerItem("net_filter",
            (properties) -> new FilterSettingItem(properties.stacksTo(1)
                    .component(EtDataComponentTypes.FILTER_CONTAINER.get(), ItemContainerContents.EMPTY)
                    .component(EtDataComponentTypes.FILTER_WHITE.get(), true)));

    public static final DeferredItem<Item> MAGNO_WRENCH = ITEM.registerItem("magno_wrench",
            (properties) -> new MagnoWrenchItem(properties.stacksTo(1)));
    public static final DeferredItem<Item> MAGNETIC_FLUX_BUCKET = ITEM.registerItem("magnetic_flux_bucket",
            (properties -> new BucketItem(EtFluids.MAGNETIC_FLUX_SOURCE.get(), properties.stacksTo(1).craftRemainder(Items.BUCKET))));

    public static final DeferredItem<Item> MAGNO_INGOT = ITEM.registerSimpleItem("magno_ingot");
    public static final DeferredItem<Item> RADIANT_MAGNO_INGOT = ITEM.registerSimpleItem("radiant_magno_ingot");
    public static final DeferredItem<Item> STELLAR_MAGNO_INGOT = ITEM.registerSimpleItem("stellar_magno_ingot");

    public static final DeferredItem<Item> COPPER_DUST = ITEM.registerSimpleItem("copper_dust");
    public static final DeferredItem<Item> IRON_DUST = ITEM.registerSimpleItem("iron_dust");
    public static final DeferredItem<Item> GOLD_DUST = ITEM.registerSimpleItem("gold_dust");
    public static final DeferredItem<Item> NETHERITE_DUST = ITEM.registerSimpleItem("netherite_dust");

    public static final DeferredItem<Item> RESONANT_CRYSTAL = ITEM.registerSimpleItem("resonant_crystal");
    public static final DeferredItem<Item> POLAR_CRYSTAL = ITEM.registerSimpleItem("polar_crystal");
    public static final DeferredItem<Item> GLOWING_ESSENCE = ITEM.registerSimpleItem("glowing_essence");
    public static final DeferredItem<Item> MAGNETIC_ESSENCE = ITEM.registerSimpleItem("magnetic_essence");
    public static final DeferredItem<Item> MONOPOLE_N = ITEM.registerSimpleItem("monopole_n");
    public static final DeferredItem<Item> MONOPOLE_S = ITEM.registerSimpleItem("monopole_s");

    public static final DeferredItem<Item> ELECTROMAGNETIC_ROD = ITEM.registerItem("electromagnetic_rod",
            (properties) -> new MagicCastItem(properties.stacksTo(1)
                    .component(EtDataComponentTypes.ET_ENERGY.get(), 0)
                    .component(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerData.getEmptyRod())
                    .component(EtDataComponentTypes.MAGIC_SELECT.get(), 2)
                    .component(EtDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.savvedData)));
    public static final DeferredItem<Item> PULSE_BOW = ITEM.registerItem("pulse_bow",
            (properties) -> new PulseBow(properties.stacksTo(1)
                    .component(EtDataComponentTypes.ET_ENERGY.get(), 0)
                    .component(EtDataComponentTypes.BOW_WORK_PATTERN.get(), 0)
                    .component(EtDataComponentTypes.ET_CONTAINER.get(), ItemContainerData.getEmptyBow())
                    .component(EtDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.savvedData)));

    public static final DeferredItem<Item> MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG = ITEM.registerItem("magneto_entropy_witch_entity_spawn_egg",
            (properties) -> new SpawnEggItem(properties));

    public static final DeferredItem<Item> FLUID_FAKE_ITEM = ITEM.registerItem("fluid_fake_item",
            (properties -> new FluidFakeItem(properties.stacksTo(1).component(EtDataComponentTypes.FLUID_CONTAINER.get(), SimpleFluidContent.EMPTY))));

    private static DeferredItem<Item> registerMagicModule(String name){
        return ITEM.registerItem(name + "_module", (properties -> new EtMagicTypeModuleItem(properties.stacksTo(1).component(EtDataComponentTypes.MAGIC_DEF_LOCATION.get(), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name)))));
    }
}
