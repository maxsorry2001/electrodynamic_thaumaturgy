package net.Gmaj7.electrodynamic_thaumaturgy.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.CommonColors;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public interface IEtFluidScreen {

    default void renderFluid(GuiGraphicsExtractor guiGraphics, ResourceHandler<FluidResource> fluidHandler, int renderIndex, int areaLeft, int areaTop, int areaHigh, int areaLength){
        FluidResource fluidResource = fluidHandler.getResource(renderIndex);
        if (fluidResource.isEmpty()) return;
        FluidStack fluidStack = fluidResource.toStack(fluidHandler.getAmountAsInt(renderIndex));

        int capacity = fluidHandler.getCapacityAsInt(renderIndex, fluidResource);
        int amount = fluidStack.amount();
        if (capacity <= 0 || amount <= 0) return;
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager()
                .getFluidStateModelSet()
                .get(fluidStack.getFluid().defaultFluidState())
                .stillMaterial().sprite();
        FluidTintSource tintSource = Minecraft.getInstance().getModelManager()
                .getFluidStateModelSet()
                .get(fluidStack.getFluid().defaultFluidState())
                .fluidTintSource();
        int color = tintSource != null ? tintSource.colorAsStack(fluidStack) : CommonColors.WHITE;
        int scaledHeight = (amount * areaHigh) / capacity;
        if (amount > 0 && scaledHeight < 1) scaledHeight = 1; // 至少1像素
        if (scaledHeight > areaHigh) scaledHeight = areaHigh;

        // 3. 绘制流体（从底部向上）
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite,
                areaLeft, areaTop + areaHigh - scaledHeight,
                areaLength, scaledHeight, color);
    }
}
