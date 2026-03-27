package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class MoeRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public MoeRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // ========== 基础材料 ==========
        // 感磁锭已在别处定义，此处不重复
        // 能量核心：8个感磁锭围成环
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENERGY_CORE.get(), 8)
                .pattern(" a ")
                .pattern("a a")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .unlockedBy("has_magno_ingot", has(MoeItems.MAGNO_INGOT.get()))
                .save(recipeOutput);

        // ========== 方块配方（仅使用原版+感磁锭+能量核心） ==========
        // 电磁装配台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE))
                .save(recipeOutput);

        // 魔法光刻台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get())
                .pattern(" c ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.GLASS_PANE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 电磁调制台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', Blocks.SMITHING_TABLE)
                .unlockedBy("has_smithing_table", has(Blocks.SMITHING_TABLE))
                .save(recipeOutput);

        // 能量块
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_BLOCK.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.MAGNO_INGOT.get())
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 能量发射天线（一次合成2个）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get(), 2)
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', Items.COPPER_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 光伏发电机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', Blocks.GLASS)
                .define('b', Items.COPPER_INGOT)
                .define('c', Items.IRON_INGOT)
                .define('d', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 温差发电机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.COPPER_INGOT)
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 生物质发电机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.BIOMASS_GENERATOR_BLOCK.get())
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', Items.IRON_INGOT)
                .define('b', Tags.Items.FOODS)   // 原版食物标签
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 电磁驱动器
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get())
                .pattern("ada")
                .pattern("bcb")
                .pattern("aea")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.IRON_INGOT)
                .define('c', MoeItems.ENERGY_CORE.get())
                .define('d', MoeItems.PRIMARY_LC.get())     // 使用初级振荡器
                .define('e', MoeItems.PRIMARY_POWER.get())  // 使用初级功放
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 生物繁殖槽
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("ada")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', MoeItems.ENERGY_CORE.get())
                .define('d', Items.IRON_INGOT)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 热力发电机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.THERMAL_GENERATOR_BLOCK.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" c ")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.FURNACE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 电磁提取机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK.get())
                .pattern("aca")
                .pattern("cbc")
                .pattern("ada")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.REDSTONE)
                .define('d', Items.IRON_INGOT)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 氮气采集器（原配方用了SI，改为感磁锭）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.NITROGEN_HARVESTER_BLOCK.get())
                .pattern("aca")
                .pattern("cbc")
                .pattern("ada")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.BONE_BLOCK)
                .define('d', Items.IRON_INGOT)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 谐波核心
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.HARMONIC_CORE_BLOCK.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', Items.EMERALD)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.NETHER_STAR)
                .unlockedBy("has_gold", has(MoeItems.INTERMEDIATE_LC.get())) // 解锁条件借用中级振荡器
                .save(recipeOutput);

        // 原子重构机
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK.get())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', Items.IRON_INGOT)
                .define('b', Items.ENDER_EYE)
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // ========== 物品配方 ==========
        // 电磁棒
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ELECTROMAGNETIC_ROD.get())
                .pattern("  a")
                .pattern(" b ")
                .pattern("c  ")
                .define('a', MoeItems.ENERGY_CORE.get())
                .define('b', Items.IRON_INGOT)
                .define('c', Tags.Items.STONES)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        // 基因记录器
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.GENETIC_RECORDER.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" c ")
                .define('a', Blocks.GLASS_PANE)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        // ========== 升阶系统：振荡器 (LC) 和 功放 (Power) ==========
// 初级振荡器：感磁锭 + 铁锭 + 红石（不变）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.PRIMARY_LC.get())
                .pattern(" a ")
                .pattern("db ")
                .pattern(" c ")
                .define('a', Items.REDSTONE)
                .define('b', Items.IRON_INGOT)
                .define('c', Items.REDSTONE)
                .define('d', MoeItems.MAGNO_INGOT.get())
                .unlockedBy("has_magno_ingot", has(MoeItems.MAGNO_INGOT.get()))
                .save(recipeOutput);

// 中级振荡器：在锻造台用初级振荡器 + 紫水晶碎片 + 金锭升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.PRIMARY_LC.get()),
                        Ingredient.of(Items.AMETHYST_SHARD),
                        Ingredient.of(Items.GOLD_INGOT),
                        RecipeCategory.MISC, MoeItems.INTERMEDIATE_LC.get())
                .unlocks("has_primary_lc", has(MoeItems.PRIMARY_LC.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "intermediate_lc_upgrade"));

