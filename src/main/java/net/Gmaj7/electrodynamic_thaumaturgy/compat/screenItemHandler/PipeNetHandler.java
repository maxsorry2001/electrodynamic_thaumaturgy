package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.screen.PipeNetScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PipeNetHandler<T extends PipeNetScreen<?>> implements IGhostIngredientHandler<T> {

    // 每个过滤槽位的数量（例如 3）
    protected static final int FILTER_SLOTS_PER_DIRECTION = 3;
    // 图标大小
    protected static final int ITEM_SIZE = 18;

    @Override
    public <I> List<Target<I>> getTargetsTyped(T gui, ITypedIngredient<I> ingredient, boolean doStart) {
        // 只处理物品类型
        ItemStack draggedStack = wrapDraggedItem(ingredient);
        if (draggedStack == null || draggedStack.isEmpty()) {
            return List.of();
        }

        List<Target<I>> targets = new ArrayList<>();

        // 获取当前屏幕中显示的方向列表（根据模式：抽取或插入）
        List<Direction> directions = gui.getCurrentDirections();
        if (directions.isEmpty()) return targets;

        int startIndex = gui.getStartIndex();      // 当前页起始索引
        int visibleRows = gui.getVisibleRows();    // 可见行数（例如 6）
        BlockPos currentPos = gui.getCurrentPos(); // 当前管道位置

        // 计算过滤槽的起始坐标（需要与 drawDirectionIcons 中的计算保持一致）
        int left = (gui.width - gui.getImageWidth()) / 2;
        int top = (gui.height - gui.getImageHeight()) / 2;
        int baseX = left + 30 + 2 * ITEM_SIZE;  // 第一个过滤槽的 X 坐标（参考您的代码）
        int baseY = top + 14;                  // 第一行的 Y 坐标

        for (int row = 0; row < visibleRows; row++) {
            int idx = startIndex + row;
            if (idx >= directions.size()) break;
            Direction dir = directions.get(idx);
            // 每个方向有 3 个过滤槽位
            for (int slot = 0; slot < FILTER_SLOTS_PER_DIRECTION; slot++) {
                int x = baseX + slot * ITEM_SIZE;
                int y = baseY + row * ITEM_SIZE;
                Rect2i area = new Rect2i(x, y, ITEM_SIZE, ITEM_SIZE);
                int finalSlot = slot;
                targets.add(getTarget(currentPos, dir, area, finalSlot, gui));
            }
        }
        return targets;
    }

    protected abstract ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient);

    @Override
    public void onComplete() {
        // 无需额外操作
    }

    @Override
    public <I> boolean quickMove(T gui, ITypedIngredient<I> ingredient) {
        // 可选实现快速移动，返回 false 让 JEI 继续尝试其他处理器
        return false;
    }

    protected abstract <I> TargetFilter<I,T> getTarget(BlockPos currentPos, Direction dir, Rect2i area, int finalSlot, T gui);

    protected abstract static class TargetFilter<I,T extends PipeNetScreen<?>> implements Target<I>{
        protected Rect2i area;
        protected BlockPos pos;
        protected Direction direction;
        protected int slot;
        protected T screen;

        TargetFilter(BlockPos pos, Direction direction, Rect2i area, int slot, T screen){
            this.area = area;
            this.screen = screen;
            this.pos = pos;
            this.slot = slot;
            this.direction = direction;
        }

        @Override
        public Rect2i getArea() {
            return area;
        }
    }
}