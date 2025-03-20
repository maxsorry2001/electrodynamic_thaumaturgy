package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import com.mojang.serialization.Codec;
import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class MoeAttachmentType {
    public static final DeferredRegister<AttachmentType<?>> MOE_ATTACHMENT_TYPE = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MagicOfElectromagnetic.MODID);

    public static final Supplier<AttachmentType<Float>> EXCITING_DAMAGE = MOE_ATTACHMENT_TYPE.register("exciting_damage",
            () -> AttachmentType.<Float>builder(() -> 1.0F).serialize(Codec.FLOAT).build());
    public static final Supplier<AttachmentType<Float>> ELECTROMAGNETIC_PROTECT = MOE_ATTACHMENT_TYPE.register("electromagnetic_protect",
            () -> AttachmentType.<Float>builder(() -> 1.0F).serialize(Codec.FLOAT).build());
    public static final Supplier<AttachmentType<Float>> ELECTRIC_FIELD_DOMAIN_DAMAGE = MOE_ATTACHMENT_TYPE.register("electric_field_domain_damage",
            () -> AttachmentType.<Float>builder(() -> 1.0F).serialize(Codec.FLOAT).build());

    public static void register(IEventBus eventBus){
        MOE_ATTACHMENT_TYPE.register(eventBus);
    }
}
