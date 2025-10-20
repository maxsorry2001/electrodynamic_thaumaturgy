package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface MoeDamageType {
    ResourceKey<DamageType> magnet_resonance = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "magnet_resonance"));
    ResourceKey<DamageType> mirage = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "mirage"));
    ResourceKey<DamageType> origin_thaumaturgy = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "origin_thaumaturgy"));
}
