package net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom.FilterSettingItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class FilterSettingWhitePacket implements CustomPacketPayload {
    public static final Type<FilterSettingWhitePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "net_filter_white"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FilterSettingWhitePacket> STREAM_CODEC = CustomPacketPayload.codec(FilterSettingWhitePacket::write, FilterSettingWhitePacket::new);

    public FilterSettingWhitePacket(){
    }

    public FilterSettingWhitePacket(RegistryFriendlyByteBuf buf){
    }

    public void write(RegistryFriendlyByteBuf buf){
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FilterSettingWhitePacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level() instanceof ServerLevel) {
                if(context.player().getMainHandItem().getItem() instanceof FilterSettingItem) {
                    boolean flag = context.player().getMainHandItem().getOrDefault(MoeDataComponentTypes.FILTER_WHITE, true);
                    context.player().getMainHandItem().set(MoeDataComponentTypes.FILTER_WHITE, !flag);
                }
            }
        });
    }
}
