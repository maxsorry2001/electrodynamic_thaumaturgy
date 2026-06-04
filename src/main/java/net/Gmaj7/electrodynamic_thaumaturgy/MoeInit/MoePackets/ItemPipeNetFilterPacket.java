package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.ItemPipe;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.ItemPipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ItemPipeNetFilterPacket implements CustomPacketPayload {
    BlockPos blockPos;
    Direction direction;
    int slot;
    boolean isEmpty;
    ItemStack itemStack;
    public static final CustomPacketPayload.Type<ItemPipeNetFilterPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "item_pipe_net_filter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemPipeNetFilterPacket> STREAM_CODEC = CustomPacketPayload.codec(ItemPipeNetFilterPacket::write, ItemPipeNetFilterPacket::new);

    public ItemPipeNetFilterPacket(BlockPos blockPos, Direction direction, int slot, ItemStack itemStack){
        this.blockPos = blockPos;
        this.direction = direction;
        this.slot = slot;
        this.isEmpty = itemStack.isEmpty();
        this.itemStack = itemStack;
    }

    public ItemPipeNetFilterPacket(RegistryFriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
        this.direction = buf.readEnum(Direction.class);
        this.slot = buf.readInt();
        this.isEmpty = buf.readBoolean();
        this.itemStack = this.isEmpty ? ItemStack.EMPTY : ItemStack.STREAM_CODEC.decode(buf);
    }

    public void write(RegistryFriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
        buf.writeEnum(direction);
        buf.writeInt(slot);
        buf.writeBoolean(isEmpty);
        if(!isEmpty) ItemStack.STREAM_CODEC.encode(buf, itemStack);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ItemPipeNetFilterPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) context.player().level();
            BlockState blockState = level.getBlockState(packet.blockPos);
            if(blockState.getBlock() instanceof ItemPipe pipe){
                ItemPipeNetSaveData data = pipe.getPipeNetSaveData(level);
                data.addFilter(packet.blockPos, packet.direction, packet.itemStack, packet.slot);
            }
        });
    }
}
