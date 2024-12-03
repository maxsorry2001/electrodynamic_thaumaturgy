package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MoeSelectMagicPacket implements CustomPacketPayload {
    private final int magicSelect;
    public static final CustomPacketPayload.Type<MoeSelectMagicPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "magic_select"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MoeSelectMagicPacket> STREAM_CODEC = CustomPacketPayload.codec(MoeSelectMagicPacket::write, MoeSelectMagicPacket::new);

    public MoeSelectMagicPacket(int magicSelect) {
        this.magicSelect = magicSelect;
    }

    public MoeSelectMagicPacket(FriendlyByteBuf buf){
        this.magicSelect = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(magicSelect);
    }

    public static void handle(MoeSelectMagicPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer serverPlayer){
                ItemStack mainHand = serverPlayer.getMainHandItem();
                mainHand.set(MoeDataComponentTypes.MAGIC_SLOT, packet.magicSelect);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
