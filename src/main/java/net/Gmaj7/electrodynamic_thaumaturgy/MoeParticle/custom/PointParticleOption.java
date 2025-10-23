package net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeParticle.MoeParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class PointParticleOption implements ParticleOptions {
    public final Vector3f center;
    public final Vector3f color;
    public final float xRot;
    public final float yRot;
    public static final MapCodec<PointParticleOption> CODEC = RecordCodecBuilder.mapCodec(
            p -> p.group(
                            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(p_253371_ -> p_253371_.color),
                            ExtraCodecs.VECTOR3F.fieldOf("center").forGetter(point -> point.center),
                            Codec.FLOAT.fieldOf("xRot").forGetter(point -> point.xRot),
                            Codec.FLOAT.fieldOf("yRot").forGetter(point -> point.yRot))
                    .apply(p, PointParticleOption::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PointParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.center,
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.color,
            ByteBufCodecs.FLOAT, pointParticleOption -> pointParticleOption.xRot,
            ByteBufCodecs.FLOAT, pointParticleOption -> pointParticleOption.yRot, PointParticleOption::new
    );

    public PointParticleOption(Vector3f center, Vector3f color, float xRot, float yRot){
        this.center = center;
        this.color = color;
        this.xRot = xRot;
        this.yRot = yRot;
    }

    public PointParticleOption(Vector3f center, Vector3f color){
        this(center, color, 0, 0);
    }

    @Override
    public ParticleType<?> getType() {
        return MoeParticles.POINT_PARTICLE.get();
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getColor() {
        return color;
    }

    public float getXRot() {
        return xRot;
    }

    public float getYRot() {
        return yRot;
    }
}
