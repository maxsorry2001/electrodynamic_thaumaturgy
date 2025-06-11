package net.Gmaj7.magic_of_electromagnetic.MoeBlock;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.*;
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

    public static final Supplier<BlockEntityType<TemperatureEnergyMakerBE>> TEMPERATURE_ENERGY_MAKER_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("temperature_energy_maker_block_be", () -> BlockEntityType.Builder.of(
                    TemperatureEnergyMakerBE::new, MoeBlocks.TEMPERATURE_ENERGY_MAKER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<PhotovoltaicEnergyMakerBE>> PHOTOVOLTAIC_ENERGY_MAKER_BE =
            MOE_BLOCK_ENTITY.register("photovoltaic_energy_maker_be", () -> BlockEntityType.Builder.of(
                    PhotovoltaicEnergyMakerBE::new, MoeBlocks.PHOTOVOLTAIC_ENERGY_MAKER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<ThermalEnergyMakerBE>> THERMAL_ENERGY_MAKER_BE =
            MOE_BLOCK_ENTITY.register("thermal_energy_maker_be", () -> BlockEntityType.Builder.of(
                    ThermalEnergyMakerBE::new, MoeBlocks.THERMAL_ENERGY_MAKER_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyTransmissionAntennaBE>> ENERGY_TRANSMISSION_ANTENNA_BE =
            MOE_BLOCK_ENTITY.register("energy_transmission_antenna_be", () -> BlockEntityType.Builder.of(
                    EnergyTransmissionAntennaBE::new, MoeBlocks.ENERGY_TRANSMISSION_ANTENNA_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
