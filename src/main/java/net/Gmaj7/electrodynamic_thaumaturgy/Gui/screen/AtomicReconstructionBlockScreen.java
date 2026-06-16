package net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.AtomicReconstructionBlockMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

import java.util.List;

public class AtomicReconstructionBlockScreen extends AbstractContainerScreen<AtomicReconstructionBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/atomic_reconstruction_machine.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy.png");
    Identifier energyNullTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy_null.png");
    Identifier progressTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/atomic_reconstruction_machine_progress.png");

    public AtomicReconstructionBlockScreen(AtomicReconstructionBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 13 && mouseY > y + 21) && (mouseX < x + 18 && mouseY < y + 71))
            guiGraphics.setTooltipForNextFrame(this.font, List.of(), mouseX, mouseY);
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
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int renderY = (int) (50 * (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt());
        guiGraphics.blit(energyTexture, x + 13, y + 71 - renderY, x + 18 , y + 71, 0, 0, 6, renderY);
        if(renderY < 50)
            guiGraphics.blit(energyNullTexture, x + 13, y + 21, x + 18, y + 71 - renderY, 0, 0, 6, 50 - renderY);
    }
}
