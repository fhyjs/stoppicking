package org.eu.hanana.reimu.mc.spm.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.eu.hanana.reimu.mc.spm.menu.FilterConfigMenu;

public class FilterConfigScreen extends AbstractContainerScreen<FilterConfigMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final ResourceLocation DEMO_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/demo_background.png");
    public FilterConfigScreen(FilterConfigMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_BACKGROUND, i, j + 71, 0, 126, this.imageWidth, 96);
        guiGraphics.blit(DEMO_BACKGROUND, i, j, 0, 0, this.imageWidth-18, 71);
        guiGraphics.blit(DEMO_BACKGROUND, i + this.imageWidth - 18, j, 230, 0, 20, 71);
        for (int i1 = menu.filterShotStart; i1 < menu.filterShotAmount+menu.filterShotStart; i1++) {
            var slot = menu.getSlot(i1);
            guiGraphics.blit(CONTAINER_BACKGROUND, i + slot.x -1, j + slot.y -1, 7, 17, 18, 18);
        }
    }
}
