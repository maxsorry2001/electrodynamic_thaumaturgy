package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.ElectromagneticDissociationRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.MagicEncodeRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.MagnetoFusionRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class MoeRecipeProvider extends RecipeProvider {
    public MoeRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        // 电磁装配台
        shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE))
                .save(output);

        // 法术编码台
        shaped(RecipeCategory.MISC, MoeBlocks.MAGIC_ENCODE_TABLE.get())
                .pattern(" c ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.GLASS_PANE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 电磁调制台
        shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', Blocks.SMITHING_TABLE)
                .unlockedBy("has_smithing_table", has(Blocks.SMITHING_TABLE))
                .save(output);

        // 能量
        shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_BLOCK.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.MAGNO_INGOT.get())
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 能量发射天线
        shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get(), 2)
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 光伏发电机
        shaped(RecipeCategory.MISC, MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', Blocks.GLASS)
                .define('b', MoeItems.MAGNO_INGOT)
                .define('c', Items.IRON_INGOT)
                .define('d', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 温差发电机
        shaped(RecipeCategory.MISC, MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', Items.IRON_INGOT)
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 生物质发电机
        shaped(RecipeCategory.MISC, MoeBlocks.BIOMASS_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', Tags.Items.FOODS)   // 原版食物标签
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 电磁驱动器
        shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get())
                .pattern("ada")
                .pattern("aca")
                .pattern("aea")
                .define('a', MoeItems.RADIANT_MAGNO_INGOT)
                .define('c', MoeItems.ENERGY_CORE.get())
                .define('d', MoeItems.PRIMARY_LC.get())     // 使用初级振荡器
                .define('e', MoeItems.PRIMARY_POWER.get())  // 使用初级功放
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 生物繁殖槽
        shaped(RecipeCategory.MISC, MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("ada")
                .define('a', MoeItems.RADIANT_MAGNO_INGOT)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', MoeItems.ENERGY_CORE.get())
                .define('d', MoeItems.MAGNO_INGOT.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 热力发电机
        shaped(RecipeCategory.MISC, MoeBlocks.THERMAL_GENERATOR_BLOCK.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" c ")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.FURNACE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 电磁挖掘机
        shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get())
                .pattern("aca")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        shaped(RecipeCategory.MISC, MoeBlocks.MAGNETO_FUSION_MACHINE_BLOCK.asItem())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', MoeItems.MAGNO_INGOT)
                .define('b', MoeItems.ENERGY_CORE)
                .define('c', Blocks.LODESTONE)
                .unlockedBy("has_magno_ingot", has(MoeItems.MAGNO_INGOT.get()))
                .save(output);

        // 谐波核心
        shaped(RecipeCategory.MISC, MoeBlocks.MAGNETO_CORE_BLOCK.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', MoeItems.STELLAR_MAGNO_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.NETHER_STAR)
                .unlockedBy("has_gold", has(Items.NETHER_STAR))
                .save(output);

        // 原子重构机
        shaped(RecipeCategory.MISC, MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', MoeItems.RADIANT_MAGNO_INGOT)
                .define('b', Items.ENDER_EYE)
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 基因记录器
        shaped(RecipeCategory.MISC, MoeItems.GENETIC_RECORDER.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" c ")
                .define('a', Blocks.GLASS_PANE)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', MoeItems.RADIANT_MAGNO_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);


        // ========== 振荡器 (LC) 升级 ==========
// 初级振荡器（不变）
        shaped(RecipeCategory.MISC, MoeItems.PRIMARY_LC.get())
                .pattern(" a ")
                .pattern("db ")
                .pattern(" c ")
                .define('a', Items.REDSTONE)
                .define('b', Items.IRON_INGOT)
                .define('c', Items.REDSTONE)
                .define('d', MoeItems.MAGNO_INGOT.get())
                .unlockedBy("has_magno_ingot", has(MoeItems.MAGNO_INGOT.get()))
                .save(output);

// 中级振荡器：初级振荡器 + 紫水晶 + 金锭
        shaped(RecipeCategory.MISC, MoeItems.INTERMEDIATE_LC.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.AMETHYST_SHARD)
                .define('b', Items.GOLD_INGOT)
                .define('c', MoeItems.PRIMARY_LC.get())
                .unlockedBy("has_primary_lc", has(MoeItems.PRIMARY_LC.get()))
                .save(output);

// 高级振荡器：中级振荡器 + 幽匿块 + 铜锭
        shaped(RecipeCategory.MISC, MoeItems.ADVANCED_LC.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Blocks.SCULK.asItem())  // 幽匿块
                .define('b', Items.COPPER_INGOT)
                .define('c', MoeItems.INTERMEDIATE_LC.get())
                .unlockedBy("has_intermediate_lc", has(MoeItems.INTERMEDIATE_LC.get()))
                .save(output);

// 超导振荡器：高级振荡器 + 超导更新组件 + 下界合金锭
        shaped(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_LC.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.NETHERITE_INGOT)
                .define('b', MoeItems.SUPERCONDUCTING_UPDATE.get())
                .define('c', MoeItems.ADVANCED_LC.get())
                .unlockedBy("has_advanced_lc", has(MoeItems.ADVANCED_LC.get()))
                .save(output);

// ========== 功放 (Power) 升级 ==========
// 初级功放（不变）
        shaped(RecipeCategory.MISC, MoeItems.PRIMARY_POWER.get())
                .pattern(" a ")
                .pattern("db ")
                .pattern("   ")
                .define('a', Items.REDSTONE)
                .define('b', MoeItems.MAGNO_INGOT.get())
                .define('d', Items.IRON_INGOT)
                .unlockedBy("has_board", has(MoeItems.MAGNO_INGOT.get()))
                .save(output);

// 中级功放：初级功放 + 紫水晶 + 金锭
        shaped(RecipeCategory.MISC, MoeItems.INTERMEDIATE_POWER.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.AMETHYST_SHARD)
                .define('b', Items.GOLD_INGOT)
                .define('c', MoeItems.PRIMARY_POWER.get())
                .unlockedBy("has_primary_power", has(MoeItems.PRIMARY_POWER.get()))
                .save(output);

// 高级功放：中级功放 + 幽匿块 + 铜锭
        shaped(RecipeCategory.MISC, MoeItems.ADVANCED_POWER.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Blocks.SCULK.asItem())
                .define('b', Items.COPPER_INGOT)
                .define('c', MoeItems.INTERMEDIATE_POWER.get())
                .unlockedBy("has_intermediate_power", has(MoeItems.INTERMEDIATE_POWER.get()))
                .save(output);

// 超导功放：高级功放 + 超导更新组件 + 末影水晶
        shaped(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_POWER.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.END_CRYSTAL)
                .define('b', MoeItems.SUPERCONDUCTING_UPDATE.get())
                .define('c', MoeItems.ADVANCED_POWER.get())
                .unlockedBy("has_advanced_power", has(MoeItems.ADVANCED_POWER.get()))
                .save(output);

        // ========== 空模块（初级/中级/高级） ==========
        // 初级空模块：铜板（假设铜板为铜锭，此处用铜锭）
        shaped(RecipeCategory.MISC, MoeItems.PRIMARY_CODE_MODULE.get())
                .pattern("ccc")
                .define('c', Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(output);

        // 中级空模块：初级空模块 + 金锭 + 石英
        shapeless(RecipeCategory.MISC, MoeItems.INTERMEDIATE_CODE_MODULE.get())
                .requires(MoeItems.PRIMARY_CODE_MODULE.get())
                .requires(Items.GOLD_INGOT)
                .requires(Items.QUARTZ)
                .unlockedBy("has_primary_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

        // 高级空模块：中级空模块 + 铜锭 + 紫颂果
        shapeless(RecipeCategory.MISC, MoeItems.ADVANCED_CODE_MODULE.get())
                .requires(MoeItems.INTERMEDIATE_CODE_MODULE.get())
                .requires(Items.COPPER_INGOT)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .unlockedBy("has_intermediate_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

        // ========== 增强模块基板 ==========
        shaped(RecipeCategory.MISC, MoeItems.ENHANCE_MODEM_BASEBOARD.get(), 4)
                .pattern("aba")
                .pattern("ccc")
                .define('a', Items.COPPER_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // 各种增强插件（使用基板 + 特定原版物品）
        shapeless(RecipeCategory.MISC, MoeItems.STRENGTH_ENHANCE.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.COOLDOWN_ENHANCE.get())
                .requires(Items.ENDER_PEARL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.EFFICIENCY_ENHANCE.get())
                .requires(Items.AMETHYST_SHARD)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.ENTROPY_ENHANCE.get())
                .requires(Items.FLINT_AND_STEEL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.LIFE_EXTRACTION_ENHANCE.get())
                .requires(Items.GOLDEN_CARROT)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(output);

        // ========== 各类电池 ==========
        shapeless(RecipeCategory.MISC, MoeItems.CARROT_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.CARROT)
                .unlockedBy("has_carrot", has(Items.CARROT))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.POTATO_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.POTATO)
                .unlockedBy("has_potato", has(Items.POTATO))
                .save(output);

        shapeless(RecipeCategory.MISC, MoeItems.SOLUTION_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_potion", has(Items.GLASS_BOTTLE))
                .save(output);

        // 移动电源
        shaped(RecipeCategory.MISC, MoeItems.POWER_BANK.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(output);

        // attract_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ATTRACT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.IRON_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// exciting_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.EXCITING_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.EMERALD)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// electric_field_domain_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// coulomb_domain_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.COULOMB_DOMAIN_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// block_nerve_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.BLOCK_NERVE_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.COPPER_INGOT)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// electric_energy_release_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// hydrogen_bond_fracture_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// domain_reconstruction_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// magnetic_recombination_cannon_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// magma_lighting_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGMA_LIGHTING_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.IRON_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// mirage_pursuit_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MIRAGE_PURSUIT_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// frequency_division_arrow_rain_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.COPPER_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// electromagnetic_assault_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// lighting_strike_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.LIGHTING_STRIKE_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.DIAMOND, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// magnet_resonance_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNET_RESONANCE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.REDSTONE)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// magnetic_flux_cascade_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.IRON_INGOT, Items.QUARTZ)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// photo_corrosive_nova_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PHOTO_CORROSIVE_NOVA_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// photoacoustic_pulse_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PHOTOACOUSTIC_PULSE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.GOLD_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// protecting_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PROTECTING_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// pulsed_plasma_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PULSED_PLASMA_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// ray_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.RAY_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

// refraction_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.REFRACTION_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.GOLD_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);

// st_elmo_s_fire_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ST_ELMO_S_FIRE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.GOLD_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);

// tree_current_module
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.TREE_CURRENT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

        MagnetoFusionRecipeBuilder.result(items, MoeItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.RADIANT_MAGNO_INGOT).items(MoeItems.MAGNO_INGOT).items(Items.GLOWSTONE_DUST).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.STELLAR_MAGNO_INGOT).items(MoeItems.RADIANT_MAGNO_INGOT).items(Items.ENDER_PEARL).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ELECTROMAGNETIC_ROD).items(MoeItems.MAGNO_INGOT).items(MoeItems.ENERGY_CORE).tag(ItemTags.COPPER).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ENERGY_CORE, 4).items(Items.COPPER_INGOT).items(MoeItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);

        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.IRON_DUST, 2, Items.RAW_IRON).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output);
    }

    protected static class Runner extends RecipeProvider.Runner{

        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new MoeRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Moe Recipes";
        }
    }
}
