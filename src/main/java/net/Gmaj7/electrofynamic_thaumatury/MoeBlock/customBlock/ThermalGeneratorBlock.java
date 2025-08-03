package net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.MoeBlockEntities;
import net.Gmaj7.electrofynamic_thaumatury.MoeBlock.customBlockEntity.ThermalGeneratorBE;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class ThermalGeneratorBlock extends AbstractEnergyMakerBlock {
    public static final MapCodec<ThermalGeneratorBlock> CODEC = simpleCodec(ThermalGeneratorBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public ThermalGeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ThermalGeneratorBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.isClientSide())
            return InteractionResult.SUCCESS;
        else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ThermalGeneratorBE thermalEnergyMakerBE && !level.isClientSide()) {
                IEnergyStorage energyStorage = thermalEnergyMakerBE.getEnergy();
                PacketDistributor.sendToAllPlayers(new MoePacket.EnergySetPacket(energyStorage.getEnergyStored(), thermalEnergyMakerBE.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(thermalEnergyMakerBE, Component.translatable("block.electrofynamic_thaumatury.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == MoeBlockEntities.THERMAL_GENERATOR_BE.get() ? createTickerHelper(blockEntityType, MoeBlockEntities.THERMAL_GENERATOR_BE.get(), ThermalGeneratorBE::tick) : null;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof ThermalGeneratorBE blockEntity) {
                blockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }
}
