package net.Gmaj7.electrodynamic_thaumaturgy.gui.hud;

import net.Gmaj7.electrodynamic_thaumaturgy.init.EtDataComponentTypes;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.EtMagicTypeModuleItem;
import net.Gmaj7.electrodynamic_thaumaturgy.item.custom.MagicCastItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;

public class ShowMagicHud implements GuiLayer {
    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof MagicCastItem)) itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof MagicCastItem)) return;
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        ItemStack typeStack = itemStack.get(EtDataComponentTypes.ET_CONTAINER.get()).getStackInSlot(itemStack.get(EtDataComponentTypes.MAGIC_SELECT));
        if(typeStack.getItem() instanceof EtMagicTypeModuleItem item){
            if (!item.isEmpty()){
                guiGraphics.fakeItem(typeStack, screenWidth / 6, screenHeight * 7 / 8);
                guiGraphics.itemDecorations(Minecraft.getInstance().font, typeStack, screenWidth / 6, screenHeight * 7 / 8);
            }
        }
    }
}
