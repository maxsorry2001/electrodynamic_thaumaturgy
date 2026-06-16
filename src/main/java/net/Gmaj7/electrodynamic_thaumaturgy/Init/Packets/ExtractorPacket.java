package net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.ElectromagneticExtractorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ExtractorPacket implements CustomPacketPayload {
    int width;
    int depth;
    BlockPos blockPos;
    public static final CustomPacketPayload.Type<ExtractorPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "extractor"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ExtractorPacket> STREAM_CODEC = CustomPacketPayload.codec(ExtractorPacket::write, ExtractorPacket::new);

    public ExtractorPacket(int width, int depth, BlockPos blockPos){
        this.width = width;
        this.depth = depth;
        this.blockPos = blockPos;
    }

    public ExtractorPacket(FriendlyByteBuf buf){
        this.width = buf.readInt();
        this.depth = buf.readInt();
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(width);
        buf.writeInt(depth);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ExtractorPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
            if(blockEntity instanceof ElectromagneticExtractorBE) {
                ((ElectromagneticExtractorBE) blockEntity).setWidth(packet.width);
                ((ElectromagneticExtractorBE) blockEntity).setDepth(packet.depth);
            }
        });
    }
}
