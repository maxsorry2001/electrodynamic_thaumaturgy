package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.EnergyPipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;

public class EnergyPipe extends AbstractPipe {
    public EnergyPipe(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSamePipe(BlockState state) {
        return state.getBlock() instanceof EnergyPipe;
    }

    @Override
    public boolean hasCapability(Level level, BlockPos pos, Direction direction) {
        return level.getCapability(Capabilities.Energy.BLOCK, pos, direction) != null;
    }

    @Override
    public void dealCapabilityChange(ServerLevel level, BlockPos pos, Direction direction, LinkState newState) {
        EnergyPipeNetSaveData data = getPipeNetSaveData(level);
        switch (newState) {
            case LINK -> data.addInsert(level, pos, direction);
            case EXTRACT -> {
                data.removeInsert(level, pos, direction);
                data.addExtract(level, pos, direction);
            }
            default -> data.removeExtract(level, pos, direction);
        }
    }

    @Override
    public EnergyPipeNetSaveData getPipeNetSaveData(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(EnergyPipeNetSaveData.ENERGY_PIPE_NETS);
    }
}
