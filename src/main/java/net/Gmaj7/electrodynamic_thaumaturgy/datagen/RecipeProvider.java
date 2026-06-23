package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder.ElectromagneticDissociationRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder.ElectromagneticInfusionRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder.MagicEncodeRecipeBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.datagen.builder.MagnetoFusionRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
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
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {

        // 电磁装配台
        shaped(RecipeCategory.MISC, EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE))
                .save(output);

        // 法术编码台
        shaped(RecipeCategory.MISC, EtBlocks.MAGIC_ENCODE_TABLE)
                .pattern(" c ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Blocks.GLASS_PANE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 电磁调制台
        shaped(RecipeCategory.MISC, EtBlocks.ELECTROMAGNETIC_MODEM_TABLE)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Blocks.SMITHING_TABLE)
                .unlockedBy("has_smithing_table", has(Blocks.SMITHING_TABLE))
                .save(output);

        // 能量
        shaped(RecipeCategory.MISC, EtBlocks.ENERGY_BLOCK)
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', Items.IRON_INGOT)
                .define('b', EtItems.MAGNO_INGOT)
                .define('c', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 能量发射天线
        shaped(RecipeCategory.MISC, EtBlocks.ENERGY_TRANSMISSION_ANTENNA, 2)
                .pattern("a")
                .pattern("a")
                .pattern("b")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 光伏发电机
        shaped(RecipeCategory.MISC, EtBlocks.PHOTOVOLTAIC_GENERATOR)
                .pattern("aaa")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', Blocks.GLASS)
                .define('b', EtItems.MAGNO_INGOT)
                .define('c', Items.IRON_INGOT)
                .define('d', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 温差发电机
        shaped(RecipeCategory.MISC, EtBlocks.TEMPERATURE_GENERATOR)
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Items.IRON_INGOT)
                .define('c', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 生物质发电机
        shaped(RecipeCategory.MISC, EtBlocks.BIOMASS_GENERATOR)
                .pattern("aaa")
                .pattern("bcb")
                .pattern("aaa")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Tags.Items.FOODS)   // 原版食物标签
                .define('c', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 电磁驱动器
        shaped(RecipeCategory.MISC, EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE)
                .pattern("ada")
                .pattern("aca")
                .pattern("aea")
                .define('a', EtItems.RADIANT_MAGNO_INGOT)
                .define('c', EtItems.ENERGY_CORE)
                .define('d', EtItems.PRIMARY_LC)     // 使用初级振荡器
                .define('e', EtItems.PRIMARY_POWER)  // 使用初级功放
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 生物繁殖槽
        shaped(RecipeCategory.MISC, EtBlocks.BIO_REPLICATION_VAT_MACHINE)
                .pattern("aba")
                .pattern("bcb")
                .pattern("ada")
                .define('a', EtItems.RADIANT_MAGNO_INGOT)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', EtItems.ENERGY_CORE)
                .define('d', EtItems.MAGNO_INGOT)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 热力发电机
        shaped(RecipeCategory.MISC, EtBlocks.THERMAL_GENERATOR)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" c ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Blocks.FURNACE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 电磁挖掘机
        shaped(RecipeCategory.MISC, EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE)
                .pattern("aca")
                .pattern("cbc")
                .pattern("aaa")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        shaped(RecipeCategory.MISC, EtBlocks.MAGNETO_FUSION_MACHINE.asItem())
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Blocks.LODESTONE)
                .unlockedBy("has_magno_ingot", has(EtItems.MAGNO_INGOT))
                .save(output);

        shaped(RecipeCategory.MISC, EtBlocks.MAGNETO_CORE)
                .pattern("aba")
                .pattern("aca")
                .pattern("aba")
                .define('a', EtItems.STELLAR_MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Items.NETHER_STAR)
                .unlockedBy("has_gold", has(Items.NETHER_STAR))
                .save(output);

        // 原子重构机
        shaped(RecipeCategory.MISC, EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', EtItems.RADIANT_MAGNO_INGOT)
                .define('b', Items.ENDER_EYE)
                .define('c', EtItems.ENERGY_CORE)
                .unlockedBy("has_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // 基因记录器
        shaped(RecipeCategory.MISC, EtItems.GENETIC_RECORDER)
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" c ")
                .define('a', Blocks.GLASS_PANE)
                .define('b', Items.ROTTEN_FLESH)
                .define('c', EtItems.RADIANT_MAGNO_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        shaped(RecipeCategory.MISC, EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)
                .pattern("aba")
                .pattern("c c")
                .pattern("aba")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Blocks.PISTON)
                .define('c', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        // ========== 增强模块基板 ==========
        shaped(RecipeCategory.MISC, EtItems.ENHANCE_MODEM_BASEBOARD, 4)
                .pattern("aba")
                .pattern("ccc")
                .define('a', Items.COPPER_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);
        
        shaped(RecipeCategory.MISC, EtItems.MAGNO_WRENCH)
                .pattern(" a ")
                .pattern(" b ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Items.STICK)
                .unlockedBy("has_magno_ingot", has(EtItems.MAGNO_INGOT))
                .save(output);

        shaped(RecipeCategory.MISC, EtBlocks.ITEM_PIPE, 16)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', Blocks.BARREL)
                .unlockedBy("has_magno_ingot", has(EtItems.MAGNO_INGOT))
                .save(output);

        shaped(RecipeCategory.MISC, EtBlocks.ENERGY_PIPE, 16)
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', EtItems.MAGNO_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .unlockedBy("has_magno_ingot", has(EtItems.MAGNO_INGOT))
                .save(output);

        // 各种增强插件（使用基板 + 特定原版物品）
        shapeless(RecipeCategory.MISC, EtItems.STRENGTH_ENHANCE)
                .requires(Items.GLOWSTONE_DUST)
                .requires(EtItems.ENHANCE_MODEM_BASEBOARD)
                .unlockedBy("has_enhance_baseboard", has(EtItems.ENHANCE_MODEM_BASEBOARD))
                .save(output);

        shapeless(RecipeCategory.MISC, EtItems.COOLDOWN_ENHANCE)
                .requires(Items.ENDER_PEARL)
                .requires(EtItems.ENHANCE_MODEM_BASEBOARD)
                .unlockedBy("has_enhance_baseboard", has(EtItems.ENHANCE_MODEM_BASEBOARD))
                .save(output);

        shapeless(RecipeCategory.MISC, EtItems.EFFICIENCY_ENHANCE)
                .requires(Items.AMETHYST_SHARD)
                .requires(EtItems.ENHANCE_MODEM_BASEBOARD)
                .unlockedBy("has_enhance_baseboard", has(EtItems.ENHANCE_MODEM_BASEBOARD))
                .save(output);

        // ========== 各类电池 ==========
        shapeless(RecipeCategory.MISC, EtItems.CARROT_BATTERY)
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.CARROT)
                .unlockedBy("has_carrot", has(Items.CARROT))
                .save(output);

        shapeless(RecipeCategory.MISC, EtItems.POTATO_BATTERY)
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.POTATO)
                .unlockedBy("has_potato", has(Items.POTATO))
                .save(output);

        shapeless(RecipeCategory.MISC, EtItems.SOLUTION_BATTERY)
                .requires(Items.IRON_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_potion", has(Items.GLASS_BOTTLE))
                .save(output);

        // 移动电源
        shaped(RecipeCategory.MISC, EtItems.POWER_BANK)
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', Items.IRON_INGOT)
                .define('b', EtItems.ENERGY_CORE)
                .unlockedBy("has_energy_core", has(EtItems.ENERGY_CORE))
                .save(output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EtItems.COPPER_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.COPPER_INGOT), 0.5F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.COPPER_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(EtItems.COPPER_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.COPPER_INGOT), 0.5F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.COPPER_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EtItems.IRON_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.IRON_INGOT), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.IRON_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(EtItems.IRON_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.IRON_INGOT), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.IRON_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EtItems.GOLD_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.GOLD_INGOT), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.GOLD_INGOT, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(EtItems.GOLD_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.GOLD_INGOT), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.GOLD_INGOT, "from_dust_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(EtItems.NETHERITE_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.NETHERITE_SCRAP), 0.7F, 100).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output, getVanillaItemWithAddition(Items.NETHERITE_SCRAP, "from_dust_smithing"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(EtItems.NETHERITE_DUST), RecipeCategory.MISC, CookingBookCategory.MISC, new ItemStackTemplate(Items.NETHERITE_SCRAP), 0.7F, 50).unlockedBy("has_blast_furnace", has(Blocks.BLAST_FURNACE)).save(output, getVanillaItemWithAddition(Items.NETHERITE_SCRAP, "from_dust_blasting"));

        MagicEncodeRecipeBuilder.magicEncode(EtItems.ATTRACT_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.IRON_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.EXCITING_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.COPPER_INGOT, Items.EMERALD)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.ELECTRIC_FIELD_DOMAIN_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.COPPER_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.COULOMB_DOMAIN_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.COPPER_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.BLOCK_NERVE_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.COPPER_INGOT, Items.COPPER_INGOT)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.ELECTRIC_ENERGY_RELEASE_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.COPPER_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.HYDROGEN_BOND_FRACTURE_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.IRON_INGOT, Items.IRON_INGOT)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.DOMAIN_RECONSTRUCTION_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.COPPER_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.MAGNETIC_RECOMBINATION_CANNON_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.IRON_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.MAGMA_LIGHTING_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.IRON_INGOT, Items.DIAMOND)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.MIRAGE_PURSUIT_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.IRON_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.FREQUENCY_DIVISION_ARROW_RAIN_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.COPPER_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.ELECTROMAGNETIC_ASSAULT_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.COPPER_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.LIGHTING_STRIKE_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.DIAMOND, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.MAGNET_RESONANCE_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.IRON_INGOT, Items.REDSTONE)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.MAGNETIC_FLUX_CASCADE_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.IRON_INGOT, Items.QUARTZ)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.PHOTO_CORROSIVE_NOVA_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.IRON_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.PHOTOACOUSTIC_PULSE_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.GOLD_INGOT, Items.GOLD_INGOT)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.PROTECTING_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.GOLD_INGOT, Items.DIAMOND)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.PULSED_PLASMA_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.GOLD_INGOT, Items.REDSTONE)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.RAY_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.GOLD_INGOT, Items.QUARTZ)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.REFRACTION_MODULE, EtItems.INTERMEDIATE_CODE_MODULE, Items.GOLD_INGOT, Items.ENDER_PEARL)
                .unlockedBy("has_intermediate_code_module", has(EtItems.INTERMEDIATE_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.ST_ELMO_S_FIRE_MODULE, EtItems.ADVANCED_CODE_MODULE, Items.GOLD_INGOT, Items.EMERALD)
                .unlockedBy("has_advanced_code_module", has(EtItems.ADVANCED_CODE_MODULE))
                .save(output);
        MagicEncodeRecipeBuilder.magicEncode(EtItems.TREE_CURRENT_MODULE, EtItems.PRIMARY_CODE_MODULE, Items.GOLD_INGOT, Items.LAPIS_LAZULI)
                .unlockedBy("has_primary_code_module", has(EtItems.PRIMARY_CODE_MODULE))
                .save(output);

        MagnetoFusionRecipeBuilder.result(items, EtItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.RADIANT_MAGNO_INGOT).items(EtItems.MAGNO_INGOT).items(EtItems.POLAR_CRYSTAL).items(EtItems.RESONANT_CRYSTAL).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.STELLAR_MAGNO_INGOT).items(EtItems.RADIANT_MAGNO_INGOT).items(EtItems.MONOPOLE_N).items(EtItems.MONOPOLE_S).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.ELECTROMAGNETIC_ROD).items(EtItems.MAGNO_INGOT).items(EtItems.ENERGY_CORE).tag(ItemTags.COPPER).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.ENERGY_CORE, 4).items(Items.COPPER_INGOT).items(EtItems.MAGNO_INGOT).items(Items.IRON_INGOT).save(output);

        MagnetoFusionRecipeBuilder.result(items, EtItems.PRIMARY_LC).items(EtItems.MAGNO_INGOT).items(Items.REDSTONE).items(Items.EMERALD).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.INTERMEDIATE_LC).items(EtItems.PRIMARY_LC).items(EtItems.RADIANT_MAGNO_INGOT).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.ADVANCED_LC).items(EtItems.INTERMEDIATE_LC).items(EtItems.STELLAR_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.SUPERCONDUCTING_LC).items(EtItems.ADVANCED_LC).items(EtItems.SUPERCONDUCTING_UPDATE).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.PRIMARY_POWER).items(EtItems.MAGNO_INGOT).items(Items.REDSTONE).items(Items.DIAMOND).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.INTERMEDIATE_POWER).items(EtItems.PRIMARY_POWER).items(EtItems.RADIANT_MAGNO_INGOT).items(Items.QUARTZ).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.ADVANCED_POWER).items(EtItems.INTERMEDIATE_POWER).items(EtItems.STELLAR_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.SUPERCONDUCTING_POWER).items(EtItems.ADVANCED_POWER).items(EtItems.SUPERCONDUCTING_UPDATE).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.PRIMARY_CODE_MODULE).items(EtItems.MAGNO_INGOT).items(Items.REDSTONE).items(Items.GOLD_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.INTERMEDIATE_CODE_MODULE).items(EtItems.PRIMARY_CODE_MODULE).items(EtItems.RADIANT_MAGNO_INGOT).items(Items.AMETHYST_SHARD).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.ADVANCED_CODE_MODULE).items(EtItems.INTERMEDIATE_CODE_MODULE).items(EtItems.STELLAR_MAGNO_INGOT).items(Items.COPPER_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.POLAR_CRYSTAL).items(Items.EMERALD).items(EtItems.MAGNO_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.RESONANT_CRYSTAL).items(Items.QUARTZ).items(EtItems.MAGNO_INGOT).save(output);
        MagnetoFusionRecipeBuilder.result(items, EtItems.FILTER_SETTING).items(Items.REDSTONE).items(EtItems.MAGNO_INGOT).items(Items.REDSTONE).save(output);

        ElectromagneticDissociationRecipeBuilder.creat(items, Items.RAW_COPPER).result(EtItems.COPPER_DUST, 2).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_COPPER).result(EtItems.COPPER_DUST, 4).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_ore_b_lock");
        ElectromagneticDissociationRecipeBuilder.creat(items, Items.RAW_IRON).result(EtItems.IRON_DUST, 2).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_IRON).result(EtItems.IRON_DUST, 4).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_ore_b_lock");
        ElectromagneticDissociationRecipeBuilder.creat(items, Items.RAW_GOLD).result(EtItems.GOLD_DUST, 2).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_raw");
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_GOLD).result(EtItems.GOLD_DUST, 4).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithAddition(output, "from_ore_b_lock");
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_NETHERITE_SCRAP).result(EtItems.NETHERITE_DUST, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output);
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_EMERALD).result(Items.EMERALD, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.EMERALD, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_COAL).result(Items.COAL, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.COAL, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_QUARTZ).result(Items.QUARTZ, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.QUARTZ, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_REDSTONE).result(Items.REDSTONE, 8).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.REDSTONE, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_DIAMOND).result(Items.DIAMOND, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.DIAMOND, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.ORES_LAPIS).result(Items.LAPIS_LAZULI, 3).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Items.LAPIS_LAZULI, "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.GRAVELS).result(Blocks.SAND.asItem()).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Blocks.SAND.asItem(), "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Tags.Items.COBBLESTONES).result(Blocks.GRAVEL.asItem()).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).save(output, getVanillaItemWithAddition(Blocks.GRAVEL.asItem(), "from_ore_dissociation"));
        ElectromagneticDissociationRecipeBuilder.creat(items, Blocks.LODESTONE, true).result(EtItems.MONOPOLE_N).result(EtItems.MONOPOLE_S).unlockedBy("has_dissociation", has(EtBlocks.ELECTROMAGNETIC_DISSOCIATION_MACHINE)).saveWithName(output, "monopole");

        ElectromagneticInfusionRecipeBuilder.creat(items, EtItems.GLOWING_ESSENCE, 2, EtItems.POLAR_CRYSTAL, Fluids.WATER).unlockedBy("has_crystal", has(EtItems.POLAR_CRYSTAL)).save(output);
    }

    protected static class Runner extends net.minecraft.data.recipes.RecipeProvider.Runner{

        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new RecipeProvider(provider, recipeOutput);
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
