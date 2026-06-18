package net.Gmaj7.electrodynamic_thaumaturgy.Item.custom;

import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.EnergyBlockEntity;
import net.Gmaj7.electrodynamic_thaumaturgy.Block.customBlockEntity.FluidBlockEntity;
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
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

import java.util.function.Consumer;

public class FluidBlockItem extends BlockItem {
    public FluidBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        boolean flag = context.getLevel().setBlockAndUpdate(context.getClickedPos(), state);
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        if(blockEntity instanceof FluidBlockEntity && flag){
            ItemStack itemStack = context.getItemInHand();
            ResourceHandler<FluidResource> resourceHandler = itemStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(itemStack));
            ((FluidBlockEntity) blockEntity).setFluid(resourceHandler.getResource(0).toStack(resourceHandler.getAmountAsInt(0)));
        }
        return flag;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        ResourceHandler<FluidResource> fluidHandler = stack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(stack));
        int i = fluidHandler.getAmountAsInt(0);
        int stackMaxEnergy = fluidHandler.getCapacityAsInt(0, fluidHandler.getResource(0));
        return Math.round(13.0F - (stackMaxEnergy - i) * 13.0F / stackMaxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        ResourceHandler<FluidResource> fluidHandler = stack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(stack));
        int i = fluidHandler.getAmountAsInt(0);
        int stackMaxEnergy = fluidHandler.getCapacityAsInt(0, fluidHandler.getResource(0));
        float f = Math.max(0.0F, (float) i / stackMaxEnergy);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        ResourceHandler<FluidResource> fluidHandler = itemStack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(itemStack));
        int i = fluidHandler.getAmountAsInt(0),j = fluidHandler.getCapacityAsInt(0, fluidHandler.getResource(0));
        builder.accept(Component.translatable("moe_show_energy").append(i + " B / " + j + " B"));
    }
}
