package net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.PipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.PipeNet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PipeNetSynPacket implements CustomPacketPayload {
    private int netId;
    private PipeNet.PipeNetType netType;

    private LinkedHashMap<BlockPos, Set<Direction>> insert;
    private LinkedHashMap<BlockPos, Map<Direction, PipeNet.TransferMode>> extract;

    public static final CustomPacketPayload.Type<PipeNetSynPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "pipe_net_syn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PipeNetSynPacket> STREAM_CODEC = CustomPacketPayload.codec(PipeNetSynPacket::write, PipeNetSynPacket::new);
    public PipeNetSynPacket(int netId, PipeNet.PipeNetType netType, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract){
        this.netId = netId;
        this.netType = netType;
        this.insert = new LinkedHashMap<>(insert);
        this.extract = new LinkedHashMap<>(extract);
    }

    public PipeNetSynPacket(RegistryFriendlyByteBuf buffer){
        this.netId = buffer.readInt();
        this.netType = buffer.readEnum(PipeNet.PipeNetType.class);
        this.extract = new LinkedHashMap<>(buffer.readMap(
                buf -> buf.readBlockPos(),
                buf -> buf.readMap(
                        buf2 -> buf2.readEnum(Direction.class),
                        buf2 -> buf2.readEnum(PipeNet.TransferMode.class)
                )
        ));
        this.insert = new LinkedHashMap<>(buffer.readMap(
                buf -> buf.readBlockPos(),
                buf -> {
                    int size = buf.readVarInt();
                    Set<Direction> set = new LinkedHashSet<>();
                    for (int i = 0; i < size; i++) {
                        set.add(buf.readEnum(Direction.class));
                    }
                    return set;
                }));
    }

    public void write(RegistryFriendlyByteBuf buffer){
        buffer.writeInt(netId);
        buffer.writeEnum(netType);
        buffer.writeMap(extract, (buf, pos) -> buf.writeBlockPos(pos),
                (buf, map) -> buf.writeMap(map,
                        (b, dir) -> b.writeEnum(dir),
                        (b, mode) -> b.writeEnum(mode)));
        buffer.writeMap(insert,  (buf, pos) -> buf.writeBlockPos(pos),
                (buf, set) -> {
                    buf.writeVarInt(set.size());
                    for (Direction dir : set) buf.writeEnum(dir);
                });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PipeNetSynPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            Screen screen = Minecraft.getInstance().screen;
            if(screen instanceof PipeNetScreen pipeNetScreen && pipeNetScreen.getMenu().getNetId() == packet.netId && pipeNetScreen.getMenu().getNetType() == packet.netType){
                pipeNetScreen.getMenu().pipeReset(packet.insert, packet.extract);
            }
        });
    }
}
