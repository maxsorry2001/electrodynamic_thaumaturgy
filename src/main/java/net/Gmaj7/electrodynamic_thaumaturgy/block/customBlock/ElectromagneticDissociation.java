package net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.ElectromagneticDissociationBE;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.EnergySetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import org.jspecify.annotations.Nullable;

public class ElectromagneticDissociation extends BaseEntityBlock {
    public static final MapCodec<ElectromagneticDissociation> CODEC = simpleCodec(ElectromagneticDissociation::new);
    public ElectromagneticDissociation(Properties properties) {
        super(properties);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return type == EtBlockEntities.ELECTROMAGNETIC_DISSOCIATION_BE.get() ? createTickerHelper(type, EtBlockEntities.ELECTROMAGNETIC_DISSOCIATION_BE.get(), ElectromagneticDissociationBE::tick) : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ElectromagneticDissociationBE electromagneticDissociationBE && !level.isClientSide()) {
                EnergyHandler energyStorage = electromagneticDissociationBE.getEnergy();
                PacketDistributor.sendToAllPlayers(new EnergySetPacket(energyStorage.getAmountAsInt(), electromagneticDissociationBE.getBlockPos()));
                if(player.isShiftKeyDown()) electromagneticDissociationBE.changeItemDirectionSet(hitResult.getDirection());
                else ((ServerPlayer) player).openMenu(new SimpleMenuProvider(electromagneticDissociationBE, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
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
        return new ElectromagneticDissociationBE(blockPos, blockState);
    }
}
