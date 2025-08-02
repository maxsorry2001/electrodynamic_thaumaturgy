package net.Gmaj7.electrofynamic_thaumatury.MoeGui.hud;

import net.Gmaj7.electrofynamic_thaumatury.MagicOfElectromagnetic;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeData.MoeDataGet;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class MoeProtectHud implements LayeredDraw.Layer {
    public static final ResourceLocation FULL = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moe_protect_full");
    public static final ResourceLocation HALF = ResourceLocation.fromNamespaceAndPath(MagicOfElectromagnetic.MODID, "moe_protect_half");
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if(player.isCreative()) return;
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        float protect = ((MoeDataGet)player).getProtective().getProtecting();
        int p = Mth.floor(protect / 2);
        double q = protect % 2;
        for (int k = 0; k < p; k++)
            guiGraphics.blitSprite(FULL, screenWidth / 2 - 91 + 8 * k, screenHeight - 39, 9, 9);
        if(q > 0.5)
            guiGraphics.blitSprite(HALF, screenWidth / 2 - 91 + 8 * p, screenHeight - 39, 9, 9);
    }
}
