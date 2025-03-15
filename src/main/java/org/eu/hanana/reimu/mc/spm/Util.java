package org.eu.hanana.reimu.mc.spm;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;

import java.util.List;

public class Util {
    public static <T>ItemStack writeData(ItemStack stack, DataComponentType<T> type,T data){
        stack.set(type,data);
        return stack;
    }
}
