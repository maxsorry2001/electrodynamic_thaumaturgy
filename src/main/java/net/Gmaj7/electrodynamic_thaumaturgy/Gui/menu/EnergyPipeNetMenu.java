package net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.Gui.EtMenuTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.EnergyPipeNetSaveData;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.PipeNet.PipeNet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EnergyPipeNetMenu extends PipeNetMenu{
    public EnergyPipeNetMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
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
        this(containerId, inventory, extract, insert, netId);
    }

    public EnergyPipeNetMenu(int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert, int netId) {
        super(EtMenuTypes.ENERGY_PIPE_NET_MENU.get(), containerId, inventory, extract, insert, netId, PipeNet.PipeNetType.ENERGY);
    }

    @Override
    protected EnergyPipeNetSaveData getPipeNetData(ServerPlayer player) {
        return player.level().getDataStorage().get(EnergyPipeNetSaveData.ENERGY_PIPE_NETS);
    }

    @Override
    protected void removeLookingPlayer(ServerPlayer player) {
        player.level().getDataStorage().get(EnergyPipeNetSaveData.ENERGY_PIPE_NETS).getNet(getNetId()).removeLookingPlayer(player);
    }
}
