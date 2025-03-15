package org.eu.hanana.reimu.mc.spm;

import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.menu.MenuRegisterHandler;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = StopPickMod.MODID)
public class StopPickMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "stoppicking";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public StopPickMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (multikeybindings) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(new EventHandler());
        modEventBus.register(new ModEventHandler());
        ItemRegisterHandler.register(modEventBus);
        ComponentRegisterHandler.register(modEventBus);
        MenuRegisterHandler.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

}
