package net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlock;

import com.mojang.serialization.MapCodec;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.EtBlockEntities;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.FluidBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.FluidSetPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

public class FluidBlock extends BaseEntityBlock {
    public static final MapCodec<FluidBlock> CODEC = simpleCodec(FluidBlock::new);
    public FluidBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidBlockEntity(blockPos, blockState);
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
            if (blockEntity instanceof FluidBlockEntity fluidBlockEntity && !level.isClientSide()) {
                ResourceHandler<FluidResource> fluidHandler = fluidBlockEntity.getFluidHandler();
                PacketDistributor.sendToAllPlayers(new FluidSetPacket(fluidHandler.getResource(0).toStack(fluidHandler.getAmountAsInt(0)), fluidBlockEntity.getBlockPos()));
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(fluidBlockEntity, Component.translatable("block.electrodynamic_thaumaturgy.energy_block")), pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ResourceHandler<FluidResource> blockHandler = level.getCapability(Capabilities.Fluid.BLOCK, pos, hitResult.getDirection()),
                itemHandler = itemStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forPlayerInteraction(player, hand));
        if(blockHandler == null || itemHandler == null) return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
        FluidResource itemResource = itemHandler.getResource(0), blockResource = blockHandler.getResource(0);
        if(itemResource.isEmpty() && !blockResource.isEmpty()){
            int amount;
            if(blockResource.isEmpty()) return InteractionResult.CONSUME;
            try(Transaction transactionOut = Transaction.openRoot()) {
                amount = Math.min(itemHandler.insert(blockResource, 1000, transactionOut), blockHandler.extract(blockResource, 1000, transactionOut));
            }
            if(amount <= 0) return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
            try (Transaction transaction = Transaction.openRoot()){
                itemHandler.insert(blockResource, amount, transaction);
                blockHandler.extract(blockResource, amount, transaction);
                transaction.commit();
                playBucketFillSound(level, pos, player, blockResource);
            }
        }
        else if(!itemResource.isEmpty() && (blockResource.isEmpty() || blockResource.is(itemResource.getFluidType()))){
            int amount;
            if(itemResource.isEmpty()) return InteractionResult.CONSUME;
            try(Transaction transactionIn = Transaction.openRoot()) {
                amount = Math.min(blockHandler.insert(itemResource, 1000, transactionIn), itemHandler.extract(itemResource, 1000, transactionIn));
            }
            if(amount <= 0) return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
            try (Transaction transaction = Transaction.openRoot()){
                blockHandler.insert(itemResource, amount, transaction);
                itemHandler.extract(itemResource, amount, transaction);
                transaction.commit();
                playBucketEmptySound(level, pos, player, itemResource);
            }
        }
        player.swing(hand);
        return InteractionResult.SUCCESS;
    }

    private void playBucketFillSound(Level level, BlockPos pos, Player player, FluidResource fluid) {
        FluidType fluidType = fluid.getFluidType();
        SoundEvent sound = fluidType.getSound(player, level, pos, SoundActions.BUCKET_FILL);
        if (sound == null) {
            sound = SoundEvents.BUCKET_FILL; // 默认填充音效
        }
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private void playBucketEmptySound(Level level, BlockPos pos, Player player, FluidResource fluid) {
        FluidType fluidType = fluid.getFluidType();
        SoundEvent sound = fluidType.getSound(player, level, pos, SoundActions.BUCKET_EMPTY);
        if (sound == null) {
            // 根据流体是否为岩浆选择不同的默认音效
            if (fluid.is(FluidTags.LAVA)) {
                sound = SoundEvents.BUCKET_EMPTY_LAVA;
            } else {
                sound = SoundEvents.BUCKET_EMPTY;
            }
        }
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return blockEntityType == EtBlockEntities.FLUID_BLOCK_BE.get() ? createTickerHelper(blockEntityType, EtBlockEntities.FLUID_BLOCK_BE.get(), FluidBlockEntity::tick) : null;
    }
}
