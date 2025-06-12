package net.Gmaj7.magic_of_electromagnetic.MoeItem;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.ElectromagneticTier;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementData;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.EnhancementType;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.*;
import net.Gmaj7.magic_of_electromagnetic.magic.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeFunction.setEmpty;

public class MoeItems {
    public static final DeferredRegister.Items MOE_ITEM = DeferredRegister.createItems(MagicOfElectromagnetic.MODID);

    public static final Supplier<BlockItem> ELECTROMAGNETIC_ASSEMBLY_TABLE = MOE_ITEM.registerSimpleBlockItem("electromagnetic_assembly_table", MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
    public static final Supplier<BlockItem> ELECTROMAGNETIC_MODEM_TABLE = MOE_ITEM.registerSimpleBlockItem("electromagnetic_modem_table", MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
    public static final Supplier<BlockItem> ENERGY_BLOCK = MOE_ITEM.register("energy_block",
            () -> new EnergyBlockItem(MoeBlocks.ENERGY_BLOCK.get(), new Item.Properties().stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)));
    public static final Supplier<BlockItem> TEMPERATURE_ENERGY_MAKER_BLOCK = MOE_ITEM.register("temperature_energy_maker",
            () -> new BlockItem(MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<BlockItem> PHOTOVOLTAIC_ENERGY_MAKER_BLOCK = MOE_ITEM.register("photovoltaic_energy_maker",
            () -> new BlockItem(MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<BlockItem> THERMAL_ENERGY_MAKER_BLOCK = MOE_ITEM.register("thermal_energy_maker",
            () -> new BlockItem(MoeBlocks.THERMAL_ENERGY_MAKER_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final Supplier<BlockItem> ENERGY_TRANSMISSION_ANTENNA = MOE_ITEM.register("energy_transmission_antenna",
            () -> new BlockItem(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get(), new Item.Properties().stacksTo(16)));
    public static final Supplier<BlockItem> MAGIC_LITHOGRAPHY_TABLE = MOE_ITEM.register("magic_lithography_table",
            () -> new BlockItem(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get(), new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> RAY_MODULE = MOE_ITEM.register("ray_module",
            () -> new MoeMagicTypeModuleItem(new ElectromagneticRay(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PULSED_PLASMA_MODULE = MOE_ITEM.register("pulsed_plasma_module",
            () -> new MoeMagicTypeModuleItem(new PulsedPlasma(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PROTECT_MODULE = MOE_ITEM.register("protecting_module",
            () -> new MoeMagicTypeModuleItem(new Protecting(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EXCITING_MODULE = MOE_ITEM.register("exciting_module",
            () -> new MoeMagicTypeModuleItem(new Exciting(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ELECTRIC_FIELD_DOMAIN_MODULE = MOE_ITEM.register("electric_field_domain_module",
            () -> new MoeMagicTypeModuleItem(new ElectricFieldDomain(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ATTRACT_MODULE = MOE_ITEM.register("attract_module",
            () -> new MoeMagicTypeModuleItem(new Attract(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TREE_CURRENT_MODULE = MOE_ITEM.register("tree_current_module",
            () -> new MoeMagicTypeModuleItem(new TreeCurrent(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> REFRACTION_MODULE = MOE_ITEM.register("refraction_module",
            () -> new MoeMagicTypeModuleItem(new Refraction(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ELECTRIC_ENERGY_RELEASE_MODULE = MOE_ITEM.register("electric_energy_release_module",
            () -> new MoeMagicTypeModuleItem(new ElectricEnergyRelease(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> PLASMA_TORCH_MODULE = MOE_ITEM.register("plasma_torch_module",
            () -> new MoeMagicTypeModuleItem(new PlasmaTorch(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ELECTROMAGNETIC_ASSAULT_MODULE = MOE_ITEM.register("electromagnetic_assault_module",
            () -> new MoeMagicTypeModuleItem(new ElectromagneticAssault(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> MAGMA_LIGHTING_MODULE = MOE_ITEM.register("magma_lighting_module",
            () -> new MoeMagicTypeModuleItem(new MagmaLighting(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ST_ELMO_S_FIRE_MODULE = MOE_ITEM.register("st_elmo_s_fire_module",
            () -> new MoeMagicTypeModuleItem(new St_Elmo_s_fire(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HYDROGEN_BOND_FRACTURE_MODULE = MOE_ITEM.register("hydrogen_bond_fracture_module",
            () -> new MoeMagicTypeModuleItem(new HydrogenBondFracture(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> LIGHTING_STRIKE_MODULE = MOE_ITEM.register("lighting_strike_module",
            () -> new MoeMagicTypeModuleItem(new LightingStrike(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> BLOCK_NERVE_MODULE = MOE_ITEM.register("block_nerve_module",
            () -> new MoeMagicTypeModuleItem(new NerveBlocking(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> DISTURBING_BY_HIGH_INTENSITY_MAGNETIC_MODULE = MOE_ITEM.register("disturbing_by_high_intensity_magnetic_module",
            () -> new MoeMagicTypeModuleItem(new DisturbingByHighIntensityMagnetic(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COULOMB_DOMAIN_MODULE = MOE_ITEM.register("coulomb_domain_module",
            () -> new MoeMagicTypeModuleItem(new CoulombDomain(), new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_PRIMARY_MODULE = MOE_ITEM.register("empty_primary_module",
            () -> new MoeMagicTypeModuleItem(null, new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> EMPTY_INTERMEDIATE_MODULE = MOE_ITEM.register("empty_intermediate_module",
            () -> new MoeMagicTypeModuleItem(null, new Item.Properties().stacksTo(16)));
    public static final Supplier<Item> EMPTY_ADVANCED_MODULE = MOE_ITEM.register("empty_advanced_module",
            () -> new MoeMagicTypeModuleItem(null, new Item.Properties().stacksTo(16)));

    public static final Supplier<Item> IRON_LC = MOE_ITEM.register("iron_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.IRON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOLD_LC = MOE_ITEM.register("gold_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.GOLD, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COPPER_LC = MOE_ITEM.register("copper_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.COPPER, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SUPERCONDUCTING_LC = MOE_ITEM.register("superconducting_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.SUPERCONDUCTING, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_LC = MOE_ITEM.register("empty_lc",
            () -> new LcOscillatorModuleItem(ElectromagneticTier.EMPTY, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> IRON_POWER = MOE_ITEM.register("iron_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.IRON, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> GOLD_POWER = MOE_ITEM.register("gold_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.GOLD, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COPPER_POWER = MOE_ITEM.register("copper_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.COPPER, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SUPERCONDUCTING_POWER = MOE_ITEM.register("superconducting_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.SUPERCONDUCTING, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EMPTY_POWER = MOE_ITEM.register("empty_power",
            () -> new PowerAmplifierItem(ElectromagneticTier.EMPTY, new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> STRENGTH_ENHANCE = MOE_ITEM.register("strength_enhance",
            () -> new EnhancementModulateItem(EnhancementType.STRENGTH, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> COOLDOWN_ENHANCE = MOE_ITEM.register("cooldown_enhance",
            () -> new EnhancementModulateItem(EnhancementType.COOLDOWN, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EFFICIENCY_ENHANCE = MOE_ITEM.register("efficiency_enhance",
            () -> new EnhancementModulateItem(EnhancementType.EFFICIENCY, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ENTROPY_ENHANCE = MOE_ITEM.register("entropy_enhance",
            () -> new EnhancementModulateItem(EnhancementType.ENTROPY, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ENHANCE_MODEM_BASEBOARD = MOE_ITEM.register("enhance_modem_baseboard",
            () -> new EnhancementModulateItem(EnhancementType.EMPTY, new Item.Properties()));

    public static final Supplier<Item> COPPER_SHEET = MOE_ITEM.registerSimpleItem("copper_sheet");
    public static final Supplier<Item> IRON_SHEET = MOE_ITEM.registerSimpleItem("iron_sheet");
    public static final Supplier<Item> ENERGY_CORE = MOE_ITEM.registerSimpleItem("energy_core", new Item.Properties());
    public static final Supplier<Item> CAPACITOR = MOE_ITEM.registerSimpleItem("capacitor", new Item.Properties().stacksTo(1));
    public static final Supplier<Item> INDUCTANCE = MOE_ITEM.registerSimpleItem("inductance", new Item.Properties().stacksTo(1));
    public static final Supplier<Item> BOARD = MOE_ITEM.registerSimpleItem("board", new Item.Properties());
    public static final Supplier<Item> BJT = MOE_ITEM.registerSimpleItem("bjt", new Item.Properties().stacksTo(1));
    public static final Supplier<Item> SUPERCONDUCTING_UPDATE = MOE_ITEM.register("superconducting_update",
            () -> new SuperconductingUpdateItem(new Item.Properties()));
    public static final Supplier<Item> FE_CU_POTATO_BATTERY = MOE_ITEM.register("fe_cu_potato_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));
    public static final Supplier<Item> FE_CU_CARROT_BATTERY = MOE_ITEM.register("fe_cu_carrot_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));
    public static final Supplier<Item> FE_CU_SOLUTION_BATTERY = MOE_ITEM.register("fe_cu_solution_battery",
            () -> new BatteryItem(new Item.Properties().stacksTo(1).component(MoeDataComponentTypes.MOE_ENERGY.get(), 16384)));

    public static final Supplier<Item> ELECTROMAGNETIC_ROD = MOE_ITEM.register("electromagnetic_rod",
            () -> new MagicCastItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, setEmpty())
                    .component(MoeDataComponentTypes.MAGIC_SELECT.get(), 2)
                    .component(MoeDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.defaultData)));
    public static final Supplier<Item> ELECTROMAGNETIC_BOOK = MOE_ITEM.register("electromagnetic_book",
            () -> new MagicCastItem(new Item.Properties().stacksTo(1)
                    .component(MoeDataComponentTypes.MOE_ENERGY.get(), 0)
                    .component(DataComponents.CONTAINER, setEmpty())
                    .component(MoeDataComponentTypes.MAGIC_SELECT.get(), 2)
                    .component(MoeDataComponentTypes.ENHANCEMENT_DATA.get(), EnhancementData.defaultData)));
}
