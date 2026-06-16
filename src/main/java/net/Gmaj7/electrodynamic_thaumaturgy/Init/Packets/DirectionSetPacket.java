package net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.IDirectionItemBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class DirectionSetPacket implements CustomPacketPayload {
    BlockPos blockPos;
    Direction direction;
    public static final CustomPacketPayload.Type<DirectionSetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "direction_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DirectionSetPacket> STREAM_CODEC = CustomPacketPayload.codec(DirectionSetPacket::write, DirectionSetPacket::new);

    public DirectionSetPacket(BlockPos blockPos, Direction direction){
        this.blockPos = blockPos;
        this.direction = direction;
    }

    public DirectionSetPacket(FriendlyByteBuf buf){
        this.blockPos = buf.readBlockPos();
        this.direction = buf.readEnum(Direction.class);
    }

    public void write(FriendlyByteBuf buf){
        buf.writeBlockPos(blockPos);
        buf.writeEnum(direction);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DirectionSetPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
            if(blockEntity instanceof IDirectionItemBlockEntity) {
                ((IDirectionItemBlockEntity) blockEntity).changeDirectionSet(packet.direction);
            }
        });
    }
}
