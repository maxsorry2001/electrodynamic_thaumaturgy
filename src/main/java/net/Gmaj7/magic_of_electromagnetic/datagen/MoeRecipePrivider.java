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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.COPPER_SHEET.get(), 16)
                .pattern("aa ")
                .pattern("   ")
                .pattern("   ")
                .define('a', Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MoeItems.ENERGY_CORE.get(), 1)
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
