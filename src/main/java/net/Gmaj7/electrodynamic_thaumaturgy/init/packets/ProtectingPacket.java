package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.mixinData.DataGet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ProtectingPacket implements CustomPacketPayload {
    float protectNum;
    public static final CustomPacketPayload.Type<ProtectingPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "protecting"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ProtectingPacket> STREAM_CODEC = CustomPacketPayload.codec(ProtectingPacket::write, ProtectingPacket::new);

    public ProtectingPacket(float protectNum){
        this.protectNum = protectNum;
    }

    public ProtectingPacket(FriendlyByteBuf buf){
        this.protectNum = buf.readFloat();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeFloat(protectNum);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ProtectingPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                ((DataGet)context.player()).getProtective().setProtecting(packet.protectNum);
            }
        });
    }
}
