package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.AtomicReconstructionBE;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class AtomicPacket implements CustomPacketPayload {
    BlockPos blockPos;
    int progress;
    public static final CustomPacketPayload.Type<AtomicPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "atomic"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AtomicPacket> STREAM_CODEC = CustomPacketPayload.codec(AtomicPacket::write, AtomicPacket::new);

    public AtomicPacket(BlockPos blockPos, int progress){
        this.blockPos = blockPos;
        this.progress = progress;
    }

    public AtomicPacket(FriendlyByteBuf buf){
        this.progress = buf.readInt();
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(progress);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AtomicPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
            if(blockEntity instanceof AtomicReconstructionBE) {
                ((AtomicReconstructionBE) blockEntity).setProgress(packet.progress);
            }
        });
    }
}
