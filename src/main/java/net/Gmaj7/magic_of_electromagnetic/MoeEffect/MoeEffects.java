package net.Gmaj7.magic_of_electromagnetic.MoeEffect;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom.ElectricFieldDomainEffect;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom.ExcitingEffect;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom.LowEntropy;
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
    public static final DeferredRegister<MobEffect> MOE_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXCITING = MOE_EFFECTS.register("exciting",
            () -> new ExcitingEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
    public static final DeferredHolder<MobEffect, MobEffect> PROTECTING = MOE_EFFECTS.register("protecting",
            () -> new MoeEffect(MobEffectCategory.BENEFICIAL, 0x00CDAA));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_FIELD_DOMAIN = MOE_EFFECTS.register("electric_field_domain",
            () -> new ElectricFieldDomainEffect(MobEffectCategory.BENEFICIAL, 0x00ED73));
    public static final DeferredHolder<MobEffect, MobEffect> POTENTIAL_DIFFERENCE = MOE_EFFECTS.register("potential_difference",
            () -> new MoeEffect(MobEffectCategory.HARMFUL, 0x22CFDE));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_ELECTRIC_RELEASE = MOE_EFFECTS.register("electric_energy_release",
            () -> new MoeEffect(MobEffectCategory.BENEFICIAL, 0x666666)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moeeffect.emspeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moeeffect.easpeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> LOW_ENTROPY = MOE_EFFECTS.register("low_entropy",
            () -> new LowEntropy(MobEffectCategory.HARMFUL, 0xFF0000)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moeeffect.lespeed"), -0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static void register(IEventBus eventBus){MOE_EFFECTS.register(eventBus);}
}
