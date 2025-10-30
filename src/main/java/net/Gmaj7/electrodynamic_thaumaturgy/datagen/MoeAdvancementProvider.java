package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.MoeItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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
                    Component.translatable("advancements.electrodynamic_thaumaturgy.root.title"),
                    Component.translatable("advancements.electrodynamic_thaumaturgy.root.description"),
                    ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/advancement.png"),
                    AdvancementType.GOAL,
                    true,
                    true,
                    false
            );
            builder.addCriterion("join", PlayerTrigger.TriggerInstance.tick());
            builder.rewards(AdvancementRewards.Builder.recipe(ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "lodestone")).addLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "advancement/et_start_book"))));
            builder.save(consumer, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "normal/root"), existingFileHelper);

            register(MoeItems.ENERGY_CORE.get(), "normal", "root", "energy_core", "has_energy_core", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ENERGY_CORE.get());
            register(MoeItems.ELECTROMAGNETIC_ROD.get(), "normal", "energy_core", "magic_cast", "has_magic_cast", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ELECTROMAGNETIC_ROD.get());
            register(MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE, "normal", "energy_core", "assembly_table", "has_assembly_table", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
            register(MoeBlocks.MAGIC_LITHOGRAPHY_TABLE, "normal", "energy_core", "lithography_table", "has_lithography_table", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.MAGIC_LITHOGRAPHY_TABLE);
            register(MoeItems.EMPTY_PRIMARY_MODULE.get(), "normal", "lithography_table", "empty_module", "has_empty_module", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.EMPTY_PRIMARY_MODULE.get());
            register(MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK, "normal", "energy_core", "generator", "has_generator", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK, MoeBlocks.TEMPERATURE_GENERATOR_BLOCK, MoeBlocks.THERMAL_GENERATOR_BLOCK, MoeBlocks.BIOMASS_GENERATOR_BLOCK);
            register(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK, "normal", "generator", "energy_send", "has_energy_antenna", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK);
            register(MoeBlocks.ENERGY_BLOCK, "normal", "generator", "energy_save", "has_energy_block", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ENERGY_BLOCK);
            register(MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE, "normal", "energy_core", "modem_table", "has_modem_block", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ELECTROMAGNETIC_MODEM_TABLE);
            register(MoeItems.EFFICIENCY_ENHANCE.get(), "normal", "modem_table", "enhance_module", "has_enhancement_block", AdvancementType.TASK, consumer, existingFileHelper, MoeItems.ENHANCE_MODEM_BASEBOARD.get());
            register(MoeItems.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK.get(), "normal", "energy_core", "machine_block", "has_machine", AdvancementType.TASK, consumer, existingFileHelper, MoeBlocks.ELECTROMAGNETIC_DRIVER_MACHINE_BLOCK, MoeBlocks.BIO_REPLICATION_VAT_MACHINE_BLOCK, MoeBlocks.NITROGEN_HARVESTER_BLOCK, MoeBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE_BLOCK, MoeBlocks.ATOMIC_RECONSTRUCTION_MACHINE_BLOCK);

        }

        private void register(ItemLike itemLike, String nameSpace, String parent, String name, String criterion, AdvancementType type, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper, ItemLike... criterionItem){
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.parent(AdvancementSubProvider.createPlaceholder( ElectrodynamicThaumaturgy.MODID + ":" + nameSpace + "/" + parent));
            builder.display(
                    new ItemStack(itemLike),
                    Component.translatable("advancements." + ElectrodynamicThaumaturgy.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ElectrodynamicThaumaturgy.MODID + "." + name + ".description"),
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
            builder.save(consumer, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, nameSpace + "/" + name), existingFileHelper);
        }

    }
}
