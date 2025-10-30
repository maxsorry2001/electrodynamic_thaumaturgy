package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom.PointLineParticleOption;
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

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HYDROGEN_BOND_PARTICLE = MOE_PARTICLE.register("hydrogen_bond_particle",
            () -> new SimpleParticleType(true));
    public static final Supplier<ParticleType<PointRotateParticleOption>> POINT_ROTATE_PARTICLE = MOE_PARTICLE.register("point_rotate_particle", () -> new ParticleType<>(true) {
        @Override
        public MapCodec<PointRotateParticleOption> codec() {
            return PointRotateParticleOption.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, PointRotateParticleOption> streamCodec() {
            return PointRotateParticleOption.STREAM_CODEC;
        }
    });
    public static final Supplier<ParticleType<PointLineParticleOption>> POINT_LINE_PARTICLE = MOE_PARTICLE.register("point_line_particle", () -> new ParticleType<>(true) {
        @Override
        public MapCodec<PointLineParticleOption> codec() {
            return PointLineParticleOption.CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, PointLineParticleOption> streamCodec() {
            return PointLineParticleOption.STREAM_CODEC;
        }
    });

    public static void register(IEventBus eventBus){MOE_PARTICLE.register(eventBus);}
}
