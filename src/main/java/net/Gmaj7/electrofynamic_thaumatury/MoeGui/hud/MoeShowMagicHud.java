package net.Gmaj7.electrofynamic_thaumatury.MoeGui.hud;

import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeData.MoeDataGet;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeDataComponentTypes;
import net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeMagicType;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MagicCastItem;
import net.Gmaj7.electrofynamic_thaumatury.MoeItem.custom.MoeMagicTypeModuleItem;
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
        if (!(itemStack.getItem() instanceof MagicCastItem)) itemStack = player.getOffhandItem();
        if (!(itemStack.getItem() instanceof MagicCastItem)) return;
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();
        ItemStack typeStack = itemStack.get(DataComponents.CONTAINER).getStackInSlot(itemStack.get(MoeDataComponentTypes.MAGIC_SELECT));
        if(typeStack.getItem() instanceof MoeMagicTypeModuleItem item){
            if (item.getMagicType() != MoeMagicType.EMPTY && item.getMagicType() != MoeMagicType.ERROR){
                guiGraphics.renderFakeItem(typeStack, screenWidth / 6, screenHeight * 7 / 8);
                guiGraphics.renderItemDecorations(Minecraft.getInstance().font, typeStack, screenWidth / 6, screenHeight * 7 / 8);
            }
        }
        float protect = ((MoeDataGet)player).getProtective().getProtecting();
        if(protect > 0 && !player.isCreative()) guiGraphics.drawString(Minecraft.getInstance().font, String.valueOf(protect), screenWidth / 2 - 91, screenHeight * 25 / 32, 0x6091EC);
    }
}
