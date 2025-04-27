package net.Gmaj7.magic_of_electromagnetic.MoeInit;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
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
        public static final CustomPacketPayload.Type<LightingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "lighting"));
        public static final StreamCodec<RegistryFriendlyByteBuf, LightingPacket> STREAM_CODEC = CustomPacketPayload.codec(LightingPacket::write, LightingPacket::new);

        public LightingPacket(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public LightingPacket(FriendlyByteBuf buf){
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        }

        public void write(FriendlyByteBuf buf){
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
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
}
