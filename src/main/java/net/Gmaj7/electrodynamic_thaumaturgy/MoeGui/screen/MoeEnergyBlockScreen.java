package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeEnergyBlockMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public class MoeEnergyBlockScreen extends AbstractContainerScreen<MoeEnergyBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_energy_block.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy.png");

    public MoeEnergyBlockScreen(MoeEnergyBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 16 && mouseY > y + 20) && (mouseX < x + 176 && mouseY < y + 27))
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(energyHandler.getAmountAsInt() + "FE / " + energyHandler.getCapacityAsInt() + "FE"), mouseX, mouseY);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelY = 10000;
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, backGrand,  x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderEnergy(guiGraphics, x, y);
    }

    private void renderEnergy(GuiGraphicsExtractor guiGraphics, int x, int y){
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        guiGraphics.blit(energyTexture, x + 16, y + 20, 0, 0, (int) (150 * (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt()), 7, 150, 7);
    }
}
