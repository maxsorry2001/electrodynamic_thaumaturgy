package net.Gmaj7.electrodynamic_thaumaturgy.entity;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EtEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<EntityType<EtRayEntity>> ET_RAY_ENTITY =
            ENTITY_TYPES.register("et_ray_entity", () -> EntityType.Builder.<EtRayEntity>of(EtRayEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(64)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_ray_entity"))));
    public static final Supplier<EntityType<PulsedPlasmaEntity>> PULSED_PLASMA_ENTITY =
            ENTITY_TYPES.register("pulsed_plasma_entity", () -> EntityType.Builder.<PulsedPlasmaEntity>of(PulsedPlasmaEntity::new, MobCategory.MISC)
                    .sized(3F, 3F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "pulsed_plasma_entity"))));
    public static final Supplier<EntityType<AttractBeaconEntity>> ATTRACT_BEACON_ENTITY =
            ENTITY_TYPES.register("attract_beacon_entity", () -> EntityType.Builder.<AttractBeaconEntity>of(AttractBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "attract_beacon_entity"))));
    public static final Supplier<EntityType<MagneticRecombinationCannonBeaconEntity>> MAGNETIC_RECOMBINATION_CANNON_BEACON_ENTITY =
            ENTITY_TYPES.register("magnetic_recombination_cannon_beacon_entity", () -> EntityType.Builder.<MagneticRecombinationCannonBeaconEntity>of(MagneticRecombinationCannonBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "plasma_torch_beacon_entity"))));
    public static final Supplier<EntityType<MagmaLightingBeaconEntity>> MAGMA_LIGHTING_BEACON_ENTITY =
            ENTITY_TYPES.register("magma_lighting_entity", () -> EntityType.Builder.<MagmaLightingBeaconEntity>of(MagmaLightingBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magma_lighting_entity"))));
    public static final Supplier<EntityType<CoulombDomainBeaconEntity>> COULOMB_DOMAIN_BEACON_ENTITY =
            ENTITY_TYPES.register("coulomb_domain_entity", () -> EntityType.Builder.<CoulombDomainBeaconEntity>of(CoulombDomainBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "coulomb_domain_entity"))));
    public static final Supplier<EntityType<MirageEntity>> MIRAGE_ENTITY =
            ENTITY_TYPES.register("mirage_entity", () -> EntityType.Builder.<MirageEntity>of(MirageEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "mirage_entity"))));
    public static final Supplier<EntityType<MagneticFluxCascadeEntity>> MAGNETIC_FLUX_CASCADE_ENTITY =
            ENTITY_TYPES.register("magnetic_levitation", () -> EntityType.Builder.<MagneticFluxCascadeEntity>of(MagneticFluxCascadeEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magnetic_levitation"))));
    public static final Supplier<EntityType<FrequencyDivisionArrowEntity>> FREQUENCY_DIVISION_ARROW_ENTITY =
            ENTITY_TYPES.register("frequency_division_arrow_entity", () -> EntityType.Builder.<FrequencyDivisionArrowEntity>of(FrequencyDivisionArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "frequency_division_arrow_entity"))));
    public static final Supplier<EntityType<PulseArrowEntity>> PULSE_ARROW_ENTITY =
            ENTITY_TYPES.register("pulse_arrow_entity", () -> EntityType.Builder.<PulseArrowEntity>of(PulseArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "frequency_division_arrow_entity"))));
    public static final Supplier<EntityType<FrequencyDivisionBeaconEntity>> FREQUENCY_DIVISION_BEACON_ENTITY =
            ENTITY_TYPES.register("frequency_division_beacon_entity", () -> EntityType.Builder.<FrequencyDivisionBeaconEntity>of(FrequencyDivisionBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "frequency_division_beacon_entity"))));
    public static final Supplier<EntityType<PhotoacousticPulseBeaconEntity>> PHOTOACOUSTIC_PULSE_BEACON_ENTITY =
            ENTITY_TYPES.register("photoacoustic_pulse_beacon_entity", () -> EntityType.Builder.<PhotoacousticPulseBeaconEntity>of(PhotoacousticPulseBeaconEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "photoacoustic_pulse_beacon_entity"))));
    public static final Supplier<EntityType<PhotoCorrosiveNovaEntity>> PHOTO_CORROSIVE_NOVA_ENTITY =
            ENTITY_TYPES.register("photo_corrosive_nova_entity", () -> EntityType.Builder.<PhotoCorrosiveNovaEntity>of(PhotoCorrosiveNovaEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5f)
                    .clientTrackingRange(4)
                    .noSave()
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "photo_corrosive_nova_entity"))));
    public static final Supplier<EntityType<MagnetoEntropyWitchEntity>> MAGNETO_ENTROPY_WITCH_ENTITY =
            ENTITY_TYPES.register("magneto_entropy_witch_entity", () -> EntityType.Builder.<MagnetoEntropyWitchEntity>of(MagnetoEntropyWitchEntity::new, MobCategory.MONSTER)
                    .sized(0.98F, 1.98F)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magneto_entropy_witch_entity"))));
    public static final Supplier<EntityType<MagnetoEntropyWitchSummonEntity>> MAGNETO_ENTROPY_WITCH_SUMMON_ENTITY =
            ENTITY_TYPES.register("magneto_entropy_witch_entity_summon_entity", () -> EntityType.Builder.<MagnetoEntropyWitchSummonEntity>of(MagnetoEntropyWitchSummonEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .noLootTable()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magneto_entropy_witch_entity_summon_entity"))));
    public static final Supplier<EntityType<MagnetoOrderSageEntity>> MAGNETO_ORDER_SAGE_ENTITY =
            ENTITY_TYPES.register("magneto_order_sage_entity", () -> EntityType.Builder.<MagnetoOrderSageEntity>of(MagnetoOrderSageEntity::new, MobCategory.MONSTER)
                    .sized(0.98F, 1.98F)
                    .noSave()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magneto_order_sage_entity"))));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);}
}
