package net.Gmaj7.electrodynamic_thaumaturgy.datagen;

import net.Gmaj7.electrodynamic_thaumaturgy.Entity.EtEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.EtItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.stream.Stream;

public class LivingEntityLootTable extends EntityLootSubProvider {
    protected LivingEntityLootTable(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    public void generate() {
        add(EtEntities.MAGNETO_ENTROPY_WITCH_ENTITY.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(EtItems.SUPERCONDUCTING_UPDATE.get()))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(EtItems.SAGE_S_MAGNETISM_SEAL.get()))
                ));
        add(EtEntities.MAGNETO_ORDER_SAGE_ENTITY.get(), LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EtEntities.ENTITY_TYPES.getEntries().stream().map(Holder::value);
    }
}
