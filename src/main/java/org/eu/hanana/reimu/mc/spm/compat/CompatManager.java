package org.eu.hanana.reimu.mc.spm.compat;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import org.eu.hanana.reimu.mc.spm.compat.tlm.TlmCompat;

import java.util.HashMap;
import java.util.Map;

public class CompatManager {
    public final Map<ResourceLocation,ICompat> iCompatMap = new HashMap<>();
    public CompatManager(){
        iCompatMap.put(TlmCompat.LOCATION,new TlmCompat());
    }
    public ICompat get(ResourceLocation resourceLocation){
        return iCompatMap.get(resourceLocation);
    }
    public void register(IEventBus modEventBus, ModContainer modContainer){
        for (ICompat value : iCompatMap.values()) {
            if (value.isModInstalled()){
                value.register(modEventBus,modContainer);
            }
        }
    }
    public void event(Event event){
        for (ICompat value : iCompatMap.values()) {
            if (value.isModInstalled()){
                value.event(event);
            }
        }
    }
}
