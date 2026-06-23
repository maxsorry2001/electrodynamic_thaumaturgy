package net.Gmaj7.electrodynamic_thaumaturgy.gui.hud;

import net.Gmaj7.electrodynamic_thaumaturgy.ElectrodynamicThaumaturgy;
import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.init.componentDatas.ItemContainerData;
import net.Gmaj7.electrodynamic_thaumaturgy.init.packets.SelectBowPacket;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class BowWheelHud implements GuiLayer {
    public static BowWheelHud instance = new BowWheelHud();
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ElectrodynamicThaumaturgy.MODID, "textures/gui/select_hud_bow.png");
    public boolean active;
    private int selection;
    private InteractionHand useHand;
    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        if(!active) return;
        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getItemInHand(useHand);
        if (player == null || Minecraft.getInstance().screen != null || Minecraft.getInstance().mouseHandler.isMouseGrabbed()
            ) {
            close();
            return;
        }
        int centerX = screenWidth / 2, centerY = screenHeight / 2;
        int r = centerY / 2;
        double alpha = - 0.375 * Math.PI;
        ItemContainerData contents = stack.get(EtDataComponentTypes.ET_CONTAINER.get());
        //for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i++){
        //    ItemStack type = contents.getStackInSlot(i);
        //    if(type.getItem() instanceof EtMagicTypeModuleItem item && !item.isEmpty()){
        //        guiGraphics.fakeItem(type, (int) (centerX + r * Math.cos(alpha) - 8), (int) (centerY + r * Math.sin(alpha)) - 8);
        //        guiGraphics.itemDecorations(Minecraft.getInstance().font, type, (int) (centerX + r * Math.cos(alpha) - 8), (int) (centerY + r * Math.sin(alpha)) - 8);
        //    }
        //    alpha = alpha + 0.25 * Math.PI;
        //}
        int textureWidth = 256, textureHeight = 256;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, centerX - textureWidth / 2, centerY - textureHeight / 2, 0, 0, textureWidth, textureHeight, 256, 256);
        double mouseX = Minecraft.getInstance().mouseHandler.xpos(), mouseY = Minecraft.getInstance().mouseHandler.ypos();
        double screenCenterX = Minecraft.getInstance().getWindow().getScreenWidth() / 2F, screenCenterY = Minecraft.getInstance().getWindow().getScreenHeight() / 2F;
        double Lx = mouseX - screenCenterX, Ly = mouseY - screenCenterY;
        double beta = Math.atan2(Ly, Lx);
        if(beta < - Math.PI / 3) selection = 0;
        else if (beta < Math.PI / 3) selection = 1;
        else if (beta < Math.PI) selection = 2;
    }

    public void close(){
        active = false;
        if(selection > -1)
            ClientPacketDistributor.sendToServer(new SelectBowPacket(selection, useHand));
        Minecraft.getInstance().mouseHandler.grabMouse();
    }

    public void open(InteractionHand hand){
        active = true;
        selection = 0;
        useHand = hand;
        Minecraft.getInstance().mouseHandler.releaseMouse();
    }
}
