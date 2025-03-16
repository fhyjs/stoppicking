package org.eu.hanana.reimu.mc.spm.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class RecipeRegisterHandler {
    public static final DeferredRegister<RecipeSerializer<?>> RecipeSerializers = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FILTER_UPGRADE_RECIPE_SERIALIZER = RecipeSerializers.register("filter_upgrade",resourceLocation -> new SimpleCraftingRecipeSerializer<>(FilterRecipe::new));
    public static void register(IEventBus modEventBus){
        RecipeSerializers.register(modEventBus);
    }
}
