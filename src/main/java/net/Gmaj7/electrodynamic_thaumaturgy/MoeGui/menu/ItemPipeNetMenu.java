package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ItemPipeNetMenu extends PipeNetMenu{
    protected LinkedHashMap<BlockPos, Map<Direction, List<ItemStack>>> filter;

    public ItemPipeNetMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract = buffer.readMap(
            buf -> buf.readBlockPos(),
            buf -> buf.readMap(
                    buf2 -> buf2.readEnum(Direction.class),
                    buf2 -> buf2.readEnum(PipeNet.TransferMode.class)
            )
    );
        Map<BlockPos, Set<Direction>> insert = buffer.readMap(
                buf -> buf.readBlockPos(),
                buf -> {
                    int size = buf.readVarInt();
                    Set<Direction> set = new LinkedHashSet<>();
                    for (int i = 0; i < size; i++) {
                        set.add(buf.readEnum(Direction.class));
                    }
                    return set;
                });
        Map<BlockPos, Map<Direction, List<ItemStack>>> filters = buffer.readMap(
                buf -> buf.readBlockPos(),
                buf -> buf.readMap(
                        buf2 -> buf2.readEnum(Direction.class),
                        buf2 -> {
                            int size = buf2.readVarInt();
                            List<ItemStack> list = new ArrayList<>(size);
                            for (int i = 0; i < size; i++) {
                                list.add(ItemStack.STREAM_CODEC.decode((RegistryFriendlyByteBuf) buf2));
                            }
                            return list;
                        }
                )
        );
        this(containerId, inventory, extract, insert, filters);
    }

    public ItemPipeNetMenu(int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, List<ItemStack>>> filter) {
        this.filter = new LinkedHashMap<>(filter);
        super(MoeMenuType.ITEM_PIPE_NET_MENU.get(), containerId, inventory, extract, insert);
    }

    public Map<BlockPos, Map<Direction, List<ItemStack>>> getFilter() {
        return filter;
    }

    public List<ItemStack> getFilterItemOfPosAndDir(BlockPos pos, Direction direction){
        return filter.get(pos).get(direction);
    }

    public void addFilter(BlockPos pos, Direction direction, int slot){
        if(slot < 0 || slot > 2) return;
        ItemStack itemStack = getCarried();
        if(itemStack.isEmpty()){
            if(filter.isEmpty() || !filter.containsKey(pos)) return;
            Map<Direction, List<ItemStack>> posFilter = filter.get(pos);
            if(!posFilter.containsKey(direction)) return;
            List<ItemStack> dirFilter = posFilter.get(direction);
            if(dirFilter.size() < slot) return;
            dirFilter.remove(slot);
            if(dirFilter.isEmpty()){
                posFilter.remove(direction);
                if(posFilter.isEmpty()) {
                    this.filter.remove(pos);
                }
            }
            return;
        }
        ItemStack filterItem = itemStack.copy();
        filterItem.setCount(1);
        if(filter.isEmpty() || !filter.containsKey(pos)){
            List<ItemStack> filters = new ArrayList<>();
            filters.add(filterItem);
            Map<Direction, List<ItemStack>> map = new HashMap<>();
            map.put(direction, filters);
            this.filter.put(pos, map);
            return;
        }
        Map<Direction, List<ItemStack>> posFilter = filter.get(pos);
        if(!posFilter.containsKey(direction)){
            List<ItemStack> filters = new ArrayList<>();
            filters.add(filterItem);
            filter.get(pos).put(direction, filters);
            return;
        }
        List<ItemStack> dirFilter = posFilter.get(direction);
        if(containItem(dirFilter, filterItem)) return;
        if(dirFilter.size() > slot)
            dirFilter.set(slot, filterItem);
        else dirFilter.add(filterItem);
    }

    private boolean containItem(List<ItemStack> filter, ItemStack stack){
        boolean flag = false;
        for (ItemStack itemStack : filter){
            if(itemStack.is(stack.getItem())){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
