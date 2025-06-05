package net.Gmaj7.magic_of_electromagnetic.MoeGui.screen;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeGui.menu.MagicLithographyTableMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MagicLithographyTableScreen extends AbstractContainerScreen<MagicLithographyTableMenu> {
    Button button;
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/electromagnetic_assembly_table.png");

    public MagicLithographyTableScreen(MagicLithographyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        this.button = this.addRenderableWidget(Button.builder(Component.translatable("moe_assemble"), (p) -> { })
                .bounds(this.width / 2 + this.imageWidth / 4, this.height / 2 - this.imageHeight / 3, 30, 20).build());
        this.inventoryLabelY = 1000;
        this.titleLabelX = this.imageWidth / 3;
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.button.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //if(this.menu.getToolSlot().hasItem() && this.menu.getToolSlot().getItem().getItem() instanceof MagicCastItem) {
        //    double d0 = mouseX - this.width / 2 - this.imageWidth / 4;
        //    double d1 = mouseY - this.height / 2 + this.imageHeight / 3;
        //    if(d0 > 0 && d0 < 30 && d1 > 0 && d1 < 20){
        //        if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
        //            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, button);
        //            return true;
        //        }
        //    }
        //}
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(backGrand,  this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
