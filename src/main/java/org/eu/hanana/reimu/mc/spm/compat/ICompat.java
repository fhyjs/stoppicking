package org.eu.hanana.reimu.mc.spm.compat;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public interface ICompat {
    boolean isModInstalled();

    void register(IEventBus modEventBus, ModContainer modContainer);

    default void event(Event event){}
}
