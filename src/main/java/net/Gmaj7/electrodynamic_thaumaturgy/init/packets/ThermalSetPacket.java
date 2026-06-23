package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ThermalGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ThermalSetPacket implements CustomPacketPayload {
    int tick;
    int burn;
    BlockPos blockPos;
    public static final CustomPacketPayload.Type<ThermalSetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "thermal_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ThermalSetPacket> STREAM_CODEC = CustomPacketPayload.codec(ThermalSetPacket::write, ThermalSetPacket::new);

    public ThermalSetPacket(int tick, int burn, BlockPos blockPos){
        this.tick = tick;
        this.burn = burn;
        this.blockPos = blockPos;
    }

    public ThermalSetPacket(FriendlyByteBuf buf){
        this.tick = buf.readInt();
        this.burn = buf.readInt();
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(tick);
        buf.writeInt(burn);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ThermalSetPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof ThermalGeneratorBE) {
                    ((ThermalGeneratorBE) blockEntity).setBurnTime(packet.tick);
                    ((ThermalGeneratorBE) blockEntity).setFullBurnTime(packet.burn);
                }
            }
        });
    }
}
