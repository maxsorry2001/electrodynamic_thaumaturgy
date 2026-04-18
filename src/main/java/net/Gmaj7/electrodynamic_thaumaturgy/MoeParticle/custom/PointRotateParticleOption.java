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
import org.joml.Vector3fc;

public class PointRotateParticleOption implements ParticleOptions {
    public final Vector3fc center;
    public final Vector3fc color;
    public final Vector3fc rotate;
    public final int lifeTime;
    public static final MapCodec<PointRotateParticleOption> CODEC = RecordCodecBuilder.mapCodec(
            p -> p.group(
                            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(p_253371_ -> p_253371_.color),
                            ExtraCodecs.VECTOR3F.fieldOf("center").forGetter(point -> point.center),
                            ExtraCodecs.VECTOR3F.fieldOf("rotate").forGetter(point -> point.rotate),
                            Codec.INT.fieldOf("life_time").forGetter(point -> point.lifeTime))
                    .apply(p, PointRotateParticleOption::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PointRotateParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.center,
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.color,
            ByteBufCodecs.VECTOR3F, pointRotateParticleOption -> pointRotateParticleOption.rotate,
            ByteBufCodecs.INT, pointRotateParticleOption -> pointRotateParticleOption.lifeTime, PointRotateParticleOption::new
    );

    public PointRotateParticleOption(Vector3fc center, Vector3fc color, Vector3fc rotate, int lifeTime){
        this.center = center;
        this.color = color;
        this.rotate = rotate;
        this.lifeTime = lifeTime;
    }

    public PointRotateParticleOption(Vector3fc center, Vector3fc color){
        this(center, color, new Vector3f(0, 0, 0), 0);
    }

    @Override
    public ParticleType<?> getType() {
        return MoeParticles.POINT_ROTATE_PARTICLE.get();
    }

    public Vector3fc getCenter() {
        return center;
    }

    public Vector3fc getColor() {
        return color;
    }

    public Vector3fc getRotate() {
        return rotate;
    }
}
