package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnhancementData(float strength, float coolDown, float efficiency, int potential_difference, int bioelectricStop) {
    public static final EnhancementData defaultData = new EnhancementData(1F, 1F, 1F, 0, 0);

    public static final Codec<EnhancementData> CODEC = RecordCodecBuilder.create(enhancementNumInstance ->
            enhancementNumInstance.group(
                    Codec.FLOAT.optionalFieldOf("strength", 1F).forGetter(EnhancementData::strength),
                    Codec.FLOAT.optionalFieldOf("cooldown", 1F).forGetter(EnhancementData::coolDown),
                    Codec.FLOAT.optionalFieldOf("efficiency", 1F).forGetter(EnhancementData::efficiency),
                    Codec.INT.optionalFieldOf("potential_difference", 0).forGetter(EnhancementData::potential_difference),
                    Codec.INT.optionalFieldOf("bioelectricStop", 0).forGetter(EnhancementData::bioelectricStop)
            ).apply(enhancementNumInstance, EnhancementData::new));
}
