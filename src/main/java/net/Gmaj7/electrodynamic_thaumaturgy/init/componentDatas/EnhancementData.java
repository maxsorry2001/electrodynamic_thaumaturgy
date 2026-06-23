package net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnhancementData(float strength, float coolDown, float efficiency) {
    public static final EnhancementData defaultData = new EnhancementData(1F, 1F, 1F);

    public static final Codec<EnhancementData> CODEC = RecordCodecBuilder.create(enhancementNumInstance ->
            enhancementNumInstance.group(
                    Codec.FLOAT.optionalFieldOf("strength", 1F).forGetter(EnhancementData::strength),
                    Codec.FLOAT.optionalFieldOf("cooldown", 1F).forGetter(EnhancementData::coolDown),
                    Codec.FLOAT.optionalFieldOf("efficiency", 1F).forGetter(EnhancementData::efficiency)
            ).apply(enhancementNumInstance, EnhancementData::new));

    public enum EnhancementType {
        STRENGTH,
        COOLDOWN,
        EFFICIENCY,
        EMPTY;
    }
}
