package net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record EnhancementData(float strength, float coolDown, float efficiency, float criticalRate, float criticalDamage, String name) {
    public static final EnhancementData savvedData = new EnhancementData(1F, 1F, 1F, 0.2F, 1.5F,"saved");

    public static final Codec<EnhancementData> CODEC = RecordCodecBuilder.create(enhancementNumInstance ->
            enhancementNumInstance.group(
                    Codec.FLOAT.fieldOf("strength").forGetter(EnhancementData::strength),
                    Codec.FLOAT.fieldOf("cooldown").forGetter(EnhancementData::coolDown),
                    Codec.FLOAT.fieldOf("efficiency").forGetter(EnhancementData::efficiency),
                    Codec.FLOAT.fieldOf("critical_rate").forGetter(EnhancementData::criticalRate),
                    Codec.FLOAT.fieldOf("critical_damage").forGetter(EnhancementData::criticalDamage),
                    Codec.STRING.fieldOf("name").forGetter(EnhancementData::name)
            ).apply(enhancementNumInstance, EnhancementData::new));

    public EnhancementData add(List<EnhancementData> dataList){
        float newStrength = this.strength, newCooldown = this.coolDown, newEfficiency = this.efficiency,
                newCriticalRate = this.criticalRate, newCriticalDamage = this.criticalDamage;
        for (EnhancementData data : dataList){
            newStrength += data.strength;
            newCooldown += data.coolDown;
            newEfficiency += data.efficiency;
            newCriticalRate += data.criticalRate;
            newCriticalDamage += data.criticalDamage;
        }
        return new EnhancementData(newStrength, newCooldown, newEfficiency, newCriticalRate, newCriticalDamage,name);
    }
}
