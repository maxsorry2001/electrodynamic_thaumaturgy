package net.Gmaj7.electrofynamic_thaumatury.datagen;

import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlocks;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_crafting_table", has(Blocks.CRAFTING_TABLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.MAGIC_LITHOGRAPHY_TABLE.get())
                .pattern(" c ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Blocks.GLASS_PANE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .define('a', MoeItems.COPPER_SHEET.get())
                .define('b', Blocks.SMITHING_TABLE)
                .unlockedBy("has_smithing_table", has(Blocks.SMITHING_TABLE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_BLOCK.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aba").define('a', MoeItems.COPPER_SHEET.get())
                .define('b', MoeItems.CAPACITOR.get())
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get(), 2)
                .pattern("a")
                .pattern("a")
                .pattern("b").define('a', Items.COPPER_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK.get())
                .pattern("aaa")
                .pattern("bdb")
                .pattern("ccc").define('a', Items.GLASS)
                .define('b', Items.COPPER_INGOT)
                .define('c', Items.IRON_INGOT)
                .define('d', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK.get())
                .pattern("aaa")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', Items.LAPIS_LAZULI)
                .define('b', Items.COPPER_INGOT)
                .define('c', Items.AMETHYST_SHARD)
                .define('d', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeBlocks.THERMAL_ENERGY_MAKER_BLOCK.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" c ")
                .define('a', Items.IRON_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.FURNACE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.IRON_SHEET.get(), 16)
                .pattern("aa ")
                .pattern("   ")
                .pattern("   ")
                .define('a', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.CAPACITOR.get())
                .pattern("   ")
                .pattern("a a")
                .pattern("b b")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', MoeItems.COPPER_SHEET.get())
                .unlockedBy("has_iron_sheet", has(MoeItems.IRON_SHEET.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.INDUCTANCE.get())
                .pattern("b  ")
                .pattern(" b ")
                .pattern("b  ")
                .define('b', MoeItems.COPPER_SHEET.get())
                .unlockedBy("has_copper_sheet", has(MoeItems.COPPER_SHEET.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.BJT.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" c ")
                .define('a', Items.REDSTONE)
                .define('b', MoeItems.IRON_SHEET.get())
                .define('c', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_iron_sheet", has(MoeItems.IRON_SHEET.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.BOARD.get(), 16)
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" a ")
                .define('a', Tags.Items.STONES)
                .unlockedBy("has_stone", has(Tags.Items.STONES)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.COPPER_SHEET.get(), 16)
                .pattern("aa ")
                .pattern("   ")
                .pattern("   ")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENERGY_CORE.get(), 8)
                .pattern(" a ")
                .pattern("aba")
                .define('a', Items.COPPER_INGOT)
                .define('b', Items.LODESTONE)
                .unlockedBy("has_copper_ingot", has(Items.LODESTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ELECTROMAGNETIC_BOOK.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" c ")
                .define('a', MoeItems.ENERGY_CORE.get())
                .define('b', Items.BOOK)
                .define('c', Items.IRON_INGOT)
                .unlockedBy("has_book", has(Items.BOOK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ELECTROMAGNETIC_ROD.get())
                .pattern("  a")
                .pattern(" b ")
                .pattern("c  ")
                .define('a', MoeItems.ENERGY_CORE.get())
                .define('b', Items.IRON_INGOT)
                .define('c', Tags.Items.STONES)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.IRON_LC.get())
                .pattern(" a ")
                .pattern("dbe")
                .pattern(" c ")
                .define('a', MoeItems.CAPACITOR.get())
                .define('b', MoeItems.BOARD.get())
                .define('c', MoeItems.INDUCTANCE.get())
                .define('d', Items.IRON_INGOT)
                .define('e', Items.REDSTONE)
                .unlockedBy("has_board", has(MoeItems.BOARD.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.GOLD_LC.get())
                .pattern("   ")
                .pattern("dbe")
                .pattern("   ")
                .define('b', MoeItems.IRON_LC.get())
                .define('d', Items.GOLD_INGOT)
                .define('e', Items.QUARTZ)
                .unlockedBy("has_iron_lc", has(MoeItems.IRON_LC.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.COPPER_LC.get())
                .pattern("   ")
                .pattern("dbe")
                .pattern("   ")
                .define('b', MoeItems.GOLD_LC.get())
                .define('d', Items.COPPER_INGOT)
                .define('e', Items.END_ROD)
                .unlockedBy("has_gold_sheet", has(MoeItems.GOLD_LC.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_LC.get())
                .requires(MoeItems.SUPERCONDUCTING_UPDATE.get())
                .requires(MoeItems.COPPER_LC.get())
                .unlockedBy("has_update", has(MoeItems.SUPERCONDUCTING_UPDATE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.IRON_POWER.get())
                .pattern(" a ")
                .pattern("dbe")
                .pattern("   ")
                .define('a', MoeItems.BJT.get())
                .define('b', MoeItems.BOARD.get())
                .define('d', Items.IRON_INGOT)
                .define('e', Items.REDSTONE)
                .unlockedBy("has_board", has(MoeItems.BOARD.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.GOLD_POWER.get())
                .pattern("   ")
                .pattern("dbe")
                .pattern("   ")
                .define('b', MoeItems.IRON_POWER.get())
                .define('d', Items.GOLD_INGOT)
                .define('e', Items.QUARTZ)
                .unlockedBy("has_iron_power", has(MoeItems.IRON_POWER.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.COPPER_POWER.get())
                .pattern("   ")
                .pattern("dbe")
                .pattern("   ")
                .define('b', MoeItems.GOLD_POWER.get())
                .define('d', Items.COPPER_INGOT)
                .define('e', Items.END_ROD)
                .unlockedBy("has_gold_power", has(MoeItems.GOLD_POWER.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_POWER.get())
                .requires(MoeItems.SUPERCONDUCTING_UPDATE.get())
                .requires(MoeItems.COPPER_LC.get())
                .unlockedBy("has_update", has(MoeItems.SUPERCONDUCTING_UPDATE.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_UPDATE.get(), 2)
                .pattern("ccc")
                .pattern("abd")
                .pattern("ccc")
                .define('a', MoeItems.ENERGY_CORE.get())
                .define('b', Items.NETHER_STAR)
                .define('c', Items.ICE)
                .define('d', Items.NETHERITE_INGOT)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.EMPTY_PRIMARY_MODULE.get())
                .pattern("ccc")
                .define('c', MoeItems.COPPER_SHEET.get())
                .unlockedBy("has_iron_sheet", has(MoeItems.COPPER_SHEET.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EMPTY_INTERMEDIATE_MODULE.get())
                .requires(MoeItems.EMPTY_PRIMARY_MODULE.get())
                .requires(Items.GOLD_INGOT)
                .requires(Items.QUARTZ)
                .unlockedBy("has_primary_module", has(MoeItems.EMPTY_PRIMARY_MODULE.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EMPTY_ADVANCED_MODULE.get())
                .requires(MoeItems.EMPTY_INTERMEDIATE_MODULE.get())
                .requires(Items.COPPER_INGOT)
                .requires(Items.END_ROD)
                .unlockedBy("has_intermediate_module", has(MoeItems.EMPTY_INTERMEDIATE_MODULE.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENHANCE_MODEM_BASEBOARD.get(), 4)
                .pattern("aba")
                .pattern("ccc")
                .define('a', Items.COPPER_INGOT)
                .define('b', MoeItems.ENERGY_CORE.get())
                .define('c', Items.REDSTONE)
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.STRENGTH_ENHANCE.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.COOLDOWN_ENHANCE.get())
                .requires(Items.ENDER_PEARL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.EFFICIENCY_ENHANCE.get())
                .requires(Items.AMETHYST_SHARD)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.ENTROPY_ENHANCE.get())
                .requires(Items.FLINT_AND_STEEL)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get())).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.LIFE_EXTRACTION_ENHANCE.get())
                .requires(Items.GOLDEN_CARROT)
                .requires(MoeItems.ENHANCE_MODEM_BASEBOARD.get())
                .unlockedBy("has_enhance_baseboard", has(MoeItems.ENHANCE_MODEM_BASEBOARD.get())).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.CARROT_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.CARROT)
                .unlockedBy("has_carrot", has(Items.CARROT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.POTATO_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.POTATO)
                .unlockedBy("has_potato", has(Items.POTATO)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.SOLUTION_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.POTION)
                .unlockedBy("has_potion", has(Items.POTION)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.POWER_BANK.get())
                .pattern("aaa")
                .pattern("bbb")
                .pattern("aaa")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', MoeItems.ENERGY_CORE.get())
                .unlockedBy("has_energy_core", has(MoeItems.ENERGY_CORE.get())).save(recipeOutput);
    }
}
