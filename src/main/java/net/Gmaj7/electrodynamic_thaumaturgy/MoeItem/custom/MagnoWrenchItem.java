package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.MoeBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlock.EnergyTransmissionAtenna;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EnergyTransmissionAntennaBE;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoeDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class MagnoWrenchItem extends Item {
    public MagnoWrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = context.getLevel().getBlockState(blockPos);
        Player player = context.getPlayer();
        if(blockState.is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA)) {
            if (!itemStack.has(MoeDataComponentTypes.LINK_POS) || !context.getLevel().getBlockState(itemStack.get(MoeDataComponentTypes.LINK_POS)).is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA)) {
                itemStack.set(MoeDataComponentTypes.LINK_POS, blockPos);
                player.swing(context.getHand());
                return InteractionResult.SUCCESS;
            }
            else {
                BlockPos targetPos = itemStack.get(MoeDataComponentTypes.LINK_POS);
                if(targetPos == blockPos) {
                    itemStack.remove(MoeDataComponentTypes.LINK_POS);
                    player.swing(context.getHand());
                }
                else {
                    BlockState targetState = context.getLevel().getBlockState(targetPos);
                    if(targetState.is(MoeBlocks.ENERGY_TRANSMISSION_ANTENNA)){
                        if(blockState.getValue(EnergyTransmissionAtenna.SEND) && !targetState.getValue(EnergyTransmissionAtenna.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(blockPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(targetPos);
                            itemStack.remove(MoeDataComponentTypes.LINK_POS);
                            player.swing(context.getHand());
                            return InteractionResult.SUCCESS;
                        }
                        else if (!blockState.getValue(EnergyTransmissionAtenna.SEND) && targetState.getValue(EnergyTransmissionAtenna.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(targetPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(blockPos);
                            itemStack.remove(MoeDataComponentTypes.LINK_POS);
                            player.swing(context.getHand());
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if(stack.has(MoeDataComponentTypes.LINK_POS)){
            BlockPos blockPos = stack.get(MoeDataComponentTypes.LINK_POS);
            builder.accept(Component.translatable("binding").append(blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ()));
        }
    }
}
