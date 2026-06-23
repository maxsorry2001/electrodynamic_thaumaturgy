package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.entity.custom.AbstractSovereignEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public  class CastTickPacket implements CustomPacketPayload {
    public int entityId;
    public int castTick;
    public static final CustomPacketPayload.Type<CastTickPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "cast_tick"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CastTickPacket> STREAM_CODEC = CustomPacketPayload.codec(CastTickPacket::write, CastTickPacket::new);

    public CastTickPacket(int entityId, int castTick){
        this.entityId = entityId;
        this.castTick = castTick;
    }

    public CastTickPacket(FriendlyByteBuf buf){
        this.entityId = buf.readInt();
        this.castTick = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeInt(castTick);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CastTickPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                Entity entity = context.player().level().getEntity(packet.entityId);
                if(entity instanceof AbstractSovereignEntity) {
                    ((AbstractSovereignEntity) entity).setCastTick(packet.castTick);
                    ((AbstractSovereignEntity) entity).castAnimationState.start(entity.tickCount);
                }
            }
        });
    }
}
