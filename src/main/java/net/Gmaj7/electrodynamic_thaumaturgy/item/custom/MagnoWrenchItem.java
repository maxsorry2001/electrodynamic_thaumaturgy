package net.Gmaj7.electrodynamic_thaumaturgy.item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.block.EtBlocks;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlock.EnergyTransmissionAtenna;
import net.Gmaj7.electrodynamic_thaumaturgy.block.customBlockEntity.EnergyTransmissionAntennaBE;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
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
        if(blockState.is(EtBlocks.ENERGY_TRANSMISSION_ANTENNA)) {
            if (!itemStack.has(EtDataComponentTypes.LINK_POS) || !context.getLevel().getBlockState(itemStack.get(EtDataComponentTypes.LINK_POS)).is(EtBlocks.ENERGY_TRANSMISSION_ANTENNA)) {
                itemStack.set(EtDataComponentTypes.LINK_POS, blockPos);
                player.swing(context.getHand());
                return InteractionResult.SUCCESS;
            }
            else {
                BlockPos targetPos = itemStack.get(EtDataComponentTypes.LINK_POS);
                if(targetPos == blockPos) {
                    itemStack.remove(EtDataComponentTypes.LINK_POS);
                    player.swing(context.getHand());
                }
                else {
                    BlockState targetState = context.getLevel().getBlockState(targetPos);
                    if(targetState.is(EtBlocks.ENERGY_TRANSMISSION_ANTENNA)){
                        if(blockState.getValue(EnergyTransmissionAtenna.SEND) && !targetState.getValue(EnergyTransmissionAtenna.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(blockPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(targetPos);
                            itemStack.remove(EtDataComponentTypes.LINK_POS);
                            player.swing(context.getHand());
                            return InteractionResult.SUCCESS;
                        }
                        else if (!blockState.getValue(EnergyTransmissionAtenna.SEND) && targetState.getValue(EnergyTransmissionAtenna.SEND)){
                            BlockEntity blockEntity = context.getLevel().getBlockEntity(targetPos);
                            ((EnergyTransmissionAntennaBE)blockEntity).getReceivePos().add(blockPos);
                            itemStack.remove(EtDataComponentTypes.LINK_POS);
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
        if(stack.has(EtDataComponentTypes.LINK_POS)){
            BlockPos blockPos = stack.get(EtDataComponentTypes.LINK_POS);
            builder.accept(Component.translatable("binding").append(blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ()));
        }
    }
}
