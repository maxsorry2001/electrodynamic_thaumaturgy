package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.PipeNetMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeInit.MoePacket;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class PipeNetScreen extends AbstractContainerScreen<PipeNetMenu> {
    private static final Identifier EXTRACT_GRAND = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/net_gui_extract.png");
    private static final Identifier INSERT_GRAND = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/net_gui_insert.png");
    private static final Identifier NEAREST = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/nearest");
    private static final Identifier NEAREST_ON = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/nearest_on");
    private static final Identifier FARTHEST = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/farthest");
    private static final Identifier FARTHEST_ON = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/farthest_on");
    private static final Identifier POLLING = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/polling");
    private static final Identifier POLLING_ON = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "container/pipe_net/polling_on");
    private static final Identifier SCROLLER_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.withDefaultNamespace("container/stonecutter/scroller_disabled");

    // 显示区域配置：每页最多显示 3 个方向
    private static final int VISIBLE_ROWS = 3;
    private static final int ITEM_SIZE = 18; // 每个图标高度
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int SCROLLER_AREA_HEIGHT = VISIBLE_ROWS * ITEM_SIZE; // 54

    // 滚动相关
    private float scrollOffs = 0.0F;
    private boolean scrolling = false;
    private int startIndex = 0; // 当前显示的第一个方向在列表中的索引

    private int posSelect = 0;
    private boolean isInsert = false; // false=extract, true=insert

    public PipeNetScreen(PipeNetMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelY = 10000;
        super.init();
        // 切换节点或模式时重置滚动
        resetScroll();
    }

    private void resetScroll() {
        scrollOffs = 0.0F;
        startIndex = 0;
        scrolling = false;
    }

    private List<Direction> getCurrentDirections() {
        if (isInsert) {
            List<BlockPos> poses = new ArrayList<>(menu.getInsert().keySet());
            if (poses.isEmpty()) return List.of();
            BlockPos pos = poses.get(posSelect % poses.size());
            return new ArrayList<>(menu.getInsert().get(pos));
        } else {
            List<BlockPos> poses = new ArrayList<>(menu.getExtract().keySet());
            if (poses.isEmpty()) return List.of();
            BlockPos pos = poses.get(posSelect % poses.size());
            return new ArrayList<>(menu.getExtract().get(pos).keySet());
        }
    }

    private int getTotalDirections() {
        return getCurrentDirections().size();
    }

    private boolean isScrollBarActive() {
        return getTotalDirections() > VISIBLE_ROWS;
    }

    private int getMaxStartIndex() {
        int total = getTotalDirections();
        return total - VISIBLE_ROWS;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;

        // 绘制滚动条
        drawScrollBar(guiGraphics, mouseX, mouseY, x, y);

        // 绘制节点信息（序号、类型、坐标）
        drawNodeInfo(guiGraphics, mouseX, mouseY, x, y);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, isInsert ? INSERT_GRAND : EXTRACT_GRAND, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        this.extractBlurredBackground(guiGraphics);
    }

    private void drawScrollBar(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int left, int top) {
        if (!isScrollBarActive()) return;

        int scrollerX = left + 129;      // 滚动条X坐标（可根据实际背景调整）
        int scrollerY = top + 14;        // 滚动条区域起始Y
        int sliderOffset = (int)(scrollOffs * (SCROLLER_AREA_HEIGHT - SCROLLER_HEIGHT));
        Identifier sprite = isOnScroller(mouseX, mouseY, left, top) ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite,
                scrollerX, scrollerY + sliderOffset, SCROLLER_WIDTH, SCROLLER_HEIGHT);

        // 鼠标悬停时改变光标
        if (mouseX >= scrollerX && mouseX < scrollerX + SCROLLER_WIDTH &&
                mouseY >= scrollerY && mouseY < scrollerY + SCROLLER_AREA_HEIGHT) {
            guiGraphics.requestCursor(scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
        }
    }

    private void drawNodeInfo(GuiGraphicsExtractor guiGraphics, int mouthX, int mouthY, int left, int top) {
        if (getTotalDirections() == 0) {
            guiGraphics.text(font, Component.literal("无连接"), left + 10, top + 40, 0xFFFFFFFF);
            return;
        }

        // 显示选中节点序号/总数
        int totalNodes = isInsert ? menu.getInsert().size() : menu.getExtract().size();
        String indexStr = (posSelect + 1) + "/" + totalNodes;
        guiGraphics.text(font, Component.literal(indexStr), left + 10, top + 10, 0xFFFFFFFF);

        BlockPos currentPos = getCurrentPos();
        String posStr = String.format("(%d,%d,%d)", currentPos.getX(), currentPos.getY(), currentPos.getZ());
        guiGraphics.text(font, Component.literal(posStr), left + 10, top + 37, 0xFFAAAAAA);

        // 绘制方向图标（带滚动）
        drawDirectionIcons(guiGraphics, mouthX, mouthY, left, top);
    }

    private BlockPos getCurrentPos() {
        if (isInsert) {
            List<BlockPos> poses = new ArrayList<>(menu.getInsert().keySet());
            return poses.get(posSelect % poses.size());
        } else {
            List<BlockPos> poses = new ArrayList<>(menu.getExtract().keySet());
            return poses.get(posSelect % poses.size());
        }
    }

    private void drawDirectionIcons(GuiGraphicsExtractor guiGraphics, int mouthX, int mouthY, int left, int top) {
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
        }
    }

    private Identifier getNormal(BlockPos nodePos, Direction dir) {
        switch (menu.getExtract().get(nodePos).get(dir)){
            case FARTHEST : return FARTHEST;
            case POLLING : return POLLING;
            case null, default : return NEAREST;
        }
    }

    private Identifier getOn(BlockPos nodePos, Direction dir) {
        switch (menu.getExtract().get(nodePos).get(dir)){
            case FARTHEST : return FARTHEST_ON;
            case POLLING : return POLLING_ON;
            case null, default : return NEAREST_ON;
        }
    }

    private boolean isOnScroller(double mouthX, double mouthY, int left, int top){
        return (mouthX >= left + 129 && mouthX < left + 129 + SCROLLER_WIDTH &&
                mouthY >= top + 14 && mouthY < top + 14 + SCROLLER_AREA_HEIGHT);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        int left = (width - imageWidth) / 2;
        int top = (height - imageHeight) / 2;
        // 处理滚动条点击
        if (isScrollBarActive()) {
            if(isOnScroller(event.x(), event.y(), left, top)){
                this.scrolling = true;
                return true; // 吞噬事件，不继续传递
            }
        }

        // 节点切换逻辑
        if (event.button() == 1) { // 右键
            if (!isInsert) {
                int total = menu.getExtract().size();
                if (total > 0) {
                    posSelect = (posSelect + 1) % total;
                    resetScroll();
                }
            } else {
                int total = menu.getInsert().size();
                if (total > 0) {
                    posSelect = (posSelect + 1) % total;
                    resetScroll();
                }
            }
            return true;
        }
        else if(event.button() == 0){
            if(!isInsert) {
                int index = getIndex(event.x(), event.y());
                if (index != -1 && index < getTotalDirections()) {
                    Direction direction = getCurrentDirections().get(index);
                    BlockPos pos = getCurrentPos();
                    menu.getExtract().get(pos).compute(direction, (k, transferMode) -> transferMode.next());
                    ClientPacketDistributor.sendToServer(new MoePacket.NetChangePacket(pos, direction));
                }
            }
            if(isOnChange(event.x(), event.y(), left, top)){
                isInsert = !isInsert;
                posSelect = 0;
                resetScroll();
                return true;
            }
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (this.scrolling && this.isScrollBarActive()) {
            int left = (width - imageWidth) / 2;
            int top = (height - imageHeight) / 2;
            int scrollerY = top + 14;
            float newScroll = ((float)event.y() - scrollerY - SCROLLER_HEIGHT / 2.0F) / (SCROLLER_AREA_HEIGHT - SCROLLER_HEIGHT);
            this.scrollOffs = Mth.clamp(newScroll, 0.0F, 1.0F);
            updateStartIndexFromScroll();
            return true;
        }
        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        this.scrolling = false;
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
        if (super.mouseScrolled(x, y, scrollX, scrollY)) {
            return true;
        }
        if (this.isScrollBarActive()) {
            int maxStart = getMaxStartIndex();
            if (maxStart <= 0) return true;
            float delta = (float)scrollY / (float)maxStart;
            this.scrollOffs = Mth.clamp(this.scrollOffs - delta, 0.0F, 1.0F);
            updateStartIndexFromScroll();
            return true;
        }
        return true;
    }

    private void updateStartIndexFromScroll() {
        int maxStart = getMaxStartIndex();
        this.startIndex = (int)((double)(this.scrollOffs * maxStart) + 0.5);
        if (this.startIndex < 0) this.startIndex = 0;
        if (this.startIndex > maxStart) this.startIndex = maxStart;
    }

    private int getIndex(double mouthX, double mouthY){
        int left = (width - imageWidth) / 2, top = (height - imageHeight) / 2;
        int index = -1, startX = left + 48, startY = top + 14;
        if(mouthX < startX || mouthX > startX + ITEM_SIZE) return index;
        if(mouthY > startY && mouthY < startY + ITEM_SIZE) index = startIndex;
        else if (mouthY < startY + ITEM_SIZE * 2) index = startIndex + 1;
        else if ((mouthY < startY + ITEM_SIZE * 3)) index = startIndex + 2;
        return index;
    }

    private boolean isOnChange(double mouthX, double mouthY, int left, int top){
        int lx = isInsert ? left + 31 : left + 81, ly = top + 13, height = 11;
        if(mouthY > ly || mouthY < ly - height) return false;
        int dy = ly - (int) mouthY, leftPx = lx + dy, rightPx = lx + 61 - dy;
        if(dy < 6){
            if(isInsert) rightPx = rightPx - 11 + 2 * dy;
            else leftPx = leftPx + 11 - 2 * dy;
        }
        return mouthX >= leftPx & mouthX <= rightPx;
    }
}