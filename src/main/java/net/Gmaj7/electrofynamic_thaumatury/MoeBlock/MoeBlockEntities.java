package net.Gmaj7.electrofynamic_thaumatury.MoeBlock;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOE_BLOCK_ENTITY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MagicOfElectromagnetic.MODID);

    public static final Supplier<BlockEntityType<EnergyBlockEntity>> ENERGY_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("energy_block_be", () -> BlockEntityType.Builder.of(
                    EnergyBlockEntity::new, MoeBlocks.ENERGY_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<TemperatureGeneratorBE>> TEMPERATURE_GENERATOR_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("temperature_generator_block_be", () -> BlockEntityType.Builder.of(
                    TemperatureGeneratorBE::new, MoeBlocks.TEMPERATURE_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<PhotovoltaicGeneratorBE>> PHOTOVOLTAIC_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("photovoltaic_generator_be", () -> BlockEntityType.Builder.of(
                    PhotovoltaicGeneratorBE::new, MoeBlocks.PHOTOVOLTAIC_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ThermalGeneratorBE>> THERMAL_GENERATOR_BE =
            MOE_BLOCK_ENTITY.register("thermal_generator_be", () -> BlockEntityType.Builder.of(
                    ThermalGeneratorBE::new, MoeBlocks.THERMAL_GENERATOR_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyTransmissionAntennaBE>> ENERGY_TRANSMISSION_ANTENNA_BE =
            MOE_BLOCK_ENTITY.register("energy_transmission_antenna_be", () -> BlockEntityType.Builder.of(
                    EnergyTransmissionAntennaBE::new, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<MagicCastBlockBE>> MAGIC_CAST_BE =
            MOE_BLOCK_ENTITY.register("magic_cast_be", () -> BlockEntityType.Builder.of(
                    MagicCastBlockBE::new, MoeBlocks.MAGIC_CAST_MACHINE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<LivingEntityCloneBE>> LIVING_ENTITY_CLONE_BE =
            MOE_BLOCK_ENTITY.register("living_entity_clone_be", () -> BlockEntityType.Builder.of(
                    LivingEntityCloneBE::new, MoeBlocks.LIVING_ENTITY_CLONE_MACHINE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
