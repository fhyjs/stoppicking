package org.eu.hanana.reimu.mc.spm.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class ItemRegisterHandler {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "multikeybindings" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredItem<Item> ITEM_FILTER = ITEMS.registerItem("filter", ItemFilter::new);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NOPICKUP_TAB = CREATIVE_MODE_TABS.register("stop_picking", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.stoppicking.filter")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ITEM_FILTER.get().getDefaultInstance())
            .build());
    public static void register(IEventBus modEventBus){
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
