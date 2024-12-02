package net.Gmaj7.magic_of_electromagnetic.MoeGui.hud;

import net.Gmaj7.magic_of_electromagnetic.MoeGui.screen.MoeMagicSelectScreen;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeKeyMapping;
import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeMagicType;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MagicUseItem;
import net.Gmaj7.magic_of_electromagnetic.MoeItem.custom.MoeMagicTypeModuleItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MoeShowMagicHud implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        ItemStack mainHand = player.getMainHandItem();
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        if (mainHand.getItem() instanceof MagicUseItem) {
            ItemStack typeStack = mainHand.get(DataComponents.CONTAINER).getStackInSlot(0);
            if(typeStack.getItem() instanceof MoeMagicTypeModuleItem item){
                if (item.getMagicType() != MoeMagicType.EMPTY && item.getMagicType() != MoeMagicType.ERROR){
                    guiGraphics.renderFakeItem(typeStack, screenWidth / 5, screenHeight * 7 / 8);
                }
                if (MoeKeyMapping.SWITCH_MAGIC.consumeClick()){
                    Minecraft.getInstance().setScreen(new MoeMagicSelectScreen(Component.literal(" ")));
                }
            }
        }
    }
}
