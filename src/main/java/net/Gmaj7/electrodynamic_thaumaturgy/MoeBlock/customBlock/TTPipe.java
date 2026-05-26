package net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePipeNet.PipeNetSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashSet;

public class TTPipe extends Block {
    public TTPipe(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide()) return InteractionResult.CONSUME;
        PipeNetSaveData a = ((ServerLevel)level).getDataStorage().computeIfAbsent(PipeNetSaveData.PIPE_NETS);
        if(player.isShiftKeyDown()){
            a.createNet().addPos(pos, new HashSet<>());
            int i = 1;
        }
        return InteractionResult.PASS;
    }
}
