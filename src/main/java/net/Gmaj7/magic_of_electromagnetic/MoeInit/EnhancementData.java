package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnhancementData(float strength, float coolDown) {
    public static final Codec<EnhancementData> CODEC = RecordCodecBuilder.create(enhancementNumInstance ->
            enhancementNumInstance.group(
                    Codec.FLOAT.optionalFieldOf("strength", 1F).forGetter(EnhancementData::strength),
                    Codec.FLOAT.optionalFieldOf("cooldown", 1F).forGetter(EnhancementData::coolDown)
            ).apply(enhancementNumInstance, EnhancementData::new));
}
