package org.eu.hanana.reimu.mc.spm.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.eu.hanana.reimu.mc.spm.StopPickMod;
import org.eu.hanana.reimu.mc.spm.compat.tlm.bauble.FilterBauble;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;

import java.lang.reflect.Field;

public class BaubleRegister {
    public static void init(BaubleManager manager){
        manager.bind(ItemRegisterHandler.ITEM_FILTER,new FilterBauble());
    }
}
