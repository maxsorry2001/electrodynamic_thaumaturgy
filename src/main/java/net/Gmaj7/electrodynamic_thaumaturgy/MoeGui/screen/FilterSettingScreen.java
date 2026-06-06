package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.FilterSettingMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class FilterSettingScreen extends AbstractContainerScreen<FilterSettingMenu> {
    protected static final Identifier WHITE_GRAND = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/net_gui_extract.png");
    protected static final Identifier BLACK_GRAND = Identifier.fromNamespaceAndPath(
            ElectrodynamicThaumaturgy.MODID, "textures/gui/net_gui_insert.png");

    public FilterSettingScreen(FilterSettingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, WHITE_GRAND, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        this.extractBlurredBackground(guiGraphics);
    }
}
