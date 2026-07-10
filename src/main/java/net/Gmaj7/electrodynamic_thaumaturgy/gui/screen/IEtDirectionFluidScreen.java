package net.Gmaj7.electrodynamic_thaumaturgy.gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import java.util.Map;

public interface IEtDirectionFluidScreen extends IEtFluidScreen{

    default void renderFluidIcon(GuiGraphicsExtractor guiGraphics, Map<Direction, Boolean> itemSet, int mouseX, int mouseY, int left, int top){
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.UP), isMouseFocusedFluidSetting(Direction.UP, mouseX, mouseY, left, top)), left + 153, top + 20, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.DOWN), isMouseFocusedFluidSetting(Direction.DOWN, mouseX, mouseY, left, top)), left + 153, top + 34, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.EAST), isMouseFocusedFluidSetting(Direction.EAST, mouseX, mouseY, left, top)), left + 146, top + 27, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.NORTH), isMouseFocusedFluidSetting(Direction.NORTH, mouseX, mouseY, left, top)), left + 153, top + 27, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.WEST), isMouseFocusedFluidSetting(Direction.WEST, mouseX, mouseY, left, top)), left + 160, top + 27, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidOutputSprites(itemSet.get(Direction.SOUTH), isMouseFocusedFluidSetting(Direction.SOUTH, mouseX, mouseY, left, top)), left + 160, top + 34, 5, 5);
    }

    Identifier getFluidOutputSprites(boolean input, boolean isFocused);

    default boolean isMouseFocusedFluidSetting(Direction direction, double mouseX, double mouseY, int left, int top){
        double d0, d1;
        switch (direction){
            case UP -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 20;
            }
            case DOWN -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 34;
            }
            case EAST -> {
                d0 = mouseX - left - 146;
                d1 = mouseY - top - 27;
            }
            case NORTH -> {
                d0 = mouseX - left - 153;
                d1 = mouseY - top - 27;
            }
            case WEST -> {
                d0 = mouseX - left - 160;
                d1 = mouseY - top - 27;
            }
            case SOUTH -> {
                d0 = mouseX - left - 160;
                d1 = mouseY - top - 34;
            }
            default -> {d0 = 0; d1 = 0;}
        }
        return d0 > 0 && d0 < 5 && d1 > 0 && d1 < 5;
    }

    default Direction getFluidFocusedDirection(double mouseX, double mouseY, int left, int top){
        int relX = (int) (mouseX - left);
        int relY = (int) (mouseY - top);

        // 检查 EAST 按钮 (146,27) 5x5
        if (relX >= 146 && relX < 151 && relY >= 27 && relY < 32) {
            return Direction.EAST;
        }

        // 检查 UP (153,20), NORTH (153,27), DOWN (153,34) 三个按钮
        if (relX >= 153 && relX < 158) {
            if (relY >= 20 && relY < 25) {
                return Direction.UP;
            }
            if (relY >= 27 && relY < 32) {
                return Direction.NORTH;
            }
            if (relY >= 34 && relY < 39) {
                return Direction.DOWN;
            }
        }

        // 检查 WEST (160,27) 和 SOUTH (160,34) 两个按钮
        if (relX >= 160 && relX < 165) {
            if (relY >= 27 && relY < 32) {
                return Direction.WEST;
            }
            if (relY >= 34 && relY < 39) {
                return Direction.SOUTH;
            }
        }

        return null;
    }
}