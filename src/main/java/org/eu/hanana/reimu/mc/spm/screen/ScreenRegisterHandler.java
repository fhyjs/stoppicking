package org.eu.hanana.reimu.mc.spm.screen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.eu.hanana.reimu.mc.spm.menu.MenuRegisterHandler;

@OnlyIn(Dist.CLIENT)
public class ScreenRegisterHandler {
    public static void register(RegisterMenuScreensEvent event){
        event.register(MenuRegisterHandler.Filter_Config_Menu.get(), FilterConfigScreen::new);
    }
}
