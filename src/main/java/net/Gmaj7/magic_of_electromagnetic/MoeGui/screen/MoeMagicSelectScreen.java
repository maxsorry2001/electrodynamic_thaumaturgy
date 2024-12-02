package net.Gmaj7.magic_of_electromagnetic.MoeGui.screen;

import net.Gmaj7.magic_of_electromagnetic.MoeInit.MoeDataComponentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MoeMagicSelectScreen extends Screen {
    public MoeMagicSelectScreen(Component title) {
        super(title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Player player = Minecraft.getInstance().player;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Player player = Minecraft.getInstance().player;
        ItemStack itemStack = player.getMainHandItem();
        itemStack.set(MoeDataComponentTypes.MAGIC_SLOT, itemStack.get(MoeDataComponentTypes.MAGIC_SLOT) + 1);
        Minecraft.getInstance().setScreen(null);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
