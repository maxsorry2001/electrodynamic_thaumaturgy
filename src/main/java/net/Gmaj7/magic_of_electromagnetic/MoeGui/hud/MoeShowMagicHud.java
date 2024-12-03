package net.Gmaj7.magic_of_electromagnetic.MoeGui.hud;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MoeShowMagicHud implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof MagicUseItem)) itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof MagicUseItem)) return;
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        ItemStack typeStack = itemStack.get(DataComponents.CONTAINER).getStackInSlot(itemStack.get(MoeDataComponentTypes.MAGIC_SLOT));
        if(typeStack.getItem() instanceof MoeMagicTypeModuleItem item){
            if (item.getMagicType() != MoeMagicType.EMPTY && item.getMagicType() != MoeMagicType.ERROR){
                    guiGraphics.renderFakeItem(typeStack, screenWidth / 6, screenHeight * 7 / 8);
            }
        }
    }
}
