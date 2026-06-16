package net.Gmaj7.electrodynamic_thaumaturgy.Init;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.magic.custom.IMoeMagic;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = ElectrodynamicThaumaturgy.MODID)
public class EtRegistries {
    public static final ResourceKey<Registry<IMoeMagic>> MAGIC_KEY = createRegistryKey("magic");
    public static final Registry<IMoeMagic> MAGIC_REGISTRY = new RegistryBuilder<>(MAGIC_KEY).maxId(256).create();

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, name));
    }

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event){
        event.register(EtRegistries.MAGIC_REGISTRY);
    }
}
