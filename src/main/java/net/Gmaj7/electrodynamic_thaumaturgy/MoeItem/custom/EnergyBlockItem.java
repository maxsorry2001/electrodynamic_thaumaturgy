package net.Gmaj7.electrodynamic_thaumaturgy.MoeItem.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeBlock.customBlockEntity.EnergyBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

import java.util.function.Consumer;

public class EnergyBlockItem extends BlockItem {
    public EnergyBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        boolean flag = context.getLevel().setBlockAndUpdate(context.getClickedPos(), state);
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        if(blockEntity instanceof EnergyBlockEntity && flag){
            ItemStack itemStack = context.getItemInHand();
            ((EnergyBlockEntity) blockEntity).setEnergy(itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack)).getAmountAsInt());
        }
        return flag;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getAmountAsInt();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getCapacityAsInt();
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        int i = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getAmountAsInt();
        int stackMaxEnergy = stack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(stack)).getCapacityAsInt();
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        EnergyHandler energyStorage = itemStack.getCapability(Capabilities.Energy.ITEM, ItemAccess.forStack(itemStack));
        int i = energyStorage.getAmountAsInt(),j = energyStorage.getCapacityAsInt();
        builder.accept(Component.translatable("moe_show_energy").append(i + " FE / " + j + " FE"));
    }
}
