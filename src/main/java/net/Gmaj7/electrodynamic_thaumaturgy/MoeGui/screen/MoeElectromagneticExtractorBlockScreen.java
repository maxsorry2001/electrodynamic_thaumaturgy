package net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.electrodynamic_thaumaturgy.EelectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.MoeGui.menu.MoeElectromagneticExtractorBlockMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoeElectromagneticExtractorBlockScreen extends AbstractContainerScreen<MoeElectromagneticExtractorBlockMenu> {
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_extractor_machine_block.png");
    protected static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "widget/button"), ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "widget/button_disabled"), ResourceLocation.fromNamespaceAndPath(EelectrodynamicThaumaturgy.MODID, "widget/button_highlighted"));

    public MoeElectromagneticExtractorBlockScreen(MoeElectromagneticExtractorBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        renderTooltip(guiGraphics, mouseX, mouseY);
        guiGraphics.blitSprite(SPRITES.get(false, false), x + 73, y + 8, 30, 12);
        guiGraphics.blitSprite(SPRITES.get(true, isMouseAddFocused(mouseX, mouseY)), x + 58, y + 8, 12, 12);
        guiGraphics.blitSprite(SPRITES.get(true, isMouseReduceFocused(mouseX, mouseY)), x + 106, y + 8, 12, 12);
        guiGraphics.drawString(this.font, Component.translatable("moe_extractor_width").append(":" + menu.blockEntity.width), x + 76, y + 10, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("+"), x + 60, y + 10, 0xFFFFFF);
        guiGraphics.drawString(this.font, Component.literal("-"), x + 108, y + 10, 0xFFFFFF);
    }

    private boolean isMouseAddFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 58;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    private boolean isMouseReduceFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - 106;
        double d1 = mouseY - y - 8;
        return d0 > 0 && d0 < 12 && d1 > 0 && d1 < 12;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isMouseAddFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, button);
                return true;
            }
        }
        if(isMouseReduceFocused(mouseX, mouseY)){
            if (this.menu.clickMenuButton(this.minecraft.player, 1)) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, button);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
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
    }
}
