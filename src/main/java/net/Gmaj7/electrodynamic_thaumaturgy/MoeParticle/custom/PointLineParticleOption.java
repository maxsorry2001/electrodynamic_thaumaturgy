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

public class PointLineParticleOption implements ParticleOptions {
    public final Vector3f target;
    public final Vector3f color;
    public final Vector3f speed;
    public final int lifeTime;
    public static final MapCodec<PointLineParticleOption> CODEC = RecordCodecBuilder.mapCodec(
            p -> p.group(
                            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(p_253371_ -> p_253371_.color),
                            ExtraCodecs.VECTOR3F.fieldOf("target").forGetter(point -> point.target),
                            ExtraCodecs.VECTOR3F.fieldOf("speed").forGetter(point -> point.speed),
                            Codec.INT.fieldOf("life_time").forGetter(point -> point.lifeTime))
                    .apply(p, PointLineParticleOption::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PointLineParticleOption> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.target,
            ByteBufCodecs.VECTOR3F, pointParticleOption -> pointParticleOption.color,
            ByteBufCodecs.VECTOR3F, pointRotateParticleOption -> pointRotateParticleOption.speed,
            ByteBufCodecs.INT, pointRotateParticleOption -> pointRotateParticleOption.lifeTime, PointLineParticleOption::new
    );

    public PointLineParticleOption(Vector3f target, Vector3f color, Vector3f speed, int lifeTime){
        this.target = target;
        this.color = color;
        this.speed = speed;
        this.lifeTime = lifeTime;
    }

    public PointLineParticleOption(Vector3f target, Vector3f color){
        this(target, color, new Vector3f(0, 0, 0), 0);
    }

    @Override
    public ParticleType<?> getType() {
        return MoeParticles.POINT_LINE_PARTICLE.get();
    }

    public Vector3f getTarget() {
        return target;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getSpeed() {
        return speed;
    }
}
