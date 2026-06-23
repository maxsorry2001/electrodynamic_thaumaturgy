package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.init.pipeNet.ItemPipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;

public class ItemPipe extends AbstractPipe{
    public ItemPipe(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSamePipe(BlockState state) {
        return state.getBlock() instanceof ItemPipe;
    }

    @Override
    public ItemPipeNetSaveData getPipeNetSaveData(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(ItemPipeNetSaveData.ITEM_PIPE_NETS);
    }

    @Override
    public boolean hasCapability(Level level, BlockPos pos, Direction direction) {
        return level.getCapability(Capabilities.Item.BLOCK, pos, direction) != null;
    }

    @Override
    public void dealCapabilityChange(ServerLevel level, BlockPos pos, Direction direction, LinkState newState) {
        ItemPipeNetSaveData data = getPipeNetSaveData(level);
        switch (newState) {
            case LINK -> data.addInsert(level, pos, direction);
            case EXTRACT -> {
                data.removeInsert(level, pos, direction);
                data.addExtract(level, pos, direction);
            }
            default -> data.removeExtract(level, pos, direction);
        }
    }
}
