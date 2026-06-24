package net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record EnhancementData(float strength, float coolDown, float efficiency) {
    public static final EnhancementData defaultData = new EnhancementData(1F, 1F, 1F);

    public static final Codec<EnhancementData> CODEC = RecordCodecBuilder.create(enhancementNumInstance ->
            enhancementNumInstance.group(
                    Codec.FLOAT.fieldOf("strength").forGetter(EnhancementData::strength),
                    Codec.FLOAT.fieldOf("cooldown").forGetter(EnhancementData::coolDown),
                    Codec.FLOAT.fieldOf("efficiency").forGetter(EnhancementData::efficiency)
            ).apply(enhancementNumInstance, EnhancementData::new));

    public EnhancementData add(List<EnhancementData> dataList){
        float newStrength = this.strength, newCooldown = this.coolDown, newEfficiency = this.efficiency;
        for (EnhancementData data : dataList){
            newStrength += data.strength;
            newCooldown += data.coolDown;
            newEfficiency += data.efficiency;
        }
        return new EnhancementData(newStrength, newCooldown, newEfficiency);
    }

    public enum EnhancementType {
        STRENGTH,
        COOLDOWN,
        EFFICIENCY,
        EMPTY;
    }
}
