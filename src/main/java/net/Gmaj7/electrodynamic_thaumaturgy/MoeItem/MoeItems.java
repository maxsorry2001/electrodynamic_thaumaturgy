package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.ElectromagneticLevel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeItems {
    public static final DeferredRegister.Items MOE_ITEM = DeferredRegister.createItems(ElectrodynamicThaumaturgy.MODID);

    public static final DeferredItem<BlockItem> ENERGY_BLOCK = MOE_ITEM.registerItem("energy_block",
            (properties -> new EnergyBlockItem(MoeBlocks.ENERGY_BLOCK.get(), properties.stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0))));

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
    public static final DeferredItem<Item> EMPTY_MAGIC_MODULE = MOE_ITEM.registerItem("empty_module",
            (properties -> new MoeMagicTypeModuleItem(properties.stacksTo(1), true)));
    public static final DeferredItem<Item> PRIMARY_CODE_MODULE = MOE_ITEM.registerItem("primary_code_module",
            (properties) -> new Item(properties.stacksTo(16)));
    public static final DeferredItem<Item> INTERMEDIATE_CODE_MODULE = MOE_ITEM.registerItem("intermediate_code_module",
            (properties) -> new Item(properties.stacksTo(16)));
    public static final DeferredItem<Item> ADVANCED_CODE_MODULE = MOE_ITEM.registerItem("advanced_code_module",
            (properties) -> new Item(properties.stacksTo(16)));

    public static final DeferredItem<Item> PRIMARY_LC = MOE_ITEM.registerItem("primary_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.PRIMARY, properties.stacksTo(1)));
    public static final DeferredItem<Item> INTERMEDIATE_LC = MOE_ITEM.registerItem("intermediate_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.INTERMEDIATE, properties.stacksTo(1)));
    public static final DeferredItem<Item> ADVANCED_LC = MOE_ITEM.registerItem("advanced_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.ADVANCED, properties.stacksTo(1)));
    public static final DeferredItem<Item> SUPERCONDUCTING_LC = MOE_ITEM.registerItem("superconducting_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.SUPERCONDUCTING, properties.stacksTo(1)));
    public static final DeferredItem<Item> EMPTY_LC = MOE_ITEM.registerItem("empty_lc",
            (properties) -> new LcOscillatorModuleItem(ElectromagneticLevel.EMPTY, properties.stacksTo(1)));

    public static final DeferredItem<Item> PRIMARY_POWER = MOE_ITEM.registerItem("primary_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.PRIMARY, properties.stacksTo(1)));
    public static final DeferredItem<Item> INTERMEDIATE_POWER = MOE_ITEM.registerItem("intermediate_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.INTERMEDIATE, properties.stacksTo(1)));
    public static final DeferredItem<Item> ADVANCED_POWER = MOE_ITEM.registerItem("advanced_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.ADVANCED, properties.stacksTo(1)));
    public static final DeferredItem<Item> SUPERCONDUCTING_POWER = MOE_ITEM.registerItem("superconducting_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.SUPERCONDUCTING, properties.stacksTo(1)));
    public static final DeferredItem<Item> EMPTY_POWER = MOE_ITEM.registerItem("empty_power",
            (properties) -> new PowerAmplifierItem(ElectromagneticLevel.EMPTY, properties.stacksTo(1)));

    public static final DeferredItem<Item> STRENGTH_ENHANCE = MOE_ITEM.registerItem("strength_enhance",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.STRENGTH, properties.stacksTo(1)));
    public static final DeferredItem<Item> COOLDOWN_ENHANCE = MOE_ITEM.registerItem("cooldown_enhance",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.COOLDOWN, properties.stacksTo(1)));
    public static final DeferredItem<Item> EFFICIENCY_ENHANCE = MOE_ITEM.registerItem("efficiency_enhance",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.EFFICIENCY, properties.stacksTo(1)));
    public static final DeferredItem<Item> ENTROPY_ENHANCE = MOE_ITEM.registerItem("entropy_enhance",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.ENTROPY, properties.stacksTo(1)));
    public static final DeferredItem<Item> LIFE_EXTRACTION_ENHANCE = MOE_ITEM.registerItem("life_extraction_enhance",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.LIFE_EXTRACTION, properties.stacksTo(1)));
    public static final DeferredItem<Item> ENHANCE_MODEM_BASEBOARD = MOE_ITEM.registerItem("enhance_modem_baseboard",
            (properties) -> new EnhancementModulateItem(EnhancementData.EnhancementType.EMPTY, properties));

    public static final DeferredItem<Item> ENERGY_CORE = MOE_ITEM.registerSimpleItem("energy_core");
    public static final DeferredItem<Item> SUPERCONDUCTING_UPDATE = MOE_ITEM.registerItem("superconducting_update",
            (properties) -> new SuperconductingUpdateItem(properties));
    public static final DeferredItem<Item> POTATO_BATTERY = MOE_ITEM.registerItem("potato_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> CARROT_BATTERY = MOE_ITEM.registerItem("carrot_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> SOLUTION_BATTERY = MOE_ITEM.registerItem("solution_battery",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));
    public static final DeferredItem<Item> POWER_BANK = MOE_ITEM.registerItem("power_bank",
            (properties) -> new BatteryItem(properties.stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)));
    public static final DeferredItem<Item> GENETIC_RECORDER = MOE_ITEM.registerItem("genetic_recorder",
            (properties) -> new Item(properties.stacksTo(1)));
    public static final DeferredItem<Item> FILTER_SETTING = MOE_ITEM.registerItem("filter_setting",
            (properties) -> new FilterSettingItem(properties.stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_CONTAINER.get(), ItemContainerContents.EMPTY)
                    .component(MoeDataComponentTypes.FILTER_WHITE.get(), true)));

    public static final DeferredItem<Item> MAGNO_WRENCH = MOE_ITEM.registerItem("magno_wrench",
            (properties) -> new MagnoWrenchItem(properties.stacksTo(1)));

    public static final DeferredItem<Item> MAGNO_INGOT = MOE_ITEM.registerSimpleItem("magno_ingot");
    public static final DeferredItem<Item> RADIANT_MAGNO_INGOT = MOE_ITEM.registerSimpleItem("radiant_magno_ingot");
    public static final DeferredItem<Item> STELLAR_MAGNO_INGOT = MOE_ITEM.registerSimpleItem("stellar_magno_ingot");

    public static final DeferredItem<Item> COPPER_DUST = MOE_ITEM.registerSimpleItem("copper_dust");
    public static final DeferredItem<Item> IRON_DUST = MOE_ITEM.registerSimpleItem("iron_dust");
    public static final DeferredItem<Item> GOLD_DUST = MOE_ITEM.registerSimpleItem("gold_dust");
    public static final DeferredItem<Item> NETHERITE_DUST = MOE_ITEM.registerSimpleItem("netherite_dust");

    public static final DeferredItem<Item> ELECTROMAGNETIC_ROD = MOE_ITEM.registerItem("electromagnetic_rod",
            (properties) -> new MagicCastItem(properties.stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(MoeDataComponentTypes.MOE_CONTAINER.get(), ItemContainerContents.EMPTY)
                    .component(MoeDataComponentTypes.MAGIC_SELECT.get(), 2)
                    .component(MoeDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.defaultData)));

    public static final DeferredItem<Item> MAGNETO_ENTROPY_WITCH_ENTITY_SPAWN_EGG = MOE_ITEM.registerItem("magneto_entropy_witch_entity_spawn_egg",
            (properties) -> new SpawnEggItem(properties));

    private static DeferredItem<Item> registerMagicModule(String name){
        return MOE_ITEM.registerItem(name + "_module", (properties -> new MoeMagicTypeModuleItem(properties.stacksTo(1).component(MoeDataComponentTypes.MAGIC_DEF_LOCATION.get(), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name)))));
    }
}
