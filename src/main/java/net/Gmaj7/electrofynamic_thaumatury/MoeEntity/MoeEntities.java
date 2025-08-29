package net.Gmaj7.electrofynamic_thaumatury.MoeEntity;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeEntity.custom.*;
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
    public static final Supplier<EntityType<AttractBeaconEntity>> ATTRACT_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("attract_beacon_entity", () -> EntityType.Builder.<AttractBeaconEntity>of(AttractBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("attract_beacon_entity"));
    public static final Supplier<EntityType<MagneticRecombinationCannonBeaconEntity>> MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("magnetic_recombination_cannon_beacon_entity", () -> EntityType.Builder.<MagneticRecombinationCannonBeaconEntity>of(MagneticRecombinationCannonBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .build("plasma_torch_beacon_entity"));
    public static final Supplier<EntityType<MagmaLightingBeaconEntity>> MAGMA_LIGHTING_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("magma_lighting_entity", () -> EntityType.Builder.<MagmaLightingBeaconEntity>of(MagmaLightingBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .build("magma_lighting_entity"));
    public static final Supplier<EntityType<CoulombDomainBeaconEntity>> COULOMB_DOMAIN_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("coulomb_domain_entity", () -> EntityType.Builder.<CoulombDomainBeaconEntity>of(CoulombDomainBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .build("coulomb_domain_entity"));
    public static final Supplier<EntityType<MirageEntity>> MIRAGE_ENTITY =
            MOE_ENTITY_TYPES.register("mirage_entity", () -> EntityType.Builder.<MirageEntity>of(MirageEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .build("mirage_entity"));
    public static final Supplier<EntityType<MagneticFluxCascadeEntity>> MAGNETIC_FLUX_CASCADE_ENTITY =
            MOE_ENTITY_TYPES.register("magnetic_levitation", () -> EntityType.Builder.<MagneticFluxCascadeEntity>of(MagneticFluxCascadeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .build("magnetic_levitation"));
    public static final Supplier<EntityType<FrequencyDivisionArrowEntity>> FREQUENCY_DIVISION_ARROW_ENTITY =
            MOE_ENTITY_TYPES.register("frequency_division_arrow_entity", () -> EntityType.Builder.<FrequencyDivisionArrowEntity>of(FrequencyDivisionArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .build("frequency_division_arrow_entity"));
    public static final Supplier<EntityType<FrequencyDivisionBeaconEntity>> FREQUENCY_DIVISION_BEACON_ENTITY =
            MOE_ENTITY_TYPES.register("frequency_division_beacon_entity", () -> EntityType.Builder.<FrequencyDivisionBeaconEntity>of(FrequencyDivisionBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .build("frequency_division_beacon_entity"));
    public static final Supplier<EntityType<HarmonicSovereignEntity>> HARMONIC_SOVEREIGN_ENTITY =
            MOE_ENTITY_TYPES.register("harmonic_sovereign", () -> EntityType.Builder.<HarmonicSovereignEntity>of(HarmonicSovereignEntity::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.98F)
                    .build("harmonic_sovereign"));
    public static final Supplier<EntityType<HarmonicSovereignSummonEntity>> HARMONIC_SOVEREIGN_SUMMON_ENTITY =
            MOE_ENTITY_TYPES.register("harmonic_sovereign_summon_entity", () -> EntityType.Builder.<HarmonicSovereignSummonEntity>of(HarmonicSovereignSummonEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build("harmonic_sovereign_summon_entity"));
    public static final Supplier<EntityType<SummonedHarmonicSovereignEntity>> SUMMONED_HARMONIC_SOVEREIGN_ENTITY =
            MOE_ENTITY_TYPES.register("summoned_harmonic_sovereign", () -> EntityType.Builder.<SummonedHarmonicSovereignEntity>of(SummonedHarmonicSovereignEntity::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.98F)
                    .build("summoned_harmonic_sovereign"));

    public static void register(IEventBus eventBus){MOE_ENTITY_TYPES.register(eventBus);}
}
