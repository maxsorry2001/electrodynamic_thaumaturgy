package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeElectromagneticExtractorBlockMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class MoeElectromagneticExtractorBlockScreen extends AbstractContainerScreen<MoeElectromagneticExtractorBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_extractor_machine_block.png");
    protected static final WidgetSprites SPRITES = new WidgetSprites(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/button"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/button_disabled"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/button_highlighted"));

    public MoeElectromagneticExtractorBlockScreen(MoeElectromagneticExtractorBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        extractTooltip(guiGraphics, mouseX, mouseY);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(false, false), x + 28, y + 8, 40, 12);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(true, isMouseAddWidthFocused(mouseX, mouseY)), x + 13, y + 8, 12, 12);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(true, isMouseReduceWidthFocused(mouseX, mouseY)), x + 71, y + 8, 12, 12);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(false, false), x + 115, y + 8, 40, 12);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(true, isMouseAddDepthFocused(mouseX, mouseY)), x + 100, y + 8, 12, 12);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(true, isMouseReduceDepthFocused(mouseX, mouseY)), x + 158, y + 8, 12, 12);
        guiGraphics.text(this.font, Component.translatable("moe_extractor_width").append(":" + menu.blockEntity.width), x + 30, y + 10, 0xFFFFFF);
        guiGraphics.text(this.font, Component.literal("+"), x + 15, y + 10, 0xFFFFFF);
        guiGraphics.text(this.font, Component.literal("-"), x + 73, y + 10, 0xFFFFFF);
        guiGraphics.text(this.font, Component.translatable("moe_extractor_depth").append(":" + menu.blockEntity.depth), x + 117, y + 10, 0xFFFFFF);
        guiGraphics.text(this.font, Component.literal("+"), x + 102, y + 10, 0xFFFFFF);
        guiGraphics.text(this.font, Component.literal("-"), x + 160, y + 10, 0xFFFFFF);
    }

    private boolean isMouseAddWidthFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 13;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    private boolean isMouseReduceWidthFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 71;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    private boolean isMouseAddDepthFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 100;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    private boolean isMouseReduceDepthFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 158;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        this.menu.quick = hasShiftDown();
        double mouseX = event.x(), mouseY = event.y();
        if(isMouseAddWidthFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
                return true;
            }
        }
        if(isMouseReduceWidthFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 1)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 1);
                return true;
            }
        }
        if(isMouseAddDepthFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 2)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 2);
                return true;
            }
        }
        if(isMouseReduceDepthFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 3)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 3);
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
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
    }
}
