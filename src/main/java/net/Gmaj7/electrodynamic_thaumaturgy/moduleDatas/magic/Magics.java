package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Magics {
    public static final DeferredRegister<IMoeMagic> MAGIC =
            DeferredRegister.create(EtRegistries.MAGIC_KEY, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<IMoeMagic> RAY = MAGIC.register("ray", ElectromagneticRay::new);
    public static final Supplier<IMoeMagic> PULSED_PLASMA = MAGIC.register("pulsed_plasma", PulsedPlasma::new);
    public static final Supplier<IMoeMagic> PROTECTING = MAGIC.register("protecting", Protecting::new);
    public static final Supplier<IMoeMagic> EXCITING = MAGIC.register("exciting", Exciting::new);
    public static final Supplier<IMoeMagic> ELECTRIC_FIELD_DOMAIN = MAGIC.register("electric_field_domain", ElectricFieldDomain::new);
    public static final Supplier<IMoeMagic> ATTRACT = MAGIC.register("attract", Attract::new);
    public static final Supplier<IMoeMagic> TREE_CURRENT = MAGIC.register("tree_current", TreeCurrent::new);
    public static final Supplier<IMoeMagic> REFRACTION = MAGIC.register("refraction", Refraction::new);
    public static final Supplier<IMoeMagic> ELECTRIC_ENERGY_RELEASE = MAGIC.register("electric_energy_release", ElectricEnergyRelease::new);
    public static final Supplier<IMoeMagic> MAGNETIC_RECOMBINATION_CANNON = MAGIC.register("magnetic_recombination_cannon", MagneticRecombinationCannon::new);
    public static final Supplier<IMoeMagic> ELECTROMAGNETIC_ASSAULT = MAGIC.register("electromagnetic_assault", ElectromagneticAssault::new);
    public static final Supplier<IMoeMagic> MAGMA_LIGHTING = MAGIC.register("magma_lighting", MagmaLighting::new);
    public static final Supplier<IMoeMagic> ST_ELMO_S_FIRE = MAGIC.register("st_elmo_s_fire", St_Elmo_s_fire::new);
    public static final Supplier<IMoeMagic> HYDROGEN_BOND_FRACTURE = MAGIC.register("hydrogen_bond_fracture", HydrogenBondFracture::new);
    public static final Supplier<IMoeMagic> LIGHTING_STRIKE = MAGIC.register("lighting_strike", LightingStrike::new);
    public static final Supplier<IMoeMagic> MAGNET_RESONANCE = MAGIC.register("magnet_resonance", MagnetResonance::new);
    public static final Supplier<IMoeMagic> BLOCK_NERVE = MAGIC.register("block_nerve", NerveBlocking::new);
    public static final Supplier<IMoeMagic> DISTURBING_BY_HIGH_INTENSITY_MAGNETIC = MAGIC.register("disturbing_by_high_intensity_magnetic", DisturbingByHighIntensityMagnetic::new);
    public static final Supplier<IMoeMagic> COULOMB_DOMAIN = MAGIC.register("coulomb_domain", CoulombDomain::new);
    public static final Supplier<IMoeMagic> DOMAIN_RECONSTRUCTION = MAGIC.register("domain_reconstruction", DomainReconstruction::new);
    public static final Supplier<IMoeMagic> MIRAGE_PURSUIT = MAGIC.register("mirage_pursuit", MiragePursuit::new);
    public static final Supplier<IMoeMagic> MAGNETIC_FLUX_CASCADE = MAGIC.register("magnetic_flux_cascade", MagneticFluxCascade::new);
    public static final Supplier<IMoeMagic> FREQUENCY_DIVISION_ARROW_RAIN = MAGIC.register("frequency_division_arrow_rain", FrequencyDivisionArrowRain::new);
    public static final Supplier<IMoeMagic> SAGE_S_MAGNETISM_SEAL = MAGIC.register("sage_s_magnetism_seal", SageSMagnetismSeal::new);
    public static final Supplier<IMoeMagic> PHOTOACOUSTIC_PULSE = MAGIC.register("photoacoustic_pulse", PhotoacousticPulse::new);
    public static final Supplier<IMoeMagic> PHOTO_CORROSIVE_NOVA = MAGIC.register("photo_corrosive_nova", PhotoCorrosiveNova::new);

    public static void register(IEventBus eventBus){
        MAGIC.register(eventBus);
    }
}
