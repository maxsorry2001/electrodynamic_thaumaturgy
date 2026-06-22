package net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.custom.FilterSettingItem;
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

public class FilterSettingItemPacket implements CustomPacketPayload {
    int slot;
    boolean isEmpty;
    ItemStack itemStack;
    public static final CustomPacketPayload.Type<FilterSettingItemPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "net_filter_item"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FilterSettingItemPacket> STREAM_CODEC = CustomPacketPayload.codec(FilterSettingItemPacket::write, FilterSettingItemPacket::new);

    public FilterSettingItemPacket(int slot, ItemStack itemStack){
        this.slot = slot;
        this.isEmpty = itemStack.isEmpty();
        this.itemStack = itemStack;
    }

    public FilterSettingItemPacket(RegistryFriendlyByteBuf buf){
        this.slot = buf.readInt();
        this.isEmpty = buf.readBoolean();
        this.itemStack = this.isEmpty ? ItemStack.EMPTY : ItemStack.STREAM_CODEC.decode(buf);
    }

    public void write(RegistryFriendlyByteBuf buf){
        buf.writeInt(slot);
        buf.writeBoolean(isEmpty);
        if(!isEmpty) ItemStack.STREAM_CODEC.encode(buf, itemStack);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(FilterSettingItemPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if(context.player().level() instanceof ServerLevel) {
                if(context.player().getMainHandItem().getItem() instanceof FilterSettingItem) {
                    List<ItemStack> list = new ArrayList<>(context.player().getMainHandItem().get(EtDataComponentTypes.FILTER_CONTAINER).allItemsCopyStream().toList());
                    if(packet.isEmpty && packet.slot < list.size()){
                        list.remove(packet.slot);
                    }
                    else {
                        if (packet.slot >= list.size()) list.add(packet.itemStack);
                        else list.set(packet.slot, packet.itemStack);
                    }
                    ItemContainerContents contents = ItemContainerContents.fromItems(list);
                    context.player().getMainHandItem().set(EtDataComponentTypes.FILTER_CONTAINER, contents);
                }
            }
        });
    }
}
