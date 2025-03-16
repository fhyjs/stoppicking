package org.eu.hanana.reimu.mc.spm.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.eu.hanana.reimu.mc.spm.Util;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterRecipe extends CustomRecipe {
    public Map<Item,Integer> upgradeLevels = new HashMap<>();
    public FilterRecipe(CraftingBookCategory category) {
        super(category);
        upgradeLevels.put(Items.IRON_INGOT,6);
        upgradeLevels.put(Items.GOLD_INGOT,9);
        upgradeLevels.put(Items.DIAMOND,18);
        upgradeLevels.put(Items.NETHERITE_INGOT,27);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.isEmpty()) return false;
        var target = (ItemStack) null;
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : input.items()) {
            if (item.isEmpty()) continue;
            if (item.has(ComponentRegisterHandler.ITEM_FILTER)){
                target=item;
            }else {
                items.add(item);
            }
        }
        if (items.size() != 1) return false;
        var upgrade = items.getFirst();

        return target!=null&&upgradeLevels.containsKey(upgrade.getItem());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        var target = (ItemStack) null;
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : input.items()) {
            if (item.isEmpty()) continue;
            if (item.has(ComponentRegisterHandler.ITEM_FILTER)){
                target=item;
            }else {
                items.add(item);
            }
        }
        var upgrade = items.getFirst();
        FilterData clone = target.get(ComponentRegisterHandler.ITEM_FILTER).clone();
        clone.size=upgradeLevels.get(upgrade.getItem());
        return Util.writeData(target.copy(),ComponentRegisterHandler.ITEM_FILTER.get(),clone);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width>=3&&height>=3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegisterHandler.FILTER_UPGRADE_RECIPE_SERIALIZER.get();
    }
}
