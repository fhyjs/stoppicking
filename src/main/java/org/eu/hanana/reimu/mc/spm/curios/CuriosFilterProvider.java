package org.eu.hanana.reimu.mc.spm.curios;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.concurrent.CompletableFuture;

public class CuriosFilterProvider extends CuriosDataProvider {

    public CuriosFilterProvider(String modId, PackOutput output,
                              ExistingFileHelper fileHelper,
                              CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        //this.createSlot("fitter")
        //        .dropRule(ICurio.DropRule.DEFAULT)
        //        .addCosmetic(true);
        this.createEntities("fitter")
                .addPlayer()
                .addSlots("ring");
    }
}