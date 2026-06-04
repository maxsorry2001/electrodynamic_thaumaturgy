package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.MoeMenuType;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EnergyPipeNetMenu extends PipeNetMenu{
    public EnergyPipeNetMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
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
        this(containerId, inventory, extract, insert);
    }

    public EnergyPipeNetMenu(int containerId, Inventory inventory, Map<BlockPos, Map<Direction, PipeNet.TransferMode>> extract, Map<BlockPos, Set<Direction>> insert) {
        super(MoeMenuType.ENERGY_BLOCK_MENU.get(), containerId, inventory, extract, insert);
    }
}
