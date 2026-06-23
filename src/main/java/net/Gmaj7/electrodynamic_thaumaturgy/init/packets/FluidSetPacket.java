package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.IDirectionFluidBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class FluidSetPacket implements CustomPacketPayload {
    BlockPos blockPos;
    FluidStack fluidStack;
    boolean isEmpty;
    public static final Type<FluidSetPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "flluid_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidSetPacket> STREAM_CODEC = CustomPacketPayload.codec(FluidSetPacket::write, FluidSetPacket::new);

    public FluidSetPacket(FluidStack fluidStack, BlockPos blockPos){
        this.fluidStack = fluidStack;
        this.blockPos = blockPos;
        isEmpty = fluidStack.isEmpty();
    }

    public FluidSetPacket(RegistryFriendlyByteBuf buf){
        this.isEmpty = buf.readBoolean();
        if(!isEmpty) this.fluidStack = FluidStack.STREAM_CODEC.decode(buf);
        this.blockPos = buf.readBlockPos();
    }

    public void write(RegistryFriendlyByteBuf buf){
        buf.writeBoolean(isEmpty);
        if(!isEmpty) FluidStack.STREAM_CODEC.encode(buf, fluidStack);
        buf.writeBlockPos(blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FluidSetPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level().isClientSide()){
                BlockEntity blockEntity = context.player().level().getBlockEntity(packet.blockPos);
                if(blockEntity instanceof IDirectionFluidBlockEntity) {
                    ((IDirectionFluidBlockEntity) blockEntity).setFluid(packet.isEmpty ? FluidStack.EMPTY : packet.fluidStack);
                }
            }
        });
    }
}
