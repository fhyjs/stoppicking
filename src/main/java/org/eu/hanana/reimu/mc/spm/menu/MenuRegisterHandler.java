package org.eu.hanana.reimu.mc.spm.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class MenuRegisterHandler {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<FilterConfigMenu>> Filter_Config_Menu =
            MENUS.register("filter_config",
                    () -> new MenuType<>(FilterConfigMenu::new, FeatureFlags.DEFAULT_FLAGS)
            );

    public static void register(IEventBus modEventBus){
        MENUS.register(modEventBus);
    }
}
