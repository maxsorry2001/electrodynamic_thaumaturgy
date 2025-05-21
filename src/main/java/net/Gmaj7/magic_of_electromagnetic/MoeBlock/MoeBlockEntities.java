package net.Gmaj7.magic_of_electromagnetic.MoeBlock;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.EnergyMakerBlockEntity;
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

    public static final Supplier<BlockEntityType<EnergyMakerBlockEntity>> ENERGY_MAKER_BLOCK_BE =
            MOE_BLOCK_ENTITY.register("energy_maker_block_be", () -> BlockEntityType.Builder.of(
                    EnergyMakerBlockEntity::new, MoeBlocks.ENERGY_MAKER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
