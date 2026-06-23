package net.Gmaj7.electrodynamic_thaumaturgy.init;

import com.mojang.serialization.Codec;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.EnhancementData;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EtDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> ET_DATA_COMPONENT_TYPE =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<DataComponentType<Integer>> ET_ENERGY = ET_DATA_COMPONENT_TYPE.register("et_energy",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> MAGIC_SELECT = ET_DATA_COMPONENT_TYPE.register("et_magic_slot",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> BOW_WORK_PATTERN = ET_DATA_COMPONENT_TYPE.register("bow_work_pattern",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<EnhancementData>> ENHANCEMENT_DATA = ET_DATA_COMPONENT_TYPE.register("et_enhancement_data",
                    () -> DataComponentType.<EnhancementData>builder().persistent(EnhancementData.CODEC).build());

    public static final Supplier<DataComponentType<BlockPos>> LINK_POS = ET_DATA_COMPONENT_TYPE.register("et_link_pos",
                    () -> DataComponentType.<BlockPos>builder().persistent(BlockPos.CODEC).build());

    public static final Supplier<DataComponentType<EntityType<?>>> ENTITY_TYPE = ET_DATA_COMPONENT_TYPE.register("entity_type",
            () -> DataComponentType.<EntityType<?>>builder().persistent(EntityType.CODEC).build());

    public static final Supplier<DataComponentType<Identifier>> MAGIC_DEF_LOCATION = ET_DATA_COMPONENT_TYPE.register("magic",
            () -> DataComponentType.<Identifier>builder().persistent(Identifier.CODEC).build());

    public static final Supplier<DataComponentType<ItemContainerData>> ET_CONTAINER = ET_DATA_COMPONENT_TYPE.register("et_container",
            () -> DataComponentType.<ItemContainerData>builder().persistent(ItemContainerData.CODEC).networkSynchronized(ItemContainerData.STREAM_CODEC).cacheEncoding().build());

    public static final Supplier<DataComponentType<ItemContainerContents>> FILTER_CONTAINER = ET_DATA_COMPONENT_TYPE.register("et_filter_container",
            () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).build());

    public static final Supplier<DataComponentType<Boolean>> FILTER_WHITE = ET_DATA_COMPONENT_TYPE.register("filter_white",
            () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build());

    public static final Supplier<DataComponentType<SimpleFluidContent>> FLUID_CONTAINER = ET_DATA_COMPONENT_TYPE.register("fluid_filter",
            () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).cacheEncoding().build());


    public static void register(IEventBus eventBus){
        ET_DATA_COMPONENT_TYPE.register(eventBus);
    }
}
