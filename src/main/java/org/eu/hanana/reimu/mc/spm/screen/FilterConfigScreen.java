package org.eu.hanana.reimu.mc.spm.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.eu.hanana.reimu.mc.spm.StopPickMod;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import org.eu.hanana.reimu.mc.spm.menu.FilterConfigMenu;
import org.eu.hanana.reimu.mc.spm.network.SetFilterModeData;

import java.util.List;

public class FilterConfigScreen extends AbstractContainerScreen<FilterConfigMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final ResourceLocation DEMO_BACKGROUND = ResourceLocation.withDefaultNamespace("textures/gui/demo_background.png");
    private static final ResourceLocation BUTTONS_TEXTURE = ResourceLocation.fromNamespaceAndPath(StopPickMod.MODID,"textures/gui/button.png");
    protected FilterData filterData=new FilterData("",1, List.of());
    public FilterConfigScreen(FilterConfigMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        filterData= getMenu().playerInventory.player.getMainHandItem().get(ComponentRegisterHandler.ITEM_FILTER);
        clearWidgets();
        addRenderableWidget(Button.builder(Component.literal(""),(button)->{
            PacketDistributor.sendToServer(new SetFilterModeData(filterData.mode.equals("black")?"white":"black"));
        }).bounds(getGuiLeft()-23,getGuiTop()+22,20,20).tooltip(Tooltip.create(Component.translatable("gui.stoppicking.filter."+filterData.mode))).build());
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        var filterData= getMenu().playerInventory.player.getMainHandItem().get(ComponentRegisterHandler.ITEM_FILTER);
        if (filterData!=this.filterData){
            this.filterData=filterData;
            minecraft.tell(this::init);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BUTTONS_TEXTURE,getGuiLeft()-20,getGuiTop()+24,15,14,0,filterData.mode.equals("black")?64:0,64,64,512,512);
        this.renderTooltip(guiGraphics,mouseX,mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_BACKGROUND, i, j + 71, 0, 126, this.imageWidth, 96);
        guiGraphics.blit(DEMO_BACKGROUND, i, j, 0, 0, this.imageWidth-18, 71);
        guiGraphics.blit(DEMO_BACKGROUND, i + this.imageWidth - 18, j, 230, 0, 20, 71);
        guiGraphics.blit(DEMO_BACKGROUND, i - 30, j+15, 0, 0, 33, 55);
        guiGraphics.blit(DEMO_BACKGROUND, i - 30, j+60, 0, 150, 33, 55);
        for (int i1 = menu.filterShotStart; i1 < menu.filterShotAmount+menu.filterShotStart; i1++) {
            var slot = menu.getSlot(i1);
            guiGraphics.blit(CONTAINER_BACKGROUND, i + slot.x -1, j + slot.y -1, 7, 17, 18, 18);
        }
    }
}
