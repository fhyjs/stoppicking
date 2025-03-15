package org.eu.hanana.reimu.mc.spm;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import org.eu.hanana.reimu.mc.spm.item.ItemRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
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
        List<Item> dataItem = new ArrayList<>();
        var white=true;
        var whited=false;
        for (SlotResult curio : curios) {
            FilterData filterData = curio.stack().get(ComponentRegisterHandler.ITEM_FILTER);
            if (filterData!=null){
                if ((white=filterData.mode.equals("white"))&&!whited) {
                    whited=true;
                }
                for (ResourceLocation itemName : filterData.itemNames) {
                    dataItem.add(BuiltInRegistries.ITEM.get(itemName));
                }
            }
        }
        if (!white){
            for (Item item1 : dataItem) {
                if (item.getItem().is(item1)){
                    event.setCanPickup(TriState.FALSE);
                    item.setPickUpDelay(10);
                }
            }
        }else {
            var fl = false;
            for (Item item1 : dataItem) {
                if (item.getItem().is(item1)){
                    fl=true;
                    break;
                }
            }
            if (!fl&&whited){
                item.setPickUpDelay(10);
                event.setCanPickup(TriState.FALSE);
            }
        }
    }
}
