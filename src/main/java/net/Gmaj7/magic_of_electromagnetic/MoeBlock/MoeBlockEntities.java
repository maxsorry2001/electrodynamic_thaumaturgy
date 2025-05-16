package net.Gmaj7.magic_of_electromagnetic.MoeBlock;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOE_BLOCK_ENTITY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MagicOfElectromagnetic.MODID);

    public static void register(IEventBus eventBus){
        MOE_BLOCK_ENTITY.register(eventBus);
    }
}
