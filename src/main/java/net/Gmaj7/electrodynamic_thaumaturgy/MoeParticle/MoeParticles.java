package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointRotateParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MoeParticles {
    public static final DeferredRegister<ParticleType<?>> MOE_PARTICLE = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TORCH_PARTICLE = MOE_PARTICLE.register("torch_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TORCH_PARTICLE_IN = MOE_PARTICLE.register("torch_particle_in",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SELF_MAGIC_CIRCLE_PARTICLE = MOE_PARTICLE.register("self_magic_circle_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SELF_MAGIC_CIRCLE_PARTICLE_IN = MOE_PARTICLE.register("self_magic_circle_particle_in",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WILD_MAGIC_CIRCLE_PARTICLE = MOE_PARTICLE.register("wild_magic_circle_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WILD_MAGIC_CIRCLE_PARTICLE_IN = MOE_PARTICLE.register("wild_magic_circle_particle_in",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HIGH_INTENSITY_MAGNETIC_PARTICLE_IN = MOE_PARTICLE.register("high_intensity_magnet_particle_in",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FRONT_MAGIC_CIRCLE_PARTICLE = MOE_PARTICLE.register("front_magic_circle_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FRONT_MAGIC_CIRCLE_PARTICLE_IN = MOE_PARTICLE.register("front_magic_circle_particle_in",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NORMAL_CIRCLE_PARTICLE = MOE_PARTICLE.register("normal_circle_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NORMAL_SHRINK_CIRCLE_PARTICLE = MOE_PARTICLE.register("normal_shrink_circle_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYDROGEN_BOND_PARTICLE = MOE_PARTICLE.register("hydrogen_bond_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGNETIC_FLUX_CASCADE_PARTICLE = MOE_PARTICLE.register("magnetic_flux_cascade_particle",
            () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FREQUENCY_DIVISION_ARROW_RAIN_PARTICLE = MOE_PARTICLE.register("frequency_division_arrow_rain_particle",
            () -> new SimpleParticleType(true));
    public static final Supplier<ParticleType<PointRotateParticleOption>> POINT_ROTATE_PARTICLE = MOE_PARTICLE.register("point_particle", () -> new ParticleType<>(false) {
        @Override
        public MapCodec<PointRotateParticleOption> codec() {
            return PointRotateParticleOption.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, PointRotateParticleOption> streamCodec() {
            return PointRotateParticleOption.STREAM_CODEC;
        }
    });

    public static void register(IEventBus eventBus){MOE_PARTICLE.register(eventBus);}
}
