package net.Gmaj7.electrodynamic_thaumaturgy.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public interface IEtFluidScreen {

    default void renderFluidWithTip(GuiGraphicsExtractor guiGraphics, ResourceHandler<FluidResource> fluidHandler, int mouseX, int mouseY, int renderIndex, int areaLeft, int areaTop, int areaHigh, int areaLength){
        renderFluid(guiGraphics, fluidHandler, renderIndex, areaLeft, areaTop, areaHigh, areaLength);
        renderFluidTooltip(guiGraphics, fluidHandler, renderIndex, mouseX, mouseY, areaLeft, areaTop, areaHigh, areaLength);
    }

    default void renderFluid(GuiGraphicsExtractor guiGraphics, ResourceHandler<FluidResource> fluidHandler, int renderIndex, int areaLeft, int areaTop, int areaHigh, int areaLength){
        FluidResource fluidResource = fluidHandler.getResource(renderIndex);
        if (fluidResource.isEmpty()) return;
        FluidStack fluidStack = fluidResource.toStack(fluidHandler.getAmountAsInt(renderIndex));

        int capacity = fluidHandler.getCapacityAsInt(renderIndex, fluidResource);
        int amount = fluidStack.amount();
        if (capacity <= 0 || amount <= 0) return;
        FluidModel fluidModel = Minecraft.getInstance().getModelManager()
                .getFluidStateModelSet()
                .get(fluidStack.getFluid().defaultFluidState());
        TextureAtlasSprite sprite = fluidModel.stillMaterial().sprite();
        FluidTintSource tintSource = fluidModel.fluidTintSource();
        int color = tintSource != null ? tintSource.colorAsStack(fluidStack) : CommonColors.WHITE;
        int scaledHeight = (amount * areaHigh) / capacity;
        if (amount > 0 && scaledHeight < 1) scaledHeight = 1; // 至少1像素
        if (scaledHeight > areaHigh) scaledHeight = areaHigh;

        // 3. 绘制流体（从底部向上）
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite,
                areaLeft, areaTop + areaHigh - scaledHeight,
                areaLength, scaledHeight, color);
    }

    default void renderFluidTooltip(GuiGraphicsExtractor guiGraphics, ResourceHandler<FluidResource> fluidHandler,
                                    int renderIndex, int mouseX, int mouseY,
                                    int areaLeft, int areaTop, int areaHigh, int areaLength) {
        if (fluidHandler == null || renderIndex < 0 || renderIndex >= fluidHandler.size()) return;

        FluidResource resource = fluidHandler.getResource(renderIndex);
        if (resource.isEmpty()) return;

        int amount = fluidHandler.getAmountAsInt(renderIndex);
        int capacity = fluidHandler.getCapacityAsInt(renderIndex, resource);
        if (amount <= 0 || capacity <= 0) return;

        // 检查鼠标是否在流体绘制区域内
        if (mouseX >= areaLeft && mouseX <= areaLeft + areaLength &&
                mouseY >= areaTop && mouseY <= areaTop + areaHigh) {

            FluidStack fluidStack = resource.toStack(amount);
            // 组建提示文本（流体名称 + 数量/容量）
            Component text = Component.literal(
                    fluidStack.getHoverName().getString() +
                            ": " + amount + " mB / " + capacity + " mB"
            );
            // 设置下一帧的工具提示
            guiGraphics.setTooltipForNextFrame(Minecraft.getInstance().font, text, mouseX, mouseY);
        }
    }
}
