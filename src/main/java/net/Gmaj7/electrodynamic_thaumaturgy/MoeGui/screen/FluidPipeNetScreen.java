package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.FluidPipeNetMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.FluidPipeNetFilterPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePackets.ItemPipeNetFilterPacket;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class FluidPipeNetScreen extends PipeNetScreen<FluidPipeNetMenu> {
    public FluidPipeNetScreen(FluidPipeNetMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void drawDirectionIcons(GuiGraphicsExtractor guiGraphics, int mouthX, int mouthY, int left, int top){
        List<Direction> directions = getCurrentDirections();
        int total = directions.size();
        if (total == 0) return;

        // 限制 startIndex 范围
        int maxStart = getMaxStartIndex();
        if (startIndex > maxStart) startIndex = maxStart;
        if (startIndex < 0) startIndex = 0;

        int startX = left + 31;
        int startY = top + 15; // 第一个图标的Y坐标

        for (int i = 0; i < VISIBLE_ROWS; i++) {
            int idx = startIndex + i;
            if (idx >= total) break;
            Direction dir = directions.get(idx);
            BlockPos nodePos = getCurrentPos(), adjacentPos = nodePos.relative(dir);
            BlockState blockState = menu.getLevel().getBlockState(adjacentPos);
            guiGraphics.fakeItem(new ItemStack(blockState.getBlock()), startX, startY + i * ITEM_SIZE);
            if(!isInsert)
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, i == getChangeIndex(mouthX, mouthY) ? getOn(nodePos, dir) : getNormal(nodePos, dir), startX + 17, startY + i * ITEM_SIZE - 1, 18, 18);
            if(!menu.getFilter().containsKey(nodePos) || ! menu.getFilter().get(nodePos).containsKey(dir)) continue;
            List<ItemStack> filter = menu.getFilterItemOfPosAndDir(nodePos, dir);
            if(filter.isEmpty()) continue;
            for (int j = 0; j < filter.size(); j++)
                guiGraphics.fakeItem(filter.get(j), startX + (j + 2) * ITEM_SIZE, startY + i * ITEM_SIZE);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int left = (width - imageWidth) / 2, top = (height - imageHeight) / 2, slot = getSlot(event.x(), event.y(), left, top);
        if(slot != -1){
            ItemStack stack = this.menu.getCarried();
            BlockPos pos = getCurrentPos();
            List<Direction> directions = getCurrentDirections();
            int idx = startIndex + getIndex(event.y());
            if (idx < directions.size()) {
                Direction dir = directions.get(idx);
                ClientPacketDistributor.sendToServer(new FluidPipeNetFilterPacket(pos, dir, slot, stack, menu.getNetId()));
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    protected int getSlot(double mouthX, double mouthY, int left, int top){
        int slot = -1, j = getIndex(mouthY);
        if(j == -1) return slot;
        int startX = left + 31 + ITEM_SIZE * 2, startY = top + 15 + j * ITEM_SIZE;
        if(mouthY < startY || mouthY > startY + ITEM_SIZE || mouthX < startX || mouthX > startX + ITEM_SIZE * 3) return slot;
        if(mouthX < startX + ITEM_SIZE) slot = 0;
        else if (mouthX < startX + ITEM_SIZE * 2) slot = 1;
        else slot = 2;
        return slot;
    }
}
