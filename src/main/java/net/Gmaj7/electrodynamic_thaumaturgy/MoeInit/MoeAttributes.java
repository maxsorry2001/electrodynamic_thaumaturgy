package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MoeAttributes {
    public static final DeferredRegister<Attribute> MOE_ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, ElectrodynamicThaumaturgy.MODID);

    public static final Holder<Attribute> CORROSION = percentRangeCreat("corrosion", 1, 1, 3);

    private static Holder<Attribute> rangeCreat(String name, int defaultNum, int min, int max){
        return MOE_ATTRIBUTE.register(name, () -> new RangedAttribute("attribute." + ElectrodynamicThaumaturgy.MODID + "." + name, defaultNum, min, max));
    }

    private static Holder<Attribute> percentRangeCreat(String name, int defaultNum, int min, int max, int scale){
        return MOE_ATTRIBUTE.register(name, () -> new PercentageAttribute("attribute." + ElectrodynamicThaumaturgy.MODID + "." + name, defaultNum, min, max, scale));
    }

    private static Holder<Attribute> percentRangeCreat(String name, int defaultNum, int min, int max){
        return percentRangeCreat(name, defaultNum, min, max, 100);
    }

    public static void register(IEventBus eventBus){
        MOE_ATTRIBUTE.register(eventBus);
    }
}
