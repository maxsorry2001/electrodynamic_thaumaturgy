package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementProvider extends net.minecraft.data.advancements.AdvancementProvider {
    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new MoeAdvancementGenerator()));
    }

    public static final class MoeAdvancementGenerator implements AdvancementSubProvider{

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> output) {
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.display(
                    new ItemStackTemplate(EtItems.ELECTROMAGNETIC_ROD.get()),
                    Component.translatable("advancements.electrodynamic_thaumaturgy.root.title"),
                    Component.translatable("advancements.electrodynamic_thaumaturgy.root.description"),
                    Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/advancement.png"),
                    AdvancementType.GOAL,
                    true,
                    true,
                    false
            );
            builder.addCriterion("join", PlayerTrigger.TriggerInstance.tick());
            builder.rewards(AdvancementRewards.Builder.recipe(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "lodestone"))).addLootTable(ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "advancement/et_start_book"))));
            builder.save(output, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "normal/root"));

            register(EtItems.ENERGY_CORE.get(), "normal", "root", "energy_core", "has_energy_core", AdvancementType.TASK, output, EtItems.ENERGY_CORE.get());
            register(EtItems.ELECTROMAGNETIC_ROD.get(), "normal", "energy_core", "magic_cast", "has_magic_cast", AdvancementType.TASK, output, EtItems.ELECTROMAGNETIC_ROD.get());
            register(EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE.asItem(), "normal", "energy_core", "assembly_table", "has_assembly_table", AdvancementType.TASK, output, EtBlocks.ELECTROMAGNETIC_ASSEMBLY_TABLE);
            register(EtBlocks.MAGIC_ENCODE_TABLE.asItem(), "normal", "energy_core", "encode_table", "has_encode_table", AdvancementType.TASK, output, EtBlocks.MAGIC_ENCODE_TABLE);
            register(EtItems.PRIMARY_CODE_MODULE.get(), "normal", "encode_table", "empty_module", "has_empty_module", AdvancementType.TASK, output, EtItems.PRIMARY_CODE_MODULE.get());
            register(EtBlocks.PHOTOVOLTAIC_GENERATOR.asItem(), "normal", "energy_core", "generator", "has_generator", AdvancementType.TASK, output, EtBlocks.PHOTOVOLTAIC_GENERATOR, EtBlocks.TEMPERATURE_GENERATOR, EtBlocks.THERMAL_GENERATOR, EtBlocks.BIOMASS_GENERATOR);
            register(EtBlocks.ENERGY_TRANSMISSION_ANTENNA.asItem(), "normal", "generator", "energy_send", "has_energy_antenna", AdvancementType.TASK, output, EtBlocks.ENERGY_TRANSMISSION_ANTENNA);
            register(EtBlocks.ENERGY_BLOCK.asItem(), "normal", "generator", "energy_save", "has_energy_block", AdvancementType.TASK, output, EtBlocks.ENERGY_BLOCK);
            register(EtBlocks.ELECTROMAGNETIC_MODEM_TABLE.asItem(), "normal", "energy_core", "modem_table", "has_modem", AdvancementType.TASK, output, EtBlocks.ELECTROMAGNETIC_MODEM_TABLE);
            register(EtItems.ENHANCE_CHIP.get(), "normal", "modem_table", "enhance_chip", "has_enhancement", AdvancementType.TASK, output, EtItems.ENHANCE_CHIP.get());
            register(EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE.get().asItem(), "normal", "energy_core", "machine", "has_machine", AdvancementType.TASK, output, EtBlocks.ELECTROMAGNETIC_DRIVER_MACHINE, EtBlocks.BIO_REPLICATION_VAT_MACHINE, EtBlocks.ELECTROMAGNETIC_EXTRACTOR_MACHINE, EtBlocks.ATOMIC_RECONSTRUCTION_MACHINE);

        }

        private void register(Item item, String nameSpace, String parent, String name, String criterion, AdvancementType type, Consumer<AdvancementHolder> consumer, ItemLike... criterionItem){
            Advancement.Builder builder = Advancement.Builder.advancement();
            builder.parent(AdvancementSubProvider.createPlaceholder( ElectrodynamicThaumaturgy.MODID + ":" + nameSpace + "/" + parent));
            builder.display(
                    new ItemStackTemplate(item),
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
            builder.save(consumer, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, nameSpace + "/" + name));
        }

    }
}
