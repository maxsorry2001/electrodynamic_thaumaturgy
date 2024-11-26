package net.Gmaj7.magic_of_electromagnetic.MoeGui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoeAssemblyTableScreen extends AbstractContainerScreen<MoeAssemblyTableMenu> {
    Button button;
    ResourceLocation backGrand = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/electromagnetic_assembly_table.png");

    public MoeAssemblyTableScreen(MoeAssemblyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        this.button = this.addRenderableWidget(Button.builder(Component.translatable("moe_assemble"), (p) -> { })
                .bounds(this.width / 2 + this.imageWidth / 4, this.height / 2 - this.imageHeight / 3, 30, 20).build());
        this.inventoryLabelY = 1000;
        this.titleLabelY = 1000;
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
        if(this.menu.slots.get(0).hasItem() && this.menu.slots.get(0).getItem().has(MoeDataComponentTypes.ELECTROMAGNETIC_MAGIC_TYPE)) {
            double d0 = mouseX - this.width / 2 - this.imageWidth / 4;
            double d1 = mouseY - this.height / 2 + this.imageHeight / 3;
            if(d0 > 0 && d0 < 30 && d1 > 0 && d1 < 20){
                if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
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
