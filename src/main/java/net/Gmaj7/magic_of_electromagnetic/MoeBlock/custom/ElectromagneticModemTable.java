package net.Gmaj7.magic_of_electromagnetic.MoeBlock.custom;

import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeAssemblyTableMenu;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeModemTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ElectromagneticModemTable extends Block {
    public ElectromagneticModemTable(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((p1, p2, p3) -> {
            return new MoeModemTableMenu(p1, p2, ContainerLevelAccess.create(level, pos));
        }, Component.translatable("block.magic_of_electromagnetic.electromagnetic_modem_table"));
    }
}
