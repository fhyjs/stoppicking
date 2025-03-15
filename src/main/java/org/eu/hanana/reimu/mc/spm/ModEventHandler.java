package org.eu.hanana.reimu.mc.spm;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.eu.hanana.reimu.mc.spm.curios.CuriosFilterProvider;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.screen.ScreenRegisterHandler;


public class ModEventHandler {
    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                new CuriosFilterProvider(
                        StopPickMod.MODID,
                        event.getGenerator().getPackOutput(),
                        event.getExistingFileHelper(),
                        event.getLookupProvider()
                )
        );
    }
    @SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ItemRegisterHandler.NOPICKUP_TAB.get()) {
            event.accept(ItemRegisterHandler.ITEM_FILTER);
        }
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerScreens(RegisterMenuScreensEvent event) {
        ScreenRegisterHandler.register(event);
    }
}
