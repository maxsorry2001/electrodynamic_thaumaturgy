package net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.minecraft.resources.ResourceKey;

public record MagicDefinition(ResourceKey<INewMagic> behaviorKey, int baseEnergyCost, int baseCooldown, String translationKey) {
    public static final Codec<MagicDefinition> CODEC = RecordCodecBuilder.create(magicDefinitionInstance ->
            magicDefinitionInstance.group(
                    ResourceKey.codec(MoeRegistries.MAGIC_KEY).fieldOf("behavior").forGetter(MagicDefinition::behaviorKey),
                    Codec.INT.fieldOf("energy_cose").forGetter(MagicDefinition::baseEnergyCost),
                    Codec.INT.fieldOf("cooldown").forGetter(MagicDefinition::baseCooldown),
                    Codec.STRING.fieldOf("translation_key").forGetter(MagicDefinition::translationKey)
            ).apply(magicDefinitionInstance, MagicDefinition::new));
}
