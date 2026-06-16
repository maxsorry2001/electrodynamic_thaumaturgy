package net.Gmaj7.electrodynamic_thaumaturgy.Gui.screen;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.Gui.menu.EddyCurrentRemelterBlockMenu;
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

public class EddyCurrentRemelterBlockScreen extends AbstractContainerScreen<EddyCurrentRemelterBlockMenu> {
    Identifier backGrand = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/electromagnetic_dissociation.png");
    Identifier energyTexture = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/energy.png");
    protected static final WidgetSprites SPRITES_INPUT = new WidgetSprites(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input_disabled"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_input_highlighted"));
    protected static final WidgetSprites SPRITES_OUTPUT = new WidgetSprites(Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output_disabled"), Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "widget/item_output_highlighted"));

    public EddyCurrentRemelterBlockScreen(EddyCurrentRemelterBlockMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        extractTooltip(guiGraphics, mouseX, mouseY);
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        if((mouseX > x + 16 && mouseY > y + 20) && (mouseX < x + 176 && mouseY < y + 27))
            guiGraphics.setTooltipForNextFrame(this.font, Component.literal(energyHandler.getAmountAsInt() + "FE / " + energyHandler.getCapacityAsInt() + "FE"), mouseX, mouseY);
        guiGraphics.text(this.font, Component.literal(String.valueOf(menu.getProgress())), x + 145, y + 40, 0xFFFFFFFF);
        var itemSet = Function.decodeDirection(menu.getItemSet());
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.UP), isMouseFocusedSetting(Direction.UP, mouseX, mouseY)), x + 100, y + 50, 10, 10);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.DOWN), isMouseFocusedSetting(Direction.DOWN, mouseX, mouseY)), x + 100, y + 74, 10, 10);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.EAST), isMouseFocusedSetting(Direction.EAST, mouseX, mouseY)), x + 100, y + 62, 10, 10);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.NORTH), isMouseFocusedSetting(Direction.NORTH, mouseX, mouseY)), x + 112, y + 62, 10, 10);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.WEST), isMouseFocusedSetting(Direction.WEST, mouseX, mouseY)), x + 124, y + 62, 10, 10);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getSprites(itemSet.get(Direction.SOUTH), isMouseFocusedSetting(Direction.SOUTH, mouseX, mouseY)), x + 136, y + 62, 10, 10);
    }

    @Override
    protected void init() {
        this.inventoryLabelY = 1000;
        this.titleLabelY = 10000;
        super.init();
    }

    public Identifier getSprites(boolean input, boolean isFocused){
        return input ? SPRITES_INPUT.get(true, isFocused) : SPRITES_OUTPUT.get(true, isFocused);
    }

    private boolean isMouseFocusedSetting(Direction direction, double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double d0, d1;
        switch (direction){
            case UP -> {
                d0 = mouseX - x - 100;
                d1 = mouseY - y - 50;
            }
            case DOWN -> {
                d0 = mouseX - x - 100;
                d1 = mouseY - y - 74;
            }
            case EAST -> {
                d0 = mouseX - x - 100;
                d1 = mouseY - y - 62;
            }
            case NORTH -> {
                d0 = mouseX - x - 112;
                d1 = mouseY - y - 62;
            }
            case WEST -> {
                d0 = mouseX - x - 124;
                d1 = mouseY - y - 62;
            }
            case SOUTH -> {
                d0 = mouseX - x - 136;
                d1 = mouseY - y - 62;
            }
            default -> {d0 = 0; d1 = 0;}
        }
        return d0 > 0 && d0 < 10 && d1 > 0 && d1 < 10;
    }

    private Direction getFocusedDirection(double mouseX, double mouseY){
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        double dx = mouseX - x, dy = mouseY - y;
        Direction direction = null;
        if(dx > 100 && dx < 110){
            if(dy > 50 && dy < 60) direction = Direction.UP;
            else if(dy > 62 && dy < 72) direction = Direction.EAST;
            else if(dy > 74 && dy < 84) direction = Direction.DOWN;
        }
        else if(dy > 62 && dy < 72){
            if(dx > 112 && dx < 122) direction = Direction.NORTH;
            else if(dx > 124 && dx < 134) direction = Direction.WEST;
            else if (dx > 136 && dx < 146) direction = Direction.SOUTH;
        }
        return direction;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        int x = (width - imageWidth) / 2, y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, backGrand,  x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        renderEnergy(guiGraphics, x, y);
    }

    private void renderEnergy(GuiGraphicsExtractor guiGraphics, int x, int y){
        EnergyHandler energyHandler = menu.blockEntity.getEnergy();
        int renderX = (int) (150 * (float) energyHandler.getAmountAsInt() / energyHandler.getCapacityAsInt());
        guiGraphics.blit(energyTexture, x + 16, y + 20, x + 16 + renderX, y + 27, 0, 0, renderX, 7);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        Direction direction = getFocusedDirection(event.x(), event.y());
        if(direction != null)
            ClientPacketDistributor.sendToServer(new DirectionSetPacket(menu.blockEntity.getBlockPos(), direction));
        return super.mouseClicked(event, doubleClick);
    }
}
