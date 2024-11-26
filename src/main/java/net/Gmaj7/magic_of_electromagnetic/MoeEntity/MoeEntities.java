package net.Gmaj7.magic_of_electromagnetic.MoeEntity;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.MoeRayEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.PlasmaEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeEntities {
    public static final DeferredRegister<EntityType<?>> MOE_ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MagicOfElectromagnetic.MODID);

    public static final Supplier<EntityType<MoeRayEntity>> MOE_RAY_ENTITY =
            MOE_ENTITY_TYPES.register("moe_ray_entity", () -> EntityType.Builder.<MoeRayEntity>of(MoeRayEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(64)
                    .build("moe_ray_entity"));
    public static final Supplier<EntityType<PlasmaEntity>> PLASMA_ENTITY =
            MOE_ENTITY_TYPES.register("plasma_entity", () -> EntityType.Builder.<PlasmaEntity>of(PlasmaEntity::new, MobCategory.MISC)
                    .sized(3F, 3F)
                    .clientTrackingRange(4)
                    .build("plasma_entity"));

    public static void register(IEventBus eventBus){MOE_ENTITY_TYPES.register(eventBus);}
}
