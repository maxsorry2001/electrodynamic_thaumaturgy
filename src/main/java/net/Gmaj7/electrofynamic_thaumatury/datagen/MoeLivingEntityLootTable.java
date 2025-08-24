package net.Gmaj7.electrofynamic_thaumatury.datagen;

import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.MoeEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.MoeItems;
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

public class MoeLivingEntityLootTable extends EntityLootSubProvider {
    protected MoeLivingEntityLootTable(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    public void generate() {
        add(MoeEntities.HARMONIC_SOVEREIGN_ENTITY.get(), LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MoeItems.SUPERCONDUCTING_UPDATE.get()))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                ));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return MoeEntities.MOE_ENTITY_TYPES.getEntries().stream().map(e -> (EntityType<?>) e.value());
    }
}
