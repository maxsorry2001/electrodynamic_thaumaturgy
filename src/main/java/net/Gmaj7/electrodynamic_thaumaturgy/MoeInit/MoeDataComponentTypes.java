package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

import java.util.function.Supplier;

public class MoeDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> MOE_DATA_COMPONENT_TYPE =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<DataComponentType<Integer>> MOE_ENERGY =
            MOE_DATA_COMPONENT_TYPE.register("moe_energy",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> MAGIC_SELECT =
            MOE_DATA_COMPONENT_TYPE.register("moe_magic_slot",
                    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<EnhancementData>> ENHANCEMENT_DATA =
            MOE_DATA_COMPONENT_TYPE.register("moe_enhancement_data",
                    () -> DataComponentType.<EnhancementData>builder().persistent(EnhancementData.CODEC).build());

    public static final Supplier<DataComponentType<BlockPos>> LINK_POS =
            MOE_DATA_COMPONENT_TYPE.register("moe_link_pos",
                    () -> DataComponentType.<BlockPos>builder().persistent(BlockPos.CODEC).build());

    public static final Supplier<DataComponentType<EntityType<?>>> ENTITY_TYPE = MOE_DATA_COMPONENT_TYPE.register("entity_type",
            () -> DataComponentType.<EntityType<?>>builder().persistent(EntityType.CODEC).build());

    public static final Supplier<DataComponentType<Identifier>> MAGIC_DEF_LOCATION = MOE_DATA_COMPONENT_TYPE.register("magic",
            () -> DataComponentType.<Identifier>builder().persistent(Identifier.CODEC).build());

    public static final Supplier<DataComponentType<ItemContainerContents>> MOE_CONTAINER = MOE_DATA_COMPONENT_TYPE.register("container",
            () -> DataComponentType.<ItemContainerContents>builder().persistent(ItemContainerContents.CODEC).networkSynchronized(ItemContainerContents.STREAM_CODEC).cacheEncoding().build());

    public static final Supplier<DataComponentType<Boolean>> FILTER_WHITE = MOE_DATA_COMPONENT_TYPE.register("filter_white",
            () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build());

    public static final Supplier<DataComponentType<SimpleFluidContent>> FLUID_FILTER = MOE_DATA_COMPONENT_TYPE.register("fluid_filter",
            () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).cacheEncoding().build());


    public static void register(IEventBus eventBus){
        MOE_DATA_COMPONENT_TYPE.register(eventBus);
    }
}
