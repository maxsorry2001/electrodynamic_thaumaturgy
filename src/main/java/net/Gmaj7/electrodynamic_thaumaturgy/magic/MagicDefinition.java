package net.Gmaj7.electrodynamic_thaumaturgy.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtRegistries;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.custom.IMoeMagic;
import net.minecraft.resources.ResourceKey;

public record MagicDefinition(ResourceKey<IMoeMagic> behaviorKey, int baseEnergyCost, int baseCooldown, String translationKey) {
    public static final Codec<MagicDefinition> CODEC = RecordCodecBuilder.create(magicDefinitionInstance ->
            magicDefinitionInstance.group(
                    ResourceKey.codec(EtRegistries.MAGIC_KEY).fieldOf("behavior").forGetter(MagicDefinition::behaviorKey),
                    Codec.INT.fieldOf("energy_cost").forGetter(MagicDefinition::baseEnergyCost),
                    Codec.INT.fieldOf("cooldown").forGetter(MagicDefinition::baseCooldown),
                    Codec.STRING.fieldOf("translation_key").forGetter(MagicDefinition::translationKey)
            ).apply(magicDefinitionInstance, MagicDefinition::new));
}
