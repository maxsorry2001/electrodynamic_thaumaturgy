package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;


import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SelectWeaponPacket implements CustomPacketPayload {
    private final int magicSelect;
    private final InteractionHand hand;
    public static final Type<SelectWeaponPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "bow_select"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SelectWeaponPacket> STREAM_CODEC = CustomPacketPayload.codec(SelectWeaponPacket::write, SelectWeaponPacket::new);

    public SelectWeaponPacket(int magicSelect, InteractionHand hand) {
        this.magicSelect = magicSelect;
        this.hand = hand;
    }

    public SelectWeaponPacket(FriendlyByteBuf buf){
        this.magicSelect = buf.readInt();
        this.hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public void write(FriendlyByteBuf buf){
        buf.writeInt(magicSelect);
        buf.writeBoolean(hand == InteractionHand.MAIN_HAND);
    }

    public static void handle(SelectWeaponPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer serverPlayer){
                ItemStack itemInHand = serverPlayer.getItemInHand(packet.hand);
                itemInHand.set(EtDataComponentTypes.WEAPON_WORK_PATTERN, packet.magicSelect);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
