package net.Gmaj7.electrodynamic_thaumaturgy.compat.screenItemHandler;

import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen.FilterSettingScreen;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.FilterSettingItemPacket;
import net.Gmaj7.electrodynamic_thaumaturgy.Item.custom.FluidFakeItem;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class FilterSettingHandler implements IGhostIngredientHandler<FilterSettingScreen> {

    // 每个过滤槽位的数量（例如 3）
    protected static final int FILTER_SLOTS_PER_DIRECTION = 3;
    // 图标大小
    protected static final int ITEM_SIZE = 18;


    private static final int COLS = 6;
    private static final int ROWS = 3;

    @Override
    public <I> List<Target<I>> getTargetsTyped(FilterSettingScreen gui, ITypedIngredient<I> ingredient, boolean doStart) {
        // 只处理物品类型
        ItemStack draggedStack = wrapDraggedItem(ingredient);
        if (draggedStack == null || draggedStack.isEmpty()) {
            return List.of();
        }

        List<Target<I>> targets = new ArrayList<>();

        int left = (gui.width - gui.getImageWidth()) / 2;
        int top = (gui.height - gui.getImageHeight()) / 2;
        int baseX = left + 30;  // 第一个过滤槽的 X 坐标（参考您的代码）
        int baseY = top + 14;                  // 第一行的 Y 坐标

        for (int row = 0; row < ROWS; row++) {
            // 每个方向有 3 个过滤槽位
            for (int slot = 0; slot < COLS; slot++) {
                int x = baseX + slot * ITEM_SIZE;
                int y = baseY + row * ITEM_SIZE;
                Rect2i area = new Rect2i(x, y, ITEM_SIZE, ITEM_SIZE);
                int finalSlot = slot;
                targets.add(getTarget(area, finalSlot, gui));
            }
        }
        return targets;
    }

    protected ItemStack wrapDraggedItem(ITypedIngredient<?> ingredient){
        var stack = ingredient.getIngredient(ingredient.getType());
        ItemStack itemStack = ItemStack.EMPTY;
        if(stack.get() instanceof ItemStack) itemStack = (ItemStack) stack.get();
        else if(stack.get() instanceof FluidStack) itemStack = FluidFakeItem.creatFluidFilter((FluidStack)stack.get());
        return itemStack;
    }

    @Override
    public void onComplete() {
        // 无需额外操作
    }

    @Override
    public <I> boolean quickMove(FilterSettingScreen gui, ITypedIngredient<I> ingredient) {
        // 可选实现快速移动，返回 false 让 JEI 继续尝试其他处理器
        return false;
    }

    protected TargetFilter getTarget(Rect2i area, int finalSlot, FilterSettingScreen gui){
        return new TargetFilter(area, finalSlot, gui);
    };

    protected static class TargetFilter<T> implements Target<T>{
        protected Rect2i area;
        protected int slot;
        protected FilterSettingScreen screen;

        TargetFilter(Rect2i area, int slot, FilterSettingScreen screen){
            this.area = area;
            this.screen = screen;
            this.slot = slot;
        }

        @Override
        public Rect2i getArea() {
            return area;
        }

        @Override
        public void accept(T ingredient) {
            ItemStack itemStack = ItemStack.EMPTY;
            if(ingredient instanceof ItemStack) itemStack = (ItemStack) ingredient;
            else if (ingredient instanceof FluidStack) itemStack = FluidFakeItem.creatFluidFilter((FluidStack) ingredient);
            if(itemStack.isEmpty()) return;
            ClientPacketDistributor.sendToServer(new FilterSettingItemPacket(slot, itemStack));
            List<ItemStack> list = screen.getMenu().getFilterList();
            if(itemStack.isEmpty() && slot < list.size()){
                list.remove(slot);
            }
            else {
                if (slot >= list.size()) list.add(itemStack);
                else list.set(slot, itemStack);
            }
            ItemContainerContents contents = ItemContainerContents.fromItems(list);
            screen.getMenu().getFilter().set(EtDataComponentTypes.ET_CONTAINER, contents);

        }
    }
}