// 高级振荡器：在锻造台用中级振荡器 + 幽匿块 + 铜锭升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.INTERMEDIATE_LC.get()),
                        Ingredient.of(Blocks.SCULK.asItem()),
                        Ingredient.of(Items.COPPER_INGOT),
                        RecipeCategory.MISC, MoeItems.ADVANCED_LC.get())
                .unlocks("has_intermediate_lc", has(MoeItems.INTERMEDIATE_LC.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "advanced_lc_upgrade"));

// 超导振荡器：在锻造台用高级振荡器 + 下界合金锭 + 末影水晶升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.ADVANCED_LC.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.END_CRYSTAL),
                        RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_LC.get())
                .unlocks("has_advanced_lc", has(MoeItems.ADVANCED_LC.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "superconducting_lc_upgrade"));

// 功放系列同理（初级、中级、高级、超导）
// 初级功放：感磁锭 + 铁锭 + 红石
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.PRIMARY_POWER.get())
                .pattern(" a ")
                .pattern("db ")
                .pattern("   ")
                .define('a', Items.REDSTONE)
                .define('b', MoeItems.MAGNO_INGOT.get())
                .define('d', Items.IRON_INGOT)
                .unlockedBy("has_board", has(MoeItems.MAGNO_INGOT.get()))
                .save(recipeOutput);

// 中级功放：锻造台升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.PRIMARY_POWER.get()),
                        Ingredient.of(Items.AMETHYST_SHARD),
                        Ingredient.of(Items.GOLD_INGOT),
                        RecipeCategory.MISC, MoeItems.INTERMEDIATE_POWER.get())
                .unlocks("has_primary_power", has(MoeItems.PRIMARY_POWER.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "intermediate_power_upgrade"));

// 高级功放：锻造台升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.INTERMEDIATE_POWER.get()),
                        Ingredient.of(Blocks.SCULK.asItem()),
                        Ingredient.of(Items.COPPER_INGOT),
                        RecipeCategory.MISC, MoeItems.ADVANCED_POWER.get())
                .unlocks("has_intermediate_power", has(MoeItems.INTERMEDIATE_POWER.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "advanced_power_upgrade"));

// 超导功放：锻造台升级
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MoeItems.ADVANCED_POWER.get()),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        Ingredient.of(Items.END_CRYSTAL),
                        RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_POWER.get())
                .unlocks("has_advanced_power", has(MoeItems.ADVANCED_POWER.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "superconducting_power_upgrade"));

        // ========== 空模块（初级/中级/高级） ==========
        // 初级空模块：铜板（假设铜板为铜锭，此处用铜锭）
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.EMPTY_PRIMARY_MODULE.get())
                .pattern("ccc")
                .define('c', Items.COPPER_INGOT)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT))
                .save(recipeOutput);

        // 中级空模块：初级空模块 + 金锭 + 石英
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EMPTY_INTERMEDIATE_MODULE.get())
                .requires(MoeItems.EMPTY_PRIMARY_MODULE.get())
                .requires(Items.GOLD_INGOT)
                .requires(Items.QUARTZ)
                .unlockedBy("has_primary_module", has(MoeItems.EMPTY_PRIMARY_MODULE.get()))
                .save(recipeOutput);

        // 高级空模块：中级空模块 + 铜锭 + 紫颂果
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EMPTY_ADVANCED_MODULE.get())
                .requires(MoeItems.EMPTY_INTERMEDIATE_MODULE.get())
                .requires(Items.COPPER_INGOT)
                .requires(Items.POPPED_CHORUS_FRUIT)
                .unlockedBy("has_intermediate_module", has(MoeItems.EMPTY_INTERMEDIATE_MODULE.get()))
                .save(recipeOutput);

        // ========== 增强模块基板 ==========
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENHANCE_MODEM_BASEBOARD.get(), 4)
                .pattern("aba")
                .pattern("ccc")
                .define('a', Items.COPPER_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);

        // 各种增强插件（使用基板 + 特定原版物品）
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.STRENGTH_ENHANCE.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.COOLDOWN_ENHANCE.get())
                .requires(Items.ENDER_PEARL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EFFICIENCY_ENHANCE.get())
                .requires(Items.AMETHYST_SHARD)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.ENTROPY_ENHANCE.get())
                .requires(Items.FLINT_AND_STEEL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.LIFE_EXTRACTION_ENHANCE.get())
                .requires(Items.GOLDEN_CARROT)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);

        // ========== 各类电池 ==========
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.CARROT_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.CARROT)
                .unlockedBy("has_carrot", has(Items.CARROT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.POTATO_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.POTATO)
                .unlockedBy("has_potato", has(Items.POTATO))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.SOLUTION_BATTERY.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_potion", has(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        // 移动电源
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.POWER_BANK.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get()))
                .save(recipeOutput);
    }
}
