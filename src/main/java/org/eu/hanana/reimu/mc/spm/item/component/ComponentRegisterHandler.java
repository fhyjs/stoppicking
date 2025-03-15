package org.eu.hanana.reimu.mc.spm.item.component;

import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.core.registries.Registries.DATA_COMPONENT_TYPE;
import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class ComponentRegisterHandler {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(DATA_COMPONENT_TYPE,MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilterData>> ITEM_FILTER = COMPONENTS.registerComponentType("filter_list", builder -> builder.persistent(FilterData.CODEC));
    public static void register(IEventBus modEventBus){
        COMPONENTS.register(modEventBus);
    }
}
