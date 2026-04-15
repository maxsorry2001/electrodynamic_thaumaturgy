package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeEntityCloneBlockMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class MoeEntityCloneBlockScreen extends AbstractContainerScreen<MoeEntityCloneBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/one_item_machine.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy.png");

    public MoeEntityCloneBlockScreen(MoeEntityCloneBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        IEnergyStorage iEnergyStorage = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 16 && mouseY > y + 20) && (mouseX < x + 176 && mouseY < y + 27))
            guiGraphics.tooltip(this.font, Component.literal(iEnergyStorage.getEnergyStored() + "FE / " + iEnergyStorage.getMaxEnergyStored() + "FE"), mouseX, mouseY);
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
        IEnergyStorage iEnergyStorage = menu.blockEntity.getEnergy();
        guiGraphics.blit(energyTexture, x + 16, y + 20, 0, 0, (int) (150 * (float) iEnergyStorage.getEnergyStored() / iEnergyStorage.getMaxEnergyStored()), 7, 150, 7);
    }
}
