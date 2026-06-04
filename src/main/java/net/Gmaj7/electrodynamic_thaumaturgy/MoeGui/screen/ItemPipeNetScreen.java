package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.ItemPipeNetMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.PipeNetMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ItemPipeNetScreen extends PipeNetScreen<ItemPipeNetMenu> {
    public ItemPipeNetScreen(ItemPipeNetMenu menu, Inventory inventory, Component title) {
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
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, i == getIndex(mouthX, mouthY) ? getOn(nodePos, dir) : getNormal(nodePos, dir), startX + 17, startY + i * ITEM_SIZE - 1, 18, 18);
            if(!menu.getFilters().containsKey(nodePos) || ! menu.getFilters().get(nodePos).containsKey(dir)) continue;
            List<ItemStack> filter = menu.getFilters().get(nodePos).get(dir);
            if(filter.isEmpty()) continue;
            for (int j = 0; j < filter.size(); j++)
                guiGraphics.fakeItem(filter.get(j), startX + (j + 2) * ITEM_SIZE, startY + i * ITEM_SIZE);
        }
    }
}
