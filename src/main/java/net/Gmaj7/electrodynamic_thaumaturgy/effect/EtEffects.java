package net.Gmaj7.electrodynamic_thaumaturgy.effect;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.custom.ElectricFieldDomainEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.custom.ExcitingEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.custom.MagneticLevitationEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.effect.custom.StElmo_sFireEffect;
import net.Gmaj7.electrodynamic_thaumaturgy.init.Attributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EtEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ElectrodynamicThaumaturgy.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXCITING = EFFECTS.register("exciting",
            () -> new ExcitingEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_FIELD_DOMAIN = EFFECTS.register("electric_field_domain",
            () -> new ElectricFieldDomainEffect(MobEffectCategory.BENEFICIAL, 0x00ED73));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_ELECTRIC_RELEASE = EFFECTS.register("electric_energy_release",
            () -> new EtEffect(MobEffectCategory.BENEFICIAL, 0x666666)
                    .addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_effect.emspeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_effect.easpeed"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> ST_ELMO_S_FIRE = EFFECTS.register("st_elmo_s_fire",
            () -> new StElmo_sFireEffect(MobEffectCategory.BENEFICIAL, 0xAA0000));
    public static final DeferredHolder<MobEffect, MobEffect> MAGNET_RESONANCE = EFFECTS.register("magnet_resonance",
            () -> new EtEffect(MobEffectCategory.HARMFUL, 0x0099B6));
    public static final DeferredHolder<MobEffect, MobEffect> NERVE_BLOCKING = EFFECTS.register("block_nerve",
            () -> new EtEffect(MobEffectCategory.HARMFUL, 0x0078AA)
                    .addAttributeModifier(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_effect.bsspeed"), -0.15000000596046448, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final DeferredHolder<MobEffect, MobEffect> MAGNETIC_LEVITATION_EFFECT = EFFECTS.register("magnetic_levitation",
            () -> new MagneticLevitationEffect(MobEffectCategory.BENEFICIAL, 0x9812DD));
    public static final DeferredHolder<MobEffect, MobEffect> PHOTO_CORROSIVE = EFFECTS.register("photo_corrosive",
            () -> new EtEffect(MobEffectCategory.HARMFUL, 0xFAF681)
                    .addAttributeModifier(Attributes.CORROSION, Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "et_effect.corrosion"), 0.25, AttributeModifier.Operation.ADD_VALUE));
    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);}
}
