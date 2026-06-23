package net.Gmaj7.electrodynamic_thaumaturgy.gui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.gui.menu.FluidBlockMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class FluidBlockScreen extends AbstractContainerScreen<FluidBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_fluid_block.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy_block_show.png");
    Identifier energyNullTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy_block_null.png");

    public FluidBlockScreen(FluidBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        ResourceHandler<FluidResource> fluidHandler = menu.blockEntity.getFluidHandler();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 16 && mouseY > y + 20) && (mouseX < x + 176 && mouseY < y + 27))
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(fluidHandler.getAmountAsInt(0) + "B / " + fluidHandler.getCapacityAsInt(0, fluidHandler.getResource(0)) + "B"), mouseX, mouseY);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelY = 10000;
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        this.extractBlurredBackground(guiGraphics);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, backGrand,  x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderEnergy(guiGraphics, x, y);
    }

    private void renderEnergy(GuiGraphicsExtractor guiGraphics, int x, int y){
        ResourceHandler<FluidResource> fluidHandler = menu.blockEntity.getFluidHandler();
        int renderX = (int) (150 * (float) fluidHandler.getAmountAsInt(0) / fluidHandler.getCapacityAsInt(0, fluidHandler.getResource(0)));
        guiGraphics.blit(energyTexture, x + 16, y + 20, x + 16 + renderX, y + 27, 0, 0, renderX, 7);
        if(renderX < 150)
            guiGraphics.blit(energyNullTexture, x + 16 + renderX, y + 20, x + 166, y + 27, 0, 0, 150 - renderX, 7);
    }
}
