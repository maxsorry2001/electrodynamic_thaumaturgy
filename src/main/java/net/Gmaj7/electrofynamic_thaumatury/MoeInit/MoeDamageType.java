package net.Gmaj7.electrofynamic_thaumatury.MoeInit;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface MoeDamageType {
    ResourceKey<DamageType> magnet_resonance = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "magnet_resonance"));
    ResourceKey<DamageType> mirage = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "mirage"));
    ResourceKey<DamageType> origin_thaumatury = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "origin_thaumatury"));
}
