package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.BiomassGeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class BiomassSetPacket implements CustomPacketPayload {
    int tick;
    int biomass;
    BlockPos blockPos;
    public static final CustomPacketPayload.Type<BiomassSetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "biomass_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BiomassSetPacket> STREAM_CODEC = CustomPacketPayload.codec(BiomassSetPacket::write, BiomassSetPacket::new);

    public BiomassSetPacket(int tick, int biomass, BlockPos blockPos){
        this.tick = tick;
        this.biomass = biomass;
        this.blockPos = blockPos;
    }

    public BiomassSetPacket(FriendlyByteBuf buf){
        this.tick = buf.readInt();
        this.biomass = buf.readInt();
        this.blockPos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(tick);
        buf.writeInt(biomass);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(BiomassSetPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof BiomassGeneratorBE) {
                    ((BiomassGeneratorBE) blockEntity).setBiomassTime(packet.tick);
                    ((BiomassGeneratorBE) blockEntity).setFullBiomassTime(packet.biomass);
                }
            }
        });
    }
}
