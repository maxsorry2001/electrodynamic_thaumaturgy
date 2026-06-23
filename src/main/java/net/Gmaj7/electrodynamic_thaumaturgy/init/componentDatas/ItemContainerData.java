package net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.Gmaj7.electrodynamic_thaumaturgy.item.EtItems;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.MagicCastItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.ArrayList;
import java.util.List;

public record ItemContainerData(List<ItemStackTemplate> list) {
    public static final Codec<ItemContainerData> CODEC = RecordCodecBuilder.create( i -> i.group(
            ItemStackTemplate.CODEC.listOf().xmap(List::copyOf, ArrayList::new).fieldOf("container_items").forGetter(ItemContainerData::list)
        ).apply(i, ItemContainerData::new));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, ItemContainerData> STREAM_CODEC = ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()).map(ItemContainerData::new, ItemContainerData::list);
    public static final ItemContainerData EMPTY = new ItemContainerData(List.of());

    public static ItemContainerData getDefaultRod(){
        List<ItemStackTemplate> list = new ArrayList<>();
        list.add(new ItemStackTemplate(EtItems.SUPERCONDUCTING_POWER.get()));
        list.add(new ItemStackTemplate(EtItems.SUPERCONDUCTING_LC.get()));
        list.add(new ItemStackTemplate(EtItems.RAY_MODULE.get()));
        for (int i = 3; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStackTemplate(EtItems.EMPTY_MAGIC_MODULE.get()));
        }
        return new ItemContainerData(list);
    }

    public static ItemContainerData getEmptyRod(){
        List<ItemStackTemplate> list = new ArrayList<>();
        list.add(new ItemStackTemplate(EtItems.EMPTY_POWER.get()));
        list.add(new ItemStackTemplate(EtItems.EMPTY_LC.get()));
        for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i ++){
            list.add(new ItemStackTemplate(EtItems.EMPTY_MAGIC_MODULE.get()));
        }
        return new ItemContainerData(list);
    }

    public static ItemContainerData getDefaultBow(){
        List<ItemStackTemplate> list = new ArrayList<>();
        list.add(new ItemStackTemplate(EtItems.SUPERCONDUCTING_POWER.get()));
        list.add(new ItemStackTemplate(EtItems.SUPERCONDUCTING_LC.get()));
        return new ItemContainerData(list);
    }

    public static ItemContainerData getEmptyBow(){
        List<ItemStackTemplate> list = new ArrayList<>();
        list.add(new ItemStackTemplate(EtItems.EMPTY_POWER.get()));
        list.add(new ItemStackTemplate(EtItems.EMPTY_LC.get()));
        return new ItemContainerData(list);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public ItemStack getStackInSlot(Integer integer) {
        return list.get(integer).create();
    }
}
