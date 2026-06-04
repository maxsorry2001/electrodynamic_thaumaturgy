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
    protected Map<BlockPos, Map<Direction, List<ItemStack>>> filters;

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

    public ItemPipeNetMenu(int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, List<ItemStack>>> filters) {
        this.filters = filters;
        super(MoeMenuType.ITEM_PIPE_NET_MENU.get(), containerId, inventory, extract, insert);
    }

    public Map<BlockPos, Map<Direction, List<ItemStack>>> getFilters() {
        return filters;
    }

    public List<ItemStack> getFilterItemOfPosAndDir(BlockPos pos, Direction direction){
        return filters.get(pos).get(direction);
    }
}
