package net.Gmaj7.magic_of_electromagnetic.datagen;

import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class MoeRecipePrivider extends RecipeProvider implements IConditionBuilder {
    public MoeRecipePrivider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
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
                .define('c', Items.LAPIS_LAZULI)
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENERGY_CORE.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern("   ")
                .define('a', Items.LAPIS_LAZULI)
                .define('b', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.PULSED_PLASMA_MODULE.get())
                .pattern("   ")
                .pattern("abc")
                .pattern("   ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.RAY_MODULE.get())
                .pattern("   ")
                .pattern("abc")
                .pattern("   ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Blocks.REDSTONE_TORCH)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.EXCITING_MODULE.get())
                .pattern("   ")
                .pattern("abc")
                .pattern("   ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Blocks.GLASS)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.CHAIN_MODULE.get())
                .pattern("   ")
                .pattern("abc")
                .pattern("   ")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ELECTRIC_FIELD_DOMAIN_MODULE.get())
                .pattern("  c")
                .pattern("abb")
                .pattern("  c")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.PROTECT_MODULE.get())
                .pattern("  d")
                .pattern("abc")
                .pattern("  d")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .define('d', Blocks.GLASS)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ATTRACT_MODULE.get())
                .pattern("  d")
                .pattern("abc")
                .pattern("  d")
                .define('a', MoeItems.IRON_SHEET.get())
                .define('b', Items.REDSTONE)
                .define('c', Items.IRON_INGOT)
                .define('d', Items.COPPER_INGOT)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);


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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.SUPERCONDUCTING_UPDATE.get())
                .pattern("ccc")
                .pattern("abd")
                .pattern("ccc")
                .define('a', MoeItems.ENERGY_CORE.get())
                .define('b', Items.NETHER_STAR)
                .define('c', Items.ICE)
                .define('d', Items.NETHERITE_INGOT)
                .unlockedBy("has_nether_star", has(Items.NETHER_STAR)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.FE_CU_CARROT_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.CARROT)
                .unlockedBy("has_carrot", has(Items.CARROT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.FE_CU_POTATO_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.POTATO)
                .unlockedBy("has_potato", has(Items.POTATO)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MoeItems.FE_CU_SOLUTION_BATTERY.get())
                .requires(MoeItems.IRON_SHEET.get())
                .requires(MoeItems.COPPER_SHEET.get())
                .requires(Items.POTION)
                .unlockedBy("has_potion", has(Items.POTION)).save(recipeOutput);
    }
}
