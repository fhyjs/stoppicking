package org.eu.hanana.reimu.mc.spm;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.eu.hanana.reimu.mc.spm.curios.CuriosFilterProvider;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import org.eu.hanana.reimu.mc.spm.network.SetFilterModeData;
import org.eu.hanana.reimu.mc.spm.screen.ScreenRegisterHandler;

import java.util.List;


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
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ItemRegisterHandler.NOPICKUP_TAB.get()) {
            event.accept(ItemRegisterHandler.ITEM_FILTER.get().getDefaultInstance().copy());
            event.accept(Util.writeData(ItemRegisterHandler.ITEM_FILTER.get().getDefaultInstance().copy(),ComponentRegisterHandler.ITEM_FILTER.get(),new FilterData("black",9,List.of())));
            event.accept(Util.writeData(ItemRegisterHandler.ITEM_FILTER.get().getDefaultInstance().copy(),ComponentRegisterHandler.ITEM_FILTER.get(),new FilterData("black",18,List.of())));
            event.accept(Util.writeData(ItemRegisterHandler.ITEM_FILTER.get().getDefaultInstance().copy(),ComponentRegisterHandler.ITEM_FILTER.get(),new FilterData("black",27,List.of())));
        }
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerScreens(RegisterMenuScreensEvent event) {
        ScreenRegisterHandler.register(event);
    }
    @SubscribeEvent
    public void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);
        registrar.playBidirectional(
                SetFilterModeData.TYPE,
                SetFilterModeData.STREAM_CODEC,
                SetFilterModeData::handler
        );
    }
}
