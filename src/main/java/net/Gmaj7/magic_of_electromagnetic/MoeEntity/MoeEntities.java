package net.Gmaj7.magic_of_electromagnetic.MoeEntity;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEntity.custom.*;
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
    public static final Supplier<EntityType<PulsedPlasmaEntity>> PULSED_PLASMA_ENTITY =
            MOE_ENTITY_TYPES.register("pulsed_plasma_entity", () -> EntityType.Builder.<PulsedPlasmaEntity>of(PulsedPlasmaEntity::new, MobCategory.MISC)
                    .sized(3F, 3F)
                    .clientTrackingRange(4)
                    .build("pulsed_plasma_entity"));
    public static final Supplier<EntityType<MagnetArrowEntity>> MAGNET_ARROW_ENTITY =
            MOE_ENTITY_TYPES.register("magnet_arrow_entity", () -> EntityType.Builder.<MagnetArrowEntity>of(MagnetArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("magnet_arrow_entity"));
    public static final Supplier<EntityType<PlasmaTorchBeaconEntity>> PLASMA_TORCH_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("plasma_torch_beacon_entity", () -> EntityType.Builder.<PlasmaTorchBeaconEntity>of(PlasmaTorchBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("plasma_torch_beacon_entity"));
    public static final Supplier<EntityType<MagmaLightingBeaconEntity>> MAGMA_LIGHTING_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("magma_lighting_entity", () -> EntityType.Builder.<MagmaLightingBeaconEntity>of(MagmaLightingBeaconEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.13F)
                    .build("magma_lighting_entity"));

    public static void register(IEventBus eventBus){MOE_ENTITY_TYPES.register(eventBus);}
}
