package org.eu.hanana.reimu.mc.spm.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.compat.ysm.YsmCompat;
import com.github.tartaricacid.touhoulittlemaid.entity.info.ServerCustomPackLoader;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.registry.CommonRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.eu.hanana.reimu.mc.spm.StopPickMod;
import org.eu.hanana.reimu.mc.spm.Util;
import org.eu.hanana.reimu.mc.spm.compat.ICompat;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public class TlmCompat implements ICompat {
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(MODID,"tlm");
    public SPMLittleMaidExtension spmLittleMaidExtension;
    @Override
    public boolean isModInstalled() {
        return ModList.get().isLoaded("touhou_little_maid");
    }

    @Override
    public void register(IEventBus modEventBus, ModContainer modContainer) {
        StopPickMod.LOGGER.info("touhou_little_maid mod found!");
    }

    public void onMaidPicking(EntityMaid entityMaid, ItemEntity entityItem, boolean simulate, CallbackInfoReturnable<Boolean> cir) {
        List<ItemStack> itemStacks= new ArrayList<>();
        for (int i = 0; i < entityMaid.getMaidBauble().getSlots(); i++) {
            var is = entityMaid.getMaidBauble().getStackInSlot(i);
            if (is.has(ComponentRegisterHandler.ITEM_FILTER))
                itemStacks.add(is);
        }
        if (itemStacks.isEmpty()) return;
        var result = Util.getAllItemFromFilter(itemStacks);
        var white = result.getB();
        var items = result.getA();
        var hit=false;
        for (Item item : items) {
            if (entityItem.getItem().is(item)) {
                hit=true;
                break;
            }
        }
        if (!white){
            if (hit) {
                entityItem.setPickUpDelay(10);
                cir.cancel();
            }
        }else {
            if (!hit){
                entityItem.setPickUpDelay(10);
                cir.cancel();
            }
        }
    }
}
