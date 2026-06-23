package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.IEnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EnergySetPacket implements CustomPacketPayload {
    int energy;
    BlockPos blockPos;
    public static final CustomPacketPayload.Type<EnergySetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "energy_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnergySetPacket> STREAM_CODEC = CustomPacketPayload.codec(EnergySetPacket::write, EnergySetPacket::new);

    public EnergySetPacket(int energy, BlockPos blockPos){
        this.energy = energy;
        this.blockPos = blockPos;
    }

    public EnergySetPacket(FriendlyByteBuf buf){
        this.energy = buf.readInt();
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(energy);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(EnergySetPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof IEnergyBlockEntity) {
                    ((IEnergyBlockEntity) blockEntity).setEnergy(packet.energy);
                }
            }
        });
    }
}
