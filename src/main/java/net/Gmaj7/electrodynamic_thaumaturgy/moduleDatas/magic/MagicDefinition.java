package net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.moduleDatas.magic.custom.IMoeMagic;
import net.minecraft.resources.ResourceKey;

public record MagicDefinition(ResourceKey<IMoeMagic> behaviorKey, int baseEnergyCost, int baseCooldown, float amountRate, String translationKey) {
    public static final Codec<MagicDefinition> CODEC = RecordCodecBuilder.create(magicDefinitionInstance ->
            magicDefinitionInstance.group(
                    ResourceKey.codec(EtRegistries.MAGIC_KEY).fieldOf("behavior").forGetter(MagicDefinition::behaviorKey),
                    Codec.INT.fieldOf("energy_cost").forGetter(MagicDefinition::baseEnergyCost),
                    Codec.INT.fieldOf("cooldown").forGetter(MagicDefinition::baseCooldown),
                    Codec.FLOAT.fieldOf("amountRate").forGetter(MagicDefinition::amountRate),
                    Codec.STRING.fieldOf("translation_key").forGetter(MagicDefinition::translationKey)
            ).apply(magicDefinitionInstance, MagicDefinition::new));
}
