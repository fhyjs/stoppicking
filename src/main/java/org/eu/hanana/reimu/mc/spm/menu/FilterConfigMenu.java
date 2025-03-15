package org.eu.hanana.reimu.mc.spm.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterConfigMenu extends AbstractContainerMenu {
    public final Inventory playerInventory;
    public final ItemStackHandler filterListInv = new ItemStackHandler(){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            var f = false;
            for (int i = 0; i < this.getSlots(); i++) {
                f=this.getStackInSlot(i).is(stack.getItem());
                if (f)
                    break;
            }

            return !f&&super.isItemValid(slot, stack);
        }
    };
    public int filterShotStart;
    public int filterShotAmount;
    public FilterConfigMenu(int containerId, Inventory playerInventory) {
        super(MenuRegisterHandler.Filter_Config_Menu.get(), containerId);
        this.playerInventory=playerInventory;
        var is = playerInventory.player.getMainHandItem();
        var data = is.get(ComponentRegisterHandler.ITEM_FILTER.get());
        if (data==null){
            playerInventory.player.sendSystemMessage(Component.literal("What?! Item in main hand doesn't have ITEM_FILTER component!").withColor(0xff1010));
            return;
        }
        int i = -18;
        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
        filterShotStart  = this.slots.size();
        filterShotAmount = data.getSize();
        filterListInv.setSize(filterShotAmount);

        int blocks = (int) Math.ceil(filterShotAmount / 9f); // 计算需要多少组（每组最多 9 个槽）
        List<Slot> slots1 = new ArrayList<>();
        int maxW = 0;
        int maxH = 0;

        for (int blk = 0; blk < blocks; blk++) { // 遍历每一组
            for (int row = 0; row < 3; row++) { // 每组 3 行
                for (int col = 0; col < 3; col++) { // 每行 3 列
                    var placedSlots = slots1.size();
                    if (placedSlots >= filterShotAmount) break; // 防止超出总槽数

                    int x = col * 18 + blk * 54;  // X 坐标
                    int y = row * 18; // Y 坐标，每组之间间隔 54
                    maxH=Math.max(maxH,row * 18+18);
                    maxW=Math.max(maxW,col * 18 + blk * 54+18);
                    slots1.add(new FSlotItemHandler(filterListInv, placedSlots, x, y));
                }
            }
        }
        for (Slot slot : slots1) {
            slot.x+=88-maxW/2;
            slot.y+=45-maxH/2;
        }
        slots1.forEach(this::addSlot);
        for (int i1 = 0; i1 < data.itemNames.size(); i1++) {
            var rl = data.itemNames.get(i1);
            if (i1>filterListInv.getSlots()-1) break;
            filterListInv.setStackInSlot(i1,BuiltInRegistries.ITEM.get(rl).getDefaultInstance());
        }
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId<0) {
            super.clicked(slotId, button, clickType, player);
            return;
        }
        if(clickType==ClickType.QUICK_CRAFT&&slotId>=filterShotStart&&slotId<filterShotStart+filterShotAmount){
            return;
        }
        var is = getSlot(slotId).getItem();
        //Disable click event on item in hand.
        if (is.equals(player.getItemInHand(InteractionHand.MAIN_HAND))){
            return;
        }
        super.clicked(slotId, button, clickType, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().has(ComponentRegisterHandler.ITEM_FILTER.get());
    }

    @Override
    public void removed(Player player) {
        var is = playerInventory.player.getMainHandItem();
        var data = is.get(ComponentRegisterHandler.ITEM_FILTER.get());
        var itemNames = new ArrayList<ResourceLocation>();
        for (int i = 0; i < filterListInv.getSlots(); i++) {
            var item = filterListInv.getStackInSlot(i);
            if (item.isEmpty()) continue;
            itemNames.add(BuiltInRegistries.ITEM.getKey(item.getItem()));
        }
        var newData = new FilterData(data.getMode(), data.getSize(), itemNames);
        is.set(ComponentRegisterHandler.ITEM_FILTER.get(),newData);
        super.removed(player);
    }

    private static class FSlotItemHandler extends SlotItemHandler{

        public FSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }
        @Override
        public ItemStack safeInsert(ItemStack stack, int increment) {
            super.safeInsert(stack.copy(), increment);
            return stack.copy();
        }
        @Override
        public ItemStack safeTake(int count, int decrement, Player player) {
            super.safeTake(count, decrement, player);
            return ItemStack.EMPTY;
        }
        @Override
        public Optional<ItemStack> tryRemove(int count, int decrement, Player player) {
            super.tryRemove(count, decrement, player);
            return Optional.of(ItemStack.EMPTY);
        }
    }
}
