package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit;

import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MoeAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> MOE_ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, EelectrodynamicThaumaturgy.MODID);

    public static void register(IEventBus eventBus){
        MOE_ATTACHMENT_TYPE.register(eventBus);
    }
}
