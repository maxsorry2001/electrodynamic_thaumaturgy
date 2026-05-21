package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.ElectromagneticDissociationRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.MagicEncodeRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.MoeBuilder.MagnetoFusionRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
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

        shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())
                .pattern("aba")
                .pattern("c c")
                .pattern("aba")
                .define('a', MoeItems.MAGNO_INGOT.get())
                .define('b', Blocks.PISTON)
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE))
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

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MoeItems.COPPER_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.COPPER_INGOT), 0.5F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.COPPER_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MoeItems.COPPER_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.COPPER_INGOT), 0.5F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.COPPER_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MoeItems.IRON_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.IRON_INGOT), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.IRON_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MoeItems.IRON_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.IRON_INGOT), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.IRON_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MoeItems.GOLD_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.GOLD_INGOT), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.GOLD_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MoeItems.GOLD_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.GOLD_INGOT), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.GOLD_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MoeItems.NETHERITE_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.NETHERITE_SCRAP), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.NETHERITE_SCRAP, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MoeItems.NETHERITE_DUST.get()), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.NETHERITE_SCRAP), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.NETHERITE_SCRAP, "from_dust_blasting"));

        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ATTRACT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.IRON_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.EXCITING_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.EMERALD)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.COULOMB_DOMAIN_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.BLOCK_NERVE_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.COPPER_INGOT)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTRIC_ENERGY_RELEASE_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.HYDROGEN_BOND_FRACTURE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.DOMAIN_RECONSTRUCTION_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.COPPER_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNETIC_RECOMBINATION_CANNON_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGMA_LIGHTING_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.IRON_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MIRAGE_PURSUIT_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.COPPER_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ELECTROMAGNETIC_ASSAULT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.COPPER_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.LIGHTING_STRIKE_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.DIAMOND, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNET_RESONANCE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.REDSTONE)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.MAGNETIC_FLUX_CASCADE_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.IRON_INGOT, Items.QUARTZ)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PHOTO_CORROSIVE_NOVA_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.IRON_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PHOTOACOUSTIC_PULSE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.GOLD_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PROTECTING_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.PULSED_PLASMA_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.RAY_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.REFRACTION_MODULE.get(), MoeItems.INTERMEDIATE_CODE_MODULE.get(), Items.GOLD_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_intermediate_code_module", has(MoeItems.INTERMEDIATE_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.ST_ELMO_S_FIRE_MODULE.get(), MoeItems.ADVANCED_CODE_MODULE.get(), Items.GOLD_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(MoeItems.ADVANCED_CODE_MODULE.get()))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(MoeItems.TREE_CURRENT_MODULE.get(), MoeItems.PRIMARY_CODE_MODULE.get(), Items.GOLD_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_primary_code_module", has(MoeItems.PRIMARY_CODE_MODULE.get()))
                .save(output);

        MagnetoFusionRecipeBuilder.result(items, MoeItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.RADIANT_MAGNO_INGOT).items(MoeItems.MAGNO_INGOT).items(Items.GLOWSTONE_DUST).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.STELLAR_MAGNO_INGOT).items(MoeItems.RADIANT_MAGNO_INGOT).items(Items.ENDER_PEARL).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ELECTROMAGNETIC_ROD).items(MoeItems.MAGNO_INGOT).items(MoeItems.ENERGY_CORE).tag(ItemTags.COPPER).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ENERGY_CORE, 4).items(Items.COPPER_INGOT).items(MoeItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);

        MagnetoFusionRecipeBuilder.result(items, MoeItems.PRIMARY_LC.get()).items(MoeItems.MAGNO_INGOT.get()).items(Items.REDSTONE).items(Items.EMERALD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.INTERMEDIATE_LC.get()).items(MoeItems.PRIMARY_LC.get()).items(MoeItems.RADIANT_MAGNO_INGOT).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ADVANCED_LC.get()).items(MoeItems.INTERMEDIATE_LC.get()).items(MoeItems.STELLAR_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.SUPERCONDUCTING_LC.get()).items(MoeItems.ADVANCED_LC.get()).items(MoeItems.SUPERCONDUCTING_UPDATE).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.PRIMARY_POWER.get()).items(MoeItems.MAGNO_INGOT.get()).items(Items.REDSTONE).items(Items.DIAMOND).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.INTERMEDIATE_POWER.get()).items(MoeItems.PRIMARY_POWER.get()).items(MoeItems.RADIANT_MAGNO_INGOT).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ADVANCED_POWER.get()).items(MoeItems.INTERMEDIATE_POWER.get()).items(MoeItems.STELLAR_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.SUPERCONDUCTING_POWER.get()).items(MoeItems.ADVANCED_POWER.get()).items(MoeItems.SUPERCONDUCTING_UPDATE).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.PRIMARY_CODE_MODULE.get()).items(MoeItems.MAGNO_INGOT.get()).items(Items.REDSTONE).items(Items.GOLD_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.INTERMEDIATE_CODE_MODULE.get()).items(MoeItems.PRIMARY_CODE_MODULE.get()).items(MoeItems.RADIANT_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, MoeItems.ADVANCED_CODE_MODULE.get()).items(MoeItems.INTERMEDIATE_CODE_MODULE.get()).items(MoeItems.STELLAR_MAGNO_INGOT).items(Items.COPPER_INGOT).save(output);

        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.COPPER_DUST, 2, Items.RAW_COPPER).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.COPPER_DUST, 4, Tags.Items.ORES_COPPER).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_ore_block");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.IRON_DUST, 2, Items.RAW_IRON).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.IRON_DUST, 4, Tags.Items.ORES_IRON).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_ore_block");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.GOLD_DUST, 2, Items.RAW_GOLD).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.GOLD_DUST, 4, Tags.Items.ORES_GOLD).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).saveWithAddition(output, "from_ore_block");
        ElectromagneticDissociationRecipeBuilder.result(items, MoeItems.NETHERITE_DUST, 3, Tags.Items.ORES_NETHERITE_SCRAP).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output);
        ElectromagneticDissociationRecipeBuilder.result(items, Items.EMERALD, 3, Tags.Items.ORES_EMERALD).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.EMERALD, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Items.COAL, 3, Tags.Items.ORES_COAL).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.COAL, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Items.QUARTZ, 3, Tags.Items.ORES_QUARTZ).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.QUARTZ, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Items.REDSTONE, 8, Tags.Items.ORES_REDSTONE).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.REDSTONE, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Items.DIAMOND, 3, Tags.Items.ORES_DIAMOND).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.DIAMOND, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Items.LAPIS_LAZULI, 3, Tags.Items.ORES_LAPIS).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Items.LAPIS_LAZULI, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Blocks.SAND, Tags.Items.GRAVELS).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Blocks.SAND.asItem(), "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.result(items, Blocks.GRAVEL, Tags.Items.COBBLESTONES).unlockedBy("has_dissociation", has(MoeBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE_BLOCK.get())).save(output, getVanillaItemWithAddition(Blocks.GRAVEL.asItem(), "from_ore_dissociation"));
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

    protected static ResourceKey<Recipe<?>> getVanillaItemWithAddition(Item item, String addition){
        String s = item.toString();
        int index = s.indexOf(":");
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, item.toString().substring(index + 1) + "_" + addition));
    }
}
