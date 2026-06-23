package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.screen.ItemPipeNetScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.init.pipeNet.PipeNet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.*;

public class ItemPipeNetSynPacket implements CustomPacketPayload {
    private LinkedHashMap<BlockPos, Set<Direction>> insert;
    private LinkedHashMap<BlockPos, Map<Direction, PipeNet.TransferMode>> extract;
    private LinkedHashMap<BlockPos, Map<Direction, List<ItemStack>>> filter;
    private int netId;
    public static final CustomPacketPayload.Type<ItemPipeNetSynPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item_pipe_net_syn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemPipeNetSynPacket> STREAM_CODEC = CustomPacketPayload.codec(ItemPipeNetSynPacket::write, ItemPipeNetSynPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public ItemPipeNetSynPacket(Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Map<Direction, List<ItemStack>>> filter, int netId){
        this.insert = new LinkedHashMap<>(insert);
        this.extract = new LinkedHashMap<>(extract);
        this.filter = new LinkedHashMap<>(filter);
        this.netId = netId;
    }

    public ItemPipeNetSynPacket(RegistryFriendlyByteBuf buffer){
        this.netId = buffer.readInt();
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
        this.filter = new LinkedHashMap<>(buffer.readMap(
                buf -> buf.readBlockPos(),
                buf -> buf.readMap(
                        buf2 -> buf2.readEnum(Direction.class),
                        buf2 -> {
                            int size = buf2.readVarInt();
                            List<ItemStack> list = new ArrayList<>(size);
                            for (int i = 0; i < size; i++) {
                                list.add(ItemStack.STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf2));
                            }
                            return list;
                        }
                )
        ));
    }

    public void write(RegistryFriendlyByteBuf buffer){
        buffer.writeInt(netId);
        buffer.writeMap(extract, (buf, pos) -> buf.writeBlockPos(pos),
                (buf, map) -> buf.writeMap(map,
                        (b, dir) -> b.writeEnum(dir),
                        (b, mode) -> b.writeEnum(mode)));
        buffer.writeMap(insert,  (buf, pos) -> buf.writeBlockPos(pos),
                (buf, set) -> {
                    buf.writeVarInt(set.size());
                    for (Direction dir : set) buf.writeEnum(dir);
                });
        buffer.writeMap(filter, (buf, pos) -> buf.writeBlockPos(pos),
                (buf, map) -> buf.writeMap(map,
                        (b, direction) -> b.writeEnum(direction),
                        (b, list) -> {
                            RegistryFriendlyByteBuf b1 = (RegistryFriendlyByteBuf)b;// 编码 List<ItemStack>
                            b1.writeVarInt(list.size());     // 写入列表大小
                            for (ItemStack stack : list) {
                                ItemStack.STREAM_CODEC.encode(b1, stack); // 编码每个 ItemStack
                            }
                        }));
    }

    public static void handle(ItemPipeNetSynPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
                Screen screen = Minecraft.getInstance().screen;
                if(screen instanceof ItemPipeNetScreen && ((ItemPipeNetScreen)screen).getMenu().getNetId() == packet.netId){
                    ((ItemPipeNetScreen)screen).getMenu().itemPipeReset(packet.insert, packet.extract, packet.filter);
                }
        });
    }
}
