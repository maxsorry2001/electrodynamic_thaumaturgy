package net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.FilterSettingMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.FilterSettingItemPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.FilterSettingWhitePacket;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

public class FilterSettingScreen extends AbstractContainerScreen<FilterSettingMenu> {

    private static final Identifier BACKGROUND_WHITE = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/filter_setting_white.png");
    private static final Identifier BACKGROUND_BLACK = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/filter_setting_black.png");
    private static final int ITEM_SIZE = 18;
    private static final int COLS = 6;
    private static final int ROWS = 3;
    private static final int START_X_OFFSET = 31;
    private static final int START_Y_OFFSET = 15;

    public FilterSettingScreen(FilterSettingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        // 绘制模糊背景
        this.extractBlurredBackground(guiGraphics);
        int left = (width - imageWidth) / 2;
        int top = (height - imageHeight) / 2;

        // 绘制主背景图
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, menu.isWhite() ? BACKGROUND_WHITE : BACKGROUND_BLACK, left, top, 0, 0, imageWidth, imageHeight, 256, 256);

        // 绘制过滤物品网格
        drawFilterItems(guiGraphics, left, top);
    }

    private void drawFilterItems(GuiGraphicsExtractor guiGraphics, int left, int top) {
        List<ItemStack> filterItems = menu.getFilterList();  // 获取过滤物品列表
        int startX = left + START_X_OFFSET;
        int startY = top + START_Y_OFFSET;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int index = row * COLS + col;
                if (index < filterItems.size()) {
                    ItemStack stack = filterItems.get(index);
                    if (!stack.isEmpty()) {
                        int x = startX + col * ITEM_SIZE;
                        int y = startY + row * ITEM_SIZE;
                        guiGraphics.fakeItem(stack, x, y);
                    }
                }
                // 如果索引超出列表范围，则留空（不绘制任何东西）
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        // 可添加工具提示（后续交互时实现）
    }

    protected int getSlotIndex(double mouseX, double mouseY) {
        int left = (width - imageWidth) / 2;
        int top = (height - imageHeight) / 2;
        int startX = left + START_X_OFFSET;
        int startY = top + START_Y_OFFSET;

        // 检查是否在网格区域内
        if (mouseX < startX || mouseX > startX + COLS * ITEM_SIZE) return -1;
        if (mouseY < startY || mouseY > startY + ROWS * ITEM_SIZE) return -1;

        int col = (int) ((mouseX - startX) / ITEM_SIZE);
        int row = (int) ((mouseY - startY) / ITEM_SIZE);
        // 防止边界溢出（理论上已在区域内，但防御一下）
        if (col < 0 || col >= COLS) return -1;
        if (row < 0 || row >= ROWS) return -1;

        return row * COLS + col;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int left = (width - imageWidth) / 2;
        int top = (height - imageHeight) / 2;
        if(event.button() == 0){
            if(isOnChange(event.x(), event.y(), left, top)){
                ClientPacketDistributor.sendToServer(new FilterSettingWhitePacket());
                boolean flag = menu.isWhite();
                menu.getFilter().set(EtDataComponentTypes.FILTER_WHITE, !flag);
            }
            int slot = getSlotIndex(event.x(), event.y());
            if(slot != -1){
                ItemStack itemStack = menu.getCarried();
                ClientPacketDistributor.sendToServer(new FilterSettingItemPacket(slot, itemStack));
                List<ItemStack> list = menu.getFilterList();
                if(itemStack.isEmpty() && slot < list.size()){
                    list.remove(slot);
                }
                else {
                    if (slot >= list.size()) list.add(itemStack);
                    else list.set(slot, itemStack);
                }
                ItemContainerContents contents = ItemContainerContents.fromItems(list);
                menu.getFilter().set(EtDataComponentTypes.ET_CONTAINER, contents);
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    protected boolean isOnChange(double mouthX, double mouthY, int left, int top){
        int lx = !menu.isWhite() ? left + 31 : left + 81, ly = top + 13, height = 11;
        if(mouthY > ly || mouthY < ly - height) return false;
        int dy = ly - (int) mouthY, leftPx = lx + dy, rightPx = lx + 61 - dy;
        if(dy < 6){
            if(!menu.isWhite()) rightPx = rightPx - 11 + 2 * dy;
            else leftPx = leftPx + 11 - 2 * dy;
        }
        return mouthX >= leftPx & mouthX <= rightPx;
    }
}