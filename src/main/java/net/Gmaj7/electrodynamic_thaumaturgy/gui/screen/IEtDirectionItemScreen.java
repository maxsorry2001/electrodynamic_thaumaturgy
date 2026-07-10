package net.Gmaj7.electrodynamic_thaumaturgy.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import java.util.Map;

public interface IEtDirectionItemScreen {

    default void renderItemIcon(GuiGraphicsExtractor guiGraphics, Map<Direction, Boolean> itemSet, int mouseX, int mouseY, int left, int top){
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.UP), isMouseFocusedItemSetting(Direction.UP, mouseX, mouseY, left, top)), left + 153, top + 50, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.DOWN), isMouseFocusedItemSetting(Direction.DOWN, mouseX, mouseY, left, top)), left + 153, top + 64, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.EAST), isMouseFocusedItemSetting(Direction.EAST, mouseX, mouseY, left, top)), left + 146, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.NORTH), isMouseFocusedItemSetting(Direction.NORTH, mouseX, mouseY, left, top)), left + 153, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.WEST), isMouseFocusedItemSetting(Direction.WEST, mouseX, mouseY, left, top)), left + 160, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getItemOutputSprites(itemSet.get(Direction.SOUTH), isMouseFocusedItemSetting(Direction.SOUTH, mouseX, mouseY, left, top)), left + 160, top + 64, 5, 5);
    }

    Identifier getItemOutputSprites(boolean input, boolean isFocused);

    default boolean isMouseFocusedItemSetting(Direction direction, double mouseX, double mouseY, int left, int top){
        double d0, d1;
        switch (direction){
            case UP -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 50;
            }
            case DOWN -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 64;
            }
            case EAST -> {
                d0 = mouseX - left - 146;
                d1 = mouseY - top - 57;
            }
            case NORTH -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 57;
            }
            case WEST -> {
                d0 = mouseX - left - 160;
                d1 = mouseY - top - 57;
            }
            case SOUTH -> {
                d0 = mouseX - left - 160;
                d1 = mouseY - top - 64;
            }
            default -> {d0 = 0; d1 = 0;}
        }
        return d0 > 0 && d0 < 5 && d1 > 0 && d1 < 5;
    }

    default Direction getItemFocusedDirection(double mouseX, double mouseY, int left, int top) {
        int relX = (int) (mouseX - left);
        int relY = (int) (mouseY - top);

        // 检查 EAST 按钮 (146,57) 5x5
        if (relX >= 146 && relX < 151 && relY >= 57 && relY < 62) {
            return Direction.EAST;
        }

        // 检查 UP (153,50), NORTH (153,57), DOWN (153,64) 三个按钮
        if (relX >= 153 && relX < 158) {
            if (relY >= 50 && relY < 55) {
                return Direction.UP;
            }
            if (relY >= 57 && relY < 62) {
                return Direction.NORTH;
            }
            if (relY >= 64 && relY < 69) {
                return Direction.DOWN;
            }
        }

        // 检查 WEST (160,57) 和 SOUTH (160,64) 两个按钮
        if (relX >= 160 && relX < 165) {
            if (relY >= 57 && relY < 62) {
                return Direction.WEST;
            }
            if (relY >= 64 && relY < 69) {
                return Direction.SOUTH;
            }
        }

        return null;
    }
}
