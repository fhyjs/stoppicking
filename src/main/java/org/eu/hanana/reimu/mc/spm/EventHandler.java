package org.eu.hanana.reimu.mc.spm;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class EventHandler {
    @SubscribeEvent
    public void onPickingUpItem(ItemEntityPickupEvent.Pre event){
        var player = event.getPlayer();
        var item = event.getItemEntity();
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
        if (curiosInventory.isEmpty()) return;
        var cInv = curiosInventory.get();
        List<SlotResult> curios = cInv.findCurios(ItemRegisterHandler.ITEM_FILTER.asItem());
        for (SlotResult curio : curios) {
            //curio.stack().get()
        }
    }
}
