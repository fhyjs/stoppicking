package org.eu.hanana.reimu.mc.spm;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static <T>ItemStack writeData(ItemStack stack, DataComponentType<T> type,T data){
        stack.set(type,data);
        return stack;
    }
    public static Tuple<List<Item>,Boolean> getAllItemFromFilter(List<ItemStack> stacks){
        boolean white = true;
        List<Item> items = new ArrayList<>();
        for (ItemStack stack : stacks) {
            if (!stack.has(ComponentRegisterHandler.ITEM_FILTER)) continue;
            var data = stack.get(ComponentRegisterHandler.ITEM_FILTER);
            if (white&&!data.mode.equals("white")) white=false;
            for (ResourceLocation itemName : data.itemNames) {
                Item item = BuiltInRegistries.ITEM.get(itemName);
                items.add(item);
            }
        }
        return new Tuple<>(ImmutableList.copyOf(items),white);
    }
}
