package net.Gmaj7.magic_of_electromagnetic.MoeGui.hud;

import net.Gmaj7.magic_of_electromagnetic.MagicOfElectromagnetic;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeSelectMagicPacket;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicCastItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.network.PacketDistributor;

public class MoeMagicWheelHud implements LayeredDraw.Layer {
    public static MoeMagicWheelHud instance = new MoeMagicWheelHud();
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "textures/gui/select_hud.png");
    public boolean active;
    private int selection;
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        if(!active) return;
        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getMainHandItem();
        if (player == null || Minecraft.getInstance().screen != null || Minecraft.getInstance().mouseHandler.isMouseGrabbed()
            ) {
            close();
            return;
        }
        if(!(stack.getItem() instanceof MagicCastItem)) stack = player.getOffhandItem();
        if(!((stack.getItem()) instanceof MagicCastItem)) return;
        int centerX = screenWidth / 2, centerY = screenHeight / 2;
        int r = centerY * 2 / 3;
        double alpha = - 0.375 * Math.PI;
        ItemContainerContents contents = stack.get(DataComponents.CONTAINER);
        for (int i = 2; i < MagicCastItem.getMaxMagicSlots(); i++){
            ItemStack type = contents.getStackInSlot(i);
            if(type.getItem() instanceof MoeMagicTypeModuleItem item && !item.isEmpty()){
                guiGraphics.renderFakeItem(type, (int) (centerX + r * Math.cos(alpha) - 8), (int) (centerY + r * Math.sin(alpha)) - 8);
            }
            alpha = alpha + 0.25 * Math.PI;
        }
        int textureWidth = 256, textureHeight = 256;
        guiGraphics.blit(TEXTURE, centerX - textureWidth / 2, centerY - textureHeight / 2, 0, 0, textureWidth, textureHeight);
        double mouseX = Minecraft.getInstance().mouseHandler.xpos(), mouseY = Minecraft.getInstance().mouseHandler.ypos();
        double screenCenterX = Minecraft.getInstance().getWindow().getScreenWidth() / 2F, screenCenterY = Minecraft.getInstance().getWindow().getScreenHeight() / 2F;
        double Lx = mouseX - screenCenterX, Ly = mouseY - screenCenterY, l = Math.sqrt(Math.pow(Lx, 2) + Math.pow(Ly, 2));
        double beta = Math.asin(Ly / l);
        if(Lx < 0) beta = beta < 0 ? - Math.PI - beta : Math.PI - beta;
        if(beta >= - Math.PI && beta < - Math.PI * 0.75) selection = 8;
        else if (beta < - Math.PI * 0.5) selection = 9;
        else if (beta < - Math.PI * 0.25) selection = 2;
        else if (beta < 0) selection = 3;
        else if (beta < Math.PI * 0.25) selection = 4;
        else if (beta < Math.PI * 0.5) selection = 5;
        else if (beta < Math.PI * 0.75) selection = 6;
        else if (beta < Math.PI) selection = 7;
    }

    public void close(){
        active = false;
        if(selection > 1)
            PacketDistributor.sendToServer(new MoeSelectMagicPacket(selection));
        Minecraft.getInstance().mouseHandler.grabMouse();
    }

    public void open(){
        active = true;
        selection = 1;
        Minecraft.getInstance().mouseHandler.releaseMouse();
    }
}
