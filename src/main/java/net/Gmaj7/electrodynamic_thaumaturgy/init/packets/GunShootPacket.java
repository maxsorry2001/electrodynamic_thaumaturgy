package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon.FocusGun;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class GunShootPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<GunShootPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "gun_shoot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, GunShootPacket> STREAM_CODEC = CustomPacketPayload.codec(GunShootPacket::write, GunShootPacket::new);

    public GunShootPacket(){
    }

    public GunShootPacket(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
    }

    private void write(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(GunShootPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            Player player = context.player();
            ItemStack itemStack = player.getMainHandItem();
            if( player.level() instanceof ServerLevel level && itemStack.getItem() instanceof FocusGun gun){
                gun.shoot(level, player, itemStack);
            }
        });
    }
}
