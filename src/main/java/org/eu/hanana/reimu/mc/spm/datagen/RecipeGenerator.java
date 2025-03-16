package org.eu.hanana.reimu.mc.spm.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.eu.hanana.reimu.mc.spm.Util;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import org.eu.hanana.reimu.mc.spm.recipe.FilterRecipe;
import org.eu.hanana.reimu.mc.spm.recipe.RecipeRegisterHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegisterHandler.ITEM_FILTER)
                .pattern(" I ")
                .pattern("IEI")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);
        SpecialRecipeBuilder.special(FilterRecipe::new).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MODID,"filter_upgrade"));
    }
}