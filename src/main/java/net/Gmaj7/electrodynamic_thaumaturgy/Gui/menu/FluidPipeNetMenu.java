package net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.FluidPipeNetSaveData;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.PipeNet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class FluidPipeNetMenu extends PipeNetMenu{
    protected LinkedHashMap<BlockPos, Map<Direction, List<ItemStack>>> filter;

    public FluidPipeNetMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        int netId = buffer.readInt();
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
        this(containerId, inventory, extract, insert, filters, netId);
    }

    public FluidPipeNetMenu(int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert, Map<BlockPos, Map<Direction, List<ItemStack>>> filter, int netId) {
        this.filter = new LinkedHashMap<>(filter);
        super(EtMenuTypes.FLUID_PIPE_NET_MENU.get(), containerId, inventory, extract, insert, netId, PipeNet.PipeNetType.ITEM);
    }

    public Map<BlockPos, Map<Direction, List<ItemStack>>> getFilter() {
        return filter;
    }

    public List<ItemStack> getFilterItemOfPosAndDir(BlockPos pos, Direction direction){
        return filter.get(pos).get(direction);
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

    @Override
    protected FluidPipeNetSaveData getPipeNetData(ServerPlayer player) {
        return player.level().getDataStorage().get(FluidPipeNetSaveData.FLUID_PIPE_NETS);
    }

    @Override
    protected void removeLookingPlayer(ServerPlayer player) {
        player.level().getDataStorage().get(FluidPipeNetSaveData.FLUID_PIPE_NETS).getNet(getNetId()).removeLookingPlayer(player);
    }

    public void fluidPipeReset(LinkedHashMap<BlockPos, Set<Direction>> insert, LinkedHashMap<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, LinkedHashMap<BlockPos, Map<Direction, List<ItemStack>>> filter){
        super.pipeReset(insert, extract);
        this.filter = filter;
    }
}
