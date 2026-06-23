package net.Gmaj7.electrodynamic_thaumaturgy.init;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentType {
    public static final DeferredRegister<net.neoforged.neoforge.attachment.AttachmentType<?>> MOE_ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ElectrodynamicThaumaturgy.MODID);

    public static void register(IEventBus eventBus){
        MOE_ATTACHMENT_TYPE.register(eventBus);
    }
}
