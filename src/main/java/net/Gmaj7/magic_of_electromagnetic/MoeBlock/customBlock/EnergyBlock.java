package net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.magic_of_electromagnetic.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.BatteryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyBlock extends BaseEntityBlock {
    public static final MapCodec<EnergyBlock> CODEC = simpleCodec(EnergyBlock::new);
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    public EnergyBlock(Properties properties) {
        super(properties);
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EnergyBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof EnergyBlockEntity energyBlockEntity){
            IEnergyStorage energyStorage = energyBlockEntity.getEnergy();
            player.sendSystemMessage(Component.literal(String.valueOf(energyStorage.getEnergyStored())));
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IEnergyStorage energyStorage =stack.getCapability(Capabilities.EnergyStorage.ITEM);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(energyStorage != null && blockEntity instanceof EnergyBlockEntity energyBlockEntity){
            IEnergyStorage blockStorage = energyBlockEntity.getEnergy();
            int change = 2048;
            if(stack.getItem() instanceof BatteryItem && energyStorage.getEnergyStored() > 0) {
                if (blockStorage.getEnergyStored() < blockStorage.getMaxEnergyStored()) {
                    if (blockStorage.getMaxEnergyStored() - blockStorage.getEnergyStored() < change) {
                        blockStorage.receiveEnergy(blockStorage.getMaxEnergyStored() - blockStorage.getEnergyStored(), false);
                        energyStorage.extractEnergy(blockStorage.getMaxEnergyStored() - blockStorage.getEnergyStored(), false);
                    }
                    else {
                        blockStorage.receiveEnergy(change, false);
                        energyStorage.extractEnergy(change, false);
                    }
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
