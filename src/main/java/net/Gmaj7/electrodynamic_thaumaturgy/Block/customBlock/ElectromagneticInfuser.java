package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.ElectromagneticInfuserBE;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jspecify.annotations.Nullable;

public class ElectromagneticInfuser extends BaseEntityBlock {
    public static final MapCodec<ElectromagneticInfuser> CODEC = simpleCodec(ElectromagneticInfuser::new);
    public ElectromagneticInfuser(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return type == EtBlockEntities.ELECTROMAGNETIC_INFUSER_BE.get() ? createTickerHelper(type, EtBlockEntities.ELECTROMAGNETIC_INFUSER_BE.get(), ElectromagneticInfuserBE::tick) : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ElectromagneticInfuserBE electromagneticInfuserBE && !level.isClientSide()) {
                EnergyHandler energyStorage = electromagneticInfuserBE.getEnergy();
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(energyStorage.getAmountAsInt(), electromagneticInfuserBE.getBlockPos()));
                if(player.isShiftKeyDown()) electromagneticInfuserBE.changeDirectionSet(hitResult.getDirection());
                else ((ServerPlayer) player).openMenu(new SimpleMenuProvider(electromagneticInfuserBE, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ElectromagneticInfuserBE(blockPos, blockState);
    }
}
