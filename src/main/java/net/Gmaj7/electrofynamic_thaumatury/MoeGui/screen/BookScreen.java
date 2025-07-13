package net.Gmaj7.electrofynamic_thaumatury.MoeGui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BookScreen extends Screen {
    private int page;
    private int maxPage;
    protected BookScreen(Component title, int page) {
        super(title);
        this.page = page <= 0 ? 1 : page;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if(page == 1){
            renderMenu();
        }
    }

    private void renderMenu() {

    }
}
