package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeGeologicalMetalExcavatorBlockMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class MoeGeologicalMetalExcavatorBlockScreen extends AbstractContainerScreen<MoeGeologicalMetalExcavatorBlockMenu> {
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/gui/27_slots_machine_block.png");

    public MoeGeologicalMetalExcavatorBlockScreen(MoeGeologicalMetalExcavatorBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        //IEnergyStorage iEnergyStorage = menu.blockEntity.getEnergy();
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
    }
}
