package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.AbstractPipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NetChangePacket implements CustomPacketPayload {
    BlockPos blockPos;
    Direction direction;
    int netId;
    public static final CustomPacketPayload.Type<NetChangePacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "pipe_net_change"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NetChangePacket> STREAM_CODEC = CustomPacketPayload.codec(NetChangePacket::write, NetChangePacket::new);

    public NetChangePacket(BlockPos blockPos, Direction direction, int netId){
        this.blockPos = blockPos;
        this.direction = direction;
        this.netId = netId;
    }

    public NetChangePacket(FriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
        this.direction = buf.readEnum(Direction.class);
        this.netId = buf.readInt();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
        buf.writeEnum(direction);
        buf.writeInt(netId);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(NetChangePacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) context.player().level();
            BlockState blockState = level.getBlockState(packet.blockPos);
            if(blockState.getBlock() instanceof AbstractPipe pipe){
                PipeNetSaveData data = pipe.getPipeNetSaveData(level);
                data.loopTransferModOfPos(packet.blockPos, packet.direction);
            }
        });
    }
}
