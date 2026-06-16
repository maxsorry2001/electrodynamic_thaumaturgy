package net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlock.ItemPipe;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.ItemPipeNet;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.ItemPipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class ItemPipeNetFilterPacket implements CustomPacketPayload {
    BlockPos blockPos;
    Direction direction;
    int slot;
    boolean isEmpty;
    ItemStack itemStack;
    int netId;
    public static final CustomPacketPayload.Type<ItemPipeNetFilterPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item_pipe_net_filter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemPipeNetFilterPacket> STREAM_CODEC = CustomPacketPayload.codec(ItemPipeNetFilterPacket::write, ItemPipeNetFilterPacket::new);

    public ItemPipeNetFilterPacket(BlockPos blockPos, Direction direction, int slot, ItemStack itemStack, int netId){
        this.blockPos = blockPos;
        this.direction = direction;
        this.slot = slot;
        this.isEmpty = itemStack.isEmpty();
        this.itemStack = itemStack;
        this.netId = netId;
    }

    public ItemPipeNetFilterPacket(RegistryFriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
        this.direction = buf.readEnum(Direction.class);
        this.slot = buf.readInt();
        this.isEmpty = buf.readBoolean();
        this.itemStack = this.isEmpty ? ItemStack.EMPTY : ItemStack.STREAM_CODEC.decode(buf);
        this.netId = buf.readInt();
    }

    public void write(RegistryFriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
        buf.writeEnum(direction);
        buf.writeInt(slot);
        buf.writeBoolean(isEmpty);
        if(!isEmpty) ItemStack.STREAM_CODEC.encode(buf, itemStack);
        buf.writeInt(netId);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ItemPipeNetFilterPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level() instanceof ServerLevel serverLevel) {
                BlockState blockState = serverLevel.getBlockState(packet.blockPos);
                if (blockState.getBlock() instanceof ItemPipe pipe) {
                    ItemPipeNetSaveData data = pipe.getPipeNetSaveData(serverLevel);
                    data.addFilter(packet.blockPos, packet.direction, packet.itemStack, packet.slot);
                    ItemPipeNet net = data.getNet(packet.netId);
                    List<ServerPlayer> observers = new ArrayList<>(net.getLookingPlayer());
                    for (ServerPlayer serverPlayer : observers)
                        PacketDistributor.sendToPlayer(serverPlayer, new ItemPipeNetSynPacket(net.getInsert(), net.getExtract(), net.getFilter(), net.getNetId()));
                }
            }
        });
    }
}
