package net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.ElectromagneticDissociationBlockMenu;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Function;
import net.Gmaj7.electrodynamic_thaumaturgy.Init.Packets.DirectionSetPacket;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

public class ElectromagneticDissociationBlockScreen extends AbstractContainerScreen<ElectromagneticDissociationBlockMenu> implements IEtDirectionItemScreen{
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_dissociation.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy.png");
    Identifier energyNullTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy_null.png");
    protected static final WidgetSprites SPRITES_INPUT = new WidgetSprites(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input_disabled"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input_highlighted"));
    protected static final WidgetSprites SPRITES_OUTPUT = new WidgetSprites(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output_disabled"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output_highlighted"));

    public ElectromagneticDissociationBlockScreen(ElectromagneticDissociationBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 13 && mouseY > y + 21) && (mouseX < x + 18 && mouseY < y + 71))
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(energyHandler.getAmountAsInt() + "FE / " + energyHandler.getCapacityAsInt() + "FE"), mouseX, mouseY);
        renderIcon(guiGraphics, Function.decodeDirection(menu.getItemSet()), mouseX, mouseY, x, y);
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
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int renderY = (int) (50 * (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt());
        guiGraphics.blit(energyTexture, x + 13, y + 71 - renderY, x + 18 , y + 71, 0, 0, 6, renderY);
        if(renderY < 50)
            guiGraphics.blit(energyNullTexture, x + 13, y + 21, x + 18, y + 71 - renderY, 0, 0, 6, 50 - renderY);
    }

    @Override
    public Identifier getSprites(boolean input, boolean isFocused){
        return input ? SPRITES_INPUT.get(true, isFocused) : SPRITES_OUTPUT.get(true, isFocused);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        Direction direction = getFocusedDirection(event.x(), event.y(), (width - imageWidth) / 2, (height - imageHeight) / 2);
        if(direction != null)
            ClientPacketDistributor.sendToServer(new DirectionSetPacket(menu.blockEntity.getBlockPos(), direction));
        return super.mouseClicked(event, doubleClick);
    }
}
