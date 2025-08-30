package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.electrodynamic_thaumaturgy.MagicOfElectromagnetic;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeThermalEnergyMakerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class MoeThermalGeneratorScreen extends AbstractContainerScreen<MoeThermalEnergyMakerMenu> {
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/thermal_generator_block.png");
    ResourceLocation energyTexture = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/energy.png");
    ResourceLocation fireTexture = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/fire.png");

    public MoeThermalGeneratorScreen(MoeThermalEnergyMakerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        IEnergyStorage iEnergyStorage = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 16 && mouseY > y + 20) && (mouseX < x + 176 && mouseY < y + 27))
            guiGraphics.renderTooltip(this.font, Component.literal(iEnergyStorage.getEnergyStored() + "FE / " + iEnergyStorage.getMaxEnergyStored() + "FE"), mouseX, mouseY);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelY = 10000;
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F,  1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, backGrand);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(backGrand,  x, y, 0, 0, imageWidth, imageHeight);
        renderEnergy(guiGraphics, x, y);
    }

    private void renderEnergy(GuiGraphics guiGraphics, int x, int y){
        IEnergyStorage iEnergyStorage = menu.blockEntity.getEnergy();
        guiGraphics.blit(energyTexture, x + 16, y + 20, 0, 0,  (int) (150 * (float)iEnergyStorage.getEnergyStored() / iEnergyStorage.getMaxEnergyStored()), 7, 150, 7);
        guiGraphics.blit(fireTexture, x + 16, y + 31, 0, 0,  menu.blockEntity.getFullBurnTime() == 0 ? 0 : 150 * menu.blockEntity.getBurnTime() / menu.blockEntity.getFullBurnTime(), 7, 150, 7);
    }
}
