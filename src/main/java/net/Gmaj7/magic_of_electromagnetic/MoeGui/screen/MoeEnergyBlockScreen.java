package net.Gmaj7.magic_of_electromagnetic.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MoeEnergyBlockMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoeEnergyBlockScreen extends AbstractContainerScreen<MoeEnergyBlockMenu> {
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/electromagnetic_energy_block.png");

    public MoeEnergyBlockScreen(MoeEnergyBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelX = this.imageWidth / 3;
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F,  1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, backGrand);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(backGrand,  x, y, 0, 0, imageWidth, imageHeight);
    }
}
