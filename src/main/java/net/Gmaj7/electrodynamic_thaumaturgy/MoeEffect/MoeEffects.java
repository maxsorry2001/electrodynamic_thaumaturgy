package net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom.ElectricFieldDomainEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom.ExcitingEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom.MagneticLevitationEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeEffect.custom.StElmo_sFireEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeEffects {
    public static final DeferredRegister<MobEffect> MOE_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, EelectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXCITING = MOE_EFFECTS.register("exciting",
            () -> new ExcitingEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_FIELD_DOMAIN = MOE_EFFECTS.register("electric_field_domain",
            () -> new ElectricFieldDomainEffect(MobEffectCategory.BENEFICIAL, 0x00ED73));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_ELECTRIC_RELEASE = MOE_EFFECTS.register("electric_energy_release",
            () -> new MoeEffect(MobEffectCategory.BENEFICIAL, 0x666666)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "moeeffect.emspeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "moeeffect.easpeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> ST_ELMO_S_FIRE = MOE_EFFECTS.register("st_elmo_s_fire",
            () -> new StElmo_sFireEffect(MobEffectCategory.BENEFICIAL, 0xAA0000));
    public static final DeferredHolder<MobEffect, MobEffect> MAGNET_RESONANCE = MOE_EFFECTS.register("magnet_resonance",
            () -> new MoeEffect(MobEffectCategory.HARMFUL, 0x0099B6));
    public static final DeferredHolder<MobEffect, MobEffect> NERVE_BLOCKING = MOE_EFFECTS.register("block_nerve",
            () -> new MoeEffect(MobEffectCategory.HARMFUL, 0x0078AA)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "moeeffect.bsspeed"), -0.15000000596046448, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> MAGNETIC_LEVITATION_EFFECT = MOE_EFFECTS.register("magnetic_levitation",
            () -> new MagneticLevitationEffect(MobEffectCategory.BENEFICIAL, 0x9812DD));
    public static final DeferredHolder<MobEffect, MobEffect> PHOTO_CORROSIVE = MOE_EFFECTS.register("photo_corrosive",
            () -> new MoeEffect(MobEffectCategory.HARMFUL, 0xFAF681)
                    .addAttributeModifier(MoeAttributes.CORROSION, ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "moeeffect.corrosion"), 0.25, AttributeModifier.Operation.ADD_VALUE));
    public static void register(IEventBus eventBus){MOE_EFFECTS.register(eventBus);}
}
