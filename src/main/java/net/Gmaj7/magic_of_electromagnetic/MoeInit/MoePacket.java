package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.IMoeEnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeData.MoeDataGet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
public class MoePacket{
    public static class MoeSelectMagicPacket implements CustomPacketPayload {
        private final int magicSelect;
        private final InteractionHand hand;
        public static final CustomPacketPayload.Type<MoeSelectMagicPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "magic_select"));
        public static final StreamCodec<RegistryFriendlyByteBuf, MoeSelectMagicPacket> STREAM_CODEC = CustomPacketPayload.codec(MoeSelectMagicPacket::write, MoeSelectMagicPacket::new);

        public MoeSelectMagicPacket(int magicSelect, InteractionHand hand) {
            this.magicSelect = magicSelect;
            this.hand = hand;
        }

        public MoeSelectMagicPacket(FriendlyByteBuf buf){
            this.magicSelect = buf.readInt();
            this.hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        }

        public void write(FriendlyByteBuf buf){
            buf.writeInt(magicSelect);
            buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
        }

        public static void handle(MoeSelectMagicPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player() instanceof ServerPlayer serverPlayer){
                    ItemStack itemInHand = serverPlayer.getItemInHand(packet.hand);
                    itemInHand.set(MoeDataComponentTypes.MAGIC_SELECT, packet.magicSelect);
                }
            });
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    public static class LightingPacket implements CustomPacketPayload{
        private final double x;
        private final double y;
        private final double z;
        private final float damage;
        public static final CustomPacketPayload.Type<LightingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "lighting"));
        public static final StreamCodec<RegistryFriendlyByteBuf, LightingPacket> STREAM_CODEC = CustomPacketPayload.codec(LightingPacket::write, LightingPacket::new);

        public LightingPacket(double x, double y, double z, float damage){
            this.x = x;
            this.y = y;
            this.z = z;
            this.damage = damage;
        }

        public LightingPacket(FriendlyByteBuf buf){
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
            this.damage = buf.readFloat();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeFloat(damage);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(LightingPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player() instanceof ServerPlayer serverPlayer){
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverPlayer.level());
                    lightningBolt.teleportTo(packet.x, packet.y, packet.z);
                    serverPlayer.level().addFreshEntity(lightningBolt);
                }
            });
        }
    }

    public static class ProtectingPacket implements CustomPacketPayload{
        float protectNum;
        public static final CustomPacketPayload.Type<ProtectingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "protecting"));
        public static final StreamCodec<RegistryFriendlyByteBuf, ProtectingPacket> STREAM_CODEC = CustomPacketPayload.codec(ProtectingPacket::write, ProtectingPacket::new);

        public ProtectingPacket(float protectNum){
            this.protectNum = protectNum;
        }

        public ProtectingPacket(FriendlyByteBuf buf){
            this.protectNum = buf.readFloat();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeFloat(protectNum);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(ProtectingPacket packet, IPayloadContext context){
            context.enqueueWork(() -> {
                if(context.player().level().isClientSide()){
                    ((MoeDataGet)context.player()).getProtective().setProtecting(packet.protectNum);
                }
            });
        }
    }

    public static class EnergySetPacket implements CustomPacketPayload{
        int energy;
        BlockPos blockPos;
        public static final CustomPacketPayload.Type<EnergySetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "energy_set"));
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
                    if(blockEntity instanceof IMoeEnergyBlockEntity) {
                        ((IMoeEnergyBlockEntity) blockEntity).setEnergy(packet.energy);
                    }
                }
            });
        }
    }
}
