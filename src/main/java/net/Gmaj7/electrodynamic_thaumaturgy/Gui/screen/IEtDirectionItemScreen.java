package net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

import java.util.Map;

public interface IEtDirectionItemScreen {

    default void renderIcon(GuiGraphicsExtractor guiGraphics, Map<Direction, Boolean> itemSet, int mouseX, int mouseY, int left, int top){
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.UP), isMouseFocusedSetting(Direction.UP, mouseX, mouseY, left, top)), left + 100, top + 50, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.DOWN), isMouseFocusedSetting(Direction.DOWN, mouseX, mouseY, left, top)), left + 100, top + 64, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.EAST), isMouseFocusedSetting(Direction.EAST, mouseX, mouseY, left, top)), left + 100, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.NORTH), isMouseFocusedSetting(Direction.NORTH, mouseX, mouseY, left, top)), left + 107, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.WEST), isMouseFocusedSetting(Direction.WEST, mouseX, mouseY, left, top)), left + 114, top + 57, 5, 5);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.SOUTH), isMouseFocusedSetting(Direction.SOUTH, mouseX, mouseY, left, top)), left + 121, top + 57, 5, 5);
    }

    Identifier getSprites(boolean input, boolean isFocused);

    default boolean isMouseFocusedSetting(Direction direction, double mouseX, double mouseY, int left, int top){
        double d0, d1;
        switch (direction){
            case UP -> {
                d0 = mouseX - left - 100;
                d1 = mouseY - top - 50;
            }
            case DOWN -> {
                d0 = mouseX - left - 100;
                d1 = mouseY - top - 64;
            }
            case EAST -> {
                d0 = mouseX - left - 100;
                d1 = mouseY - top - 57;
            }
            case NORTH -> {
                d0 = mouseX - left - 107;
                d1 = mouseY - top - 57;
            }
            case WEST -> {
                d0 = mouseX - left - 114;
                d1 = mouseY - top - 57;
            }
            case SOUTH -> {
                d0 = mouseX - left - 121;
                d1 = mouseY - top - 57;
            }
            default -> {d0 = 0; d1 = 0;}
        }
        return d0 > 0 && d0 < 5 && d1 > 0 && d1 < 5;
    }

    default Direction getFocusedDirection(double mouseX, double mouseY, int left, int top){
        double dx = mouseX - left, dy = mouseY - top;
        Direction direction = null;
        if(dx > 100 && dx < 105){
            if(dy > 50 && dy < 55) direction = Direction.UP;
            else if(dy > 57 && dy < 62) direction = Direction.EAST;
            else if(dy > 64 && dy < 69) direction = Direction.DOWN;
        }
        else if(dy > 57 && dy < 62){
            if(dx > 107 && dx < 112) direction = Direction.NORTH;
            else if(dx > 114 && dx < 119) direction = Direction.WEST;
            else if (dx > 121 && dx < 126) direction = Direction.SOUTH;
        }
        return direction;
    }
}
