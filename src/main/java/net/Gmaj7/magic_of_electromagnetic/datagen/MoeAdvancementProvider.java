package net.Gmaj7.magic_of_electromagnetic.datagen;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.MoeBlocks;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.MoeItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MoeAdvancementProvider extends AdvancementProvider {
    public MoeAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new MoeAdvancementGenerator()));
    }

    public static final class MoeAdvancementGenerator implements AdvancementProvider.AdvancementGenerator{

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.display(
                    new ItemStack(MoeItems.ELECTROMAGNETIC_ROD.get()),
                    Component.translatable("advancements.magic_of_electromagnetic.root.title"),
                    Component.translatable("advancements.magic_of_electromagnetic.root.description"),
                    ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/advancement.png"),
                    AdvancementType.GOAL,
                    true,
                    true,
                    false
            );
            builder.addCriterion("has_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE));
            builder.requirements(AdvancementRequirements.anyOf(List.of("has_crafting_table")));
            builder.rewards(AdvancementRewards.Builder.recipe(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "lodestone")));
            builder.save(consumer, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "normal/root"), existingFileHelper);

            register(MoeItems.ENERGY_CORE.get(), MagicOfElectromagnetic.MODID, "normal", "root", "energy_core", "has_energy_core", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ENERGY_CORE.get());
            register(MoeItems.ELECTROMAGNETIC_ROD.get(), MagicOfElectromagnetic.MODID, "normal", "energy_core", "magic_cast", "has_magic_cast", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ELECTROMAGNETIC_ROD.get(), MoeItems.ELECTROMAGNETIC_BOOK.get());
            register(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE, MagicOfElectromagnetic.MODID, "normal", "energy_core", "assembly_table", "has_assembly_table", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
            register(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE, MagicOfElectromagnetic.MODID, "normal", "energy_core", "lithography_table", "has_lithography_table", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.MAGIC_LITHOGRAPHY_TABLE);
            register(MoeItems.EMPTY_PRIMARY_MODULE.get(), MagicOfElectromagnetic.MODID, "normal", "lithography_table", "empty_module", "has_empty_module", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.EMPTY_PRIMARY_MODULE.get());
            register(MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK, MagicOfElectromagnetic.MODID, "normal", "energy_core", "energy_maker", "has_energy_maker", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK, MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK);
            register(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK, MagicOfElectromagnetic.MODID, "normal", "energy_maker", "energy_send", "has_energy_antenna", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK);
            register(MoeBlocks.ENERGY_BLOCK, MagicOfElectromagnetic.MODID, "normal", "energy_maker", "energy_save", "has_energy_block", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ENERGY_BLOCK);
            register(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE, MagicOfElectromagnetic.MODID, "normal", "energy_core", "modem_table", "has_modem_block", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
            register(MoeItems.EFFICIENCY_ENHANCE.get(), MagicOfElectromagnetic.MODID, "normal", "modem_table", "enhance_module", "has_enhancement_block", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ENHANCE_MODEM_BASEBOARD.get());

        }

        private void register(ItemLike itemLike, String id, String nameSpace, String parent, String name, String criterion, AdvancementType type, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper, ItemLike... criterionItem){
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.parent(AdvancementSubProvider.createPlaceholder( id + ":" + nameSpace + "/" + parent));
            builder.display(
                    new ItemStack(itemLike),
                    Component.translatable("advancements." + MagicOfElectromagnetic.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + MagicOfElectromagnetic.MODID + "." + name + ".description"),
                    null,
                    type,
                    true,
                    true,
                    false
            );
            List<String> list = new ArrayList<>();
            for (int i = 0; i < criterionItem.length; i++) {
                builder.addCriterion(criterion + "_" + i, InventoryChangeTrigger.TriggerInstance.hasItems(criterionItem[i]));
                list.add(criterion + "_" + i);
            }
            builder.requirements(AdvancementRequirements.anyOf(list));
            builder.save(consumer, ResourceLocation.fromNamespaceAndPath(id, nameSpace + "/" + name), existingFileHelper);
        }

    }
}
