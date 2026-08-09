package net.Gmaj7.electrodynamic_thaumaturgy.init.packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon.AbstractEnergyGun;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.weapon.FocusGun;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EnergyGunShootPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EnergyGunShootPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "energy_gun_shoot"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnergyGunShootPacket> STREAM_CODEC = CustomPacketPayload.codec(EnergyGunShootPacket::write, EnergyGunShootPacket::new);

    public EnergyGunShootPacket(){
    }

    public EnergyGunShootPacket(RegistryFriendlyByteBuf buf) {
    }

    private void write(RegistryFriendlyByteBuf buf) {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(EnergyGunShootPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            Player player = context.player();
            ItemStack itemStack = player.getMainHandItem();
            if(player.level() instanceof ServerLevel level && itemStack.getItem() instanceof AbstractEnergyGun gun){
                gun.shoot(level, player, itemStack);
            }
        });
    }
}
