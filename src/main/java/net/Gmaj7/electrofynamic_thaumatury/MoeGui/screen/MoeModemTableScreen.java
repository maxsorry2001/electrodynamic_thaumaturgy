package net.Gmaj7.electrofynamic_thaumatury.MoeGui.screen;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeGui.menu.MoeModemTableMenu;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MagicCastItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoeModemTableScreen extends AbstractContainerScreen<MoeModemTableMenu> {
    Button button;
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/electromagnetic_modem_table.png");
    protected static final WidgetSprites SPRITES = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "widget/button"), ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "widget/button_disabled"), ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "widget/button_highlighted"));

    public MoeModemTableScreen(MoeModemTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelX = this.imageWidth / 3;
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blitSprite(SPRITES.get(true, isMouseFocused(mouseX, mouseY)), x + 138, y + 35, 30, 20);
        guiGraphics.drawString(this.font, Component.translatable("moe_modem"), x + 145, y + 40, 0xFFFFFF);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean isMouseFocused(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0 = mouseX - x - this.imageWidth * 3 / 4;
        double d1 = mouseY - y - this.imageHeight / 4;
        return d0 > 0 && d0 < 30 && d1 > 0 && d1 < 20;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(this.menu.getToolSlot().hasItem() && this.menu.getToolSlot().getItem().getItem() instanceof MagicCastItem) {
            if(isMouseFocused(mouseX, mouseY)){
                if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, button);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(backGrand,  this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
