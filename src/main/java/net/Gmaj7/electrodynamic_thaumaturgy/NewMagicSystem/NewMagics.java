package net.Gmaj7.electrodynamic_thaumaturgy.NewMagicSystem;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NewMagics {
    public static final DeferredRegister<INewMagic> MAGIC =
            DeferredRegister.create(MoeRegistries.MAGIC_KEY, ElectrodynamicThaumaturgy.MODID);

    public static final Supplier<INewMagic> TT =
            MAGIC.register("test_magic", TestBehavior::new);

    public static void register(IEventBus eventBus){
        MAGIC.register(eventBus);
    }
}
