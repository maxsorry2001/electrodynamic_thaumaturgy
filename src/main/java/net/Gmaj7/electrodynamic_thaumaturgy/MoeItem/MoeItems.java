package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.ElectromagneticLevel;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.*;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeFunction.setEmpty;

public class MoeItems {
    public static final DeferredRegister.Items MOE_ITEM = DeferredRegister.createItems(ElectrodynamicThaumaturgy.MODID);

    public static final DeferredItem<BlockItem> ENERGY_BLOCK = MOE_ITEM.registerItem("energy_block",
            (properties) -> new EnergyBlockItem(MoeBlocks.ENERGY_BLOCK.get(), properties.stacksTo(1)));

    public static final DeferredItem<Item> RAY_MODULE = MOE_ITEM.registerItem("ray_module",
            (properties) -> new MoeMagicTypeModuleItem(new ElectromagneticRay(), properties.stacksTo(1)));
    public static final DeferredItem<Item> PULSED_PLASMA_MODULE = MOE_ITEM.registerItem("pulsed_plasma_module",
            (properties) -> new MoeMagicTypeModuleItem(new PulsedPlasma(), properties.stacksTo(1)));
    public static final DeferredItem<Item> PROTECTING_MODULE = MOE_ITEM.registerItem("protecting_module",
            (properties) -> new MoeMagicTypeModuleItem(new Protecting(), properties.stacksTo(1)));
    public static final DeferredItem<Item> EXCITING_MODULE = MOE_ITEM.registerItem("exciting_module",
            (properties) -> new MoeMagicTypeModuleItem(new Exciting(), properties.stacksTo(1)));
    public static final DeferredItem<Item> ELECTRIC_FIELD_DOMAIN_MODULE = MOE_ITEM.registerItem("electric_field_domain_module",
            (properties) -> new MoeMagicTypeModuleItem(new ElectricFieldDomain(), properties.stacksTo(1)));
    public static final DeferredItem<Item> ATTRACT_MODULE = MOE_ITEM.registerItem("attract_module",
            (properties) -> new MoeMagicTypeModuleItem(new Attract(), properties.stacksTo(1)));
    public static final DeferredItem<Item> TREE_CURRENT_MODULE = MOE_ITEM.registerItem("tree_current_module",
            (properties) -> new MoeMagicTypeModuleItem(new TreeCurrent(), properties.stacksTo(1)));
    public static final DeferredItem<Item> REFRACTION_MODULE = MOE_ITEM.registerItem("refraction_module",
            (properties) -> new MoeMagicTypeModuleItem(new Refraction(), properties.stacksTo(1)));
    public static final DeferredItem<Item> ELECTRIC_ENERGY_RELEASE_MODULE = MOE_ITEM.registerItem("electric_energy_release_module",
            (properties) -> new MoeMagicTypeModuleItem(new ElectricEnergyRelease(), properties.stacksTo(1)));
    public static final DeferredItem<Item> MAGNETIC_RECOMBINATION_CANNON_MODULE = MOE_ITEM.registerItem("magnetic_recombination_cannon_module",
            (properties) -> new MoeMagicTypeModuleItem(new MagneticRecombinationCannon(), properties.stacksTo(1)));
    public static final DeferredItem<Item> ELECTROMAGNETIC_ASSAULT_MODULE = MOE_ITEM.registerItem("electromagnetic_assault_module",
            (properties) -> new MoeMagicTypeModuleItem(new ElectromagneticAssault(), properties.stacksTo(1)));
    public static final DeferredItem<Item> MAGMA_LIGHTING_MODULE = MOE_ITEM.registerItem("magma_lighting_module",
            (properties) -> new MoeMagicTypeModuleItem(new MagmaLighting(), properties.stacksTo(1)));
    public static final DeferredItem<Item> ST_ELMO_S_FIRE_MODULE = MOE_ITEM.registerItem("st_elmo_s_fire_module",
            (properties) -> new MoeMagicTypeModuleItem(new St_Elmo_s_fire(), properties.stacksTo(1)));
    public static final DeferredItem<Item> HYDROGEN_BOND_FRACTURE_MODULE = MOE_ITEM.registerItem("hydrogen_bond_fracture_module",
            (properties) -> new MoeMagicTypeModuleItem(new HydrogenBondFracture(), properties.stacksTo(1)));
    public static final DeferredItem<Item> LIGHTING_STRIKE_MODULE = MOE_ITEM.registerItem("lighting_strike_module",
            (properties) -> new MoeMagicTypeModuleItem(new LightingStrike(), properties.stacksTo(1)));
    public static final DeferredItem<Item> MAGNET_RESONANCE_MODULE = MOE_ITEM.registerItem("magnet_resonance_module",
            (properties) -> new MoeMagicTypeModuleItem(new MagnetResonance(), properties.stacksTo(1)));
    public static final DeferredItem<Item> BLOCK_NERVE_MODULE = MOE_ITEM.registerItem("block_nerve_module",
            (properties) -> new MoeMagicTypeModuleItem(new NerveBlocking(), properties.stacksTo(1)));
    public static final DeferredItem<Item> DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE = MOE_ITEM.registerItem("disturbing_by_high_intensity_magnetic_module",
            (properties) -> new MoeMagicTypeModuleItem(new DisturbingByHighIntensityMagnetic(), properties.stacksTo(1)));
    public static final DeferredItem<Item> COULOMB_DOMAIN_MODULE = MOE_ITEM.registerItem("coulomb_domain_module",
            (properties) -> new MoeMagicTypeModuleItem(new CoulombDomain(), properties.stacksTo(1)));
    public static final DeferredItem<Item> DOMAIN_RECONSTRUCTION_MODULE = MOE_ITEM.registerItem("domain_reconstruction_module",
            (properties) -> new MoeMagicTypeModuleItem(new DomainReconstruction(), properties.stacksTo(1)));
    public static final DeferredItem<Item> MIRAGE_PURSUIT_MODULE = MOE_ITEM.registerItem("mirage_pursuit_module",
            (properties) -> new MoeMagicTypeModuleItem(new MiragePursuit(), properties.stacksTo(1)));
    public static final DeferredItem<Item> MAGNETIC_FLUX_CASCADE_MODULE = MOE_ITEM.registerItem("magnetic_flux_cascade_module",
            (properties) -> new MoeMagicTypeModuleItem(new MagneticFluxCascade(), properties.stacksTo(1)));
    public static final DeferredItem<Item> FREQUENCY_DIVISION_ARROW_RAIN_MODULE = MOE_ITEM.registerItem("frequency_division_arrow_rain_module",
            (properties) -> new MoeMagicTypeModuleItem(new FrequencyDivisionArrowRain(), properties.stacksTo(1)));
    public static final DeferredItem<Item> SAINT_SUMMON_MODULE = MOE_ITEM.registerItem("saint_summon_module",
            (properties) -> new MoeMagicTypeModuleItem(new SaintSummon(), properties.stacksTo(1)));
    public static final DeferredItem<Item> PHOTOACOUSTIC_PULSE_MODULE = MOE_ITEM.registerItem("photoacoustic_pulse_module",
            (properties) -> new MoeMagicTypeModuleItem(new PhotoacousticPulse(), properties.stacksTo(1)));
    public static final DeferredItem<Item> PHOTO_CORROSIVE_NOVA_MODULE = MOE_ITEM.registerItem("photo_corrosive_nova_module",
            (properties) -> new MoeMagicTypeModuleItem(new PhotoCorrosiveNova(), properties.stacksTo(1)));
    public static final DeferredItem<Item> EMPTY_PRIMARY_MODULE = MOE_ITEM.registerItem("empty_primary_module",
            (properties) -> new MoeMagicTypeModuleItem(null, properties.stacksTo(16)));
    public static final DeferredItem<Item> EMPTY_INTERMEDIATE_MODULE = MOE_ITEM.registerItem("empty_intermediate_module",
            (properties) -> new MoeMagicTypeModuleItem(null, properties.stacksTo(16)));
    public static final DeferredItem<Item> EMPTY_ADVANCED_MODULE = MOE_ITEM.registerItem("empty_advanced_module",
            (properties) -> new MoeMagicTypeModuleItem(null, properties.stacksTo(16)));

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

    public static final DeferredItem<Item> MAGNO_INGOT = MOE_ITEM.registerSimpleItem("magno_ingot");

    public static final DeferredItem<Item> ELECTROMAGNETIC_ROD = MOE_ITEM.registerItem("electromagnetic_rod",
            (properties) -> new MagicCastItem(properties.stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .component(MoeDataComponentTypes.MAGIC_SELECT.get(), 2)
                    .component(MoeDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.defaultData)));

    public static final DeferredItem<Item> HARMONIC_SOVEREIGN_SPAWN_EGG = MOE_ITEM.registerItem("harmonic_sovereign_spawn_egg",
            (properties) -> new SpawnEggItem(properties));
}
