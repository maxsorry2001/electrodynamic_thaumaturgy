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

public class PointRotateParticleOption implements ParticleOptions {
    public final Vector3f center;
    public final Vector3f color;
    public final Vector3f rotate;
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

    public PointRotateParticleOption(Vector3f center, Vector3f color, Vector3f rotate, int lifeTime){
        this.center = center;
        this.color = color;
        this.rotate = rotate;
        this.lifeTime = lifeTime;
    }

    public PointRotateParticleOption(Vector3f center, Vector3f color){
        this(center, color, new Vector3f(0, 0, 0), 0);
    }

    @Override
    public ParticleType<?> getType() {
        return MoeParticles.POINT_ROTATE_PARTICLE.get();
    }

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getRotate() {
        return rotate;
    }
}
