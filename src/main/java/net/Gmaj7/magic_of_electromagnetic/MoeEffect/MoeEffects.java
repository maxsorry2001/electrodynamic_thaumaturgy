package net.Gmaj7.magic_of_electromagnetic.MoeEffect;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom.ElectricFieldDomainEffect;
import net.Gmaj7.magic_of_electromagnetic.MoeEffect.custom.ExcitingEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeEffects {
    public static final DeferredRegister<MobEffect> MOE_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MagicOfElectromagnetic.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXCITING = MOE_EFFECTS.register("exciting",
            () -> new ExcitingEffect(MobEffectCategory.HARMFUL, 99638872));
    public static final DeferredHolder<MobEffect, MobEffect> PROTECTING = MOE_EFFECTS.register("protecting",
            () -> new MoeEffect(MobEffectCategory.BENEFICIAL, 66322298));
    public static final DeferredHolder<MobEffect, MobEffect> ELECTRIC_FIELD_DOMAIN = MOE_EFFECTS.register("electric_field_domain",
            () -> new ElectricFieldDomainEffect(MobEffectCategory.BENEFICIAL, 66322298));
    public static void register(IEventBus eventBus){MOE_EFFECTS.register(eventBus);}
}
