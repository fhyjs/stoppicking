package org.eu.hanana.reimu.mc.spm.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;
import org.eu.hanana.reimu.mc.spm.item.component.FilterData;
import org.eu.hanana.reimu.mc.spm.menu.FilterConfigMenu;
import org.eu.hanana.reimu.mc.spm.menu.MenuRegisterHandler;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class ItemFilter extends Item implements ICurioItem {

    public ItemFilter(Properties properties) {
        super(properties.stacksTo(1).component(ComponentRegisterHandler.ITEM_FILTER.get(),new FilterData("black",3, List.of())));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("tooltip.stoppicking.gui").withColor(ChatFormatting.GOLD.getColor()));
        var data = stack.get(ComponentRegisterHandler.ITEM_FILTER.get());
        if (data==null) return;
        tooltipComponents.add(Component.translatable("tooltip.stoppicking.size",data.itemNames.size(),data.size).withColor(ChatFormatting.GRAY.getColor()));
        tooltipComponents.add(Component.translatable("tooltip.stoppicking.mode").append(Component.translatable("gui.stoppicking.filter."+data.mode).withColor(ChatFormatting.BLUE.getColor())));

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var is = player.getItemInHand(usedHand);
        if (player.isShiftKeyDown()&&is.has(ComponentRegisterHandler.ITEM_FILTER.get())){
            if (!level.isClientSide()){
                player.openMenu(new SimpleMenuProvider((containerId, playerInventory, player1) -> new FilterConfigMenu(containerId,playerInventory), Component.translatable("gui.stoppicking.filter.title")));
            }
            return InteractionResultHolder.success(is);
        }
        return super.use(level, player, usedHand);
    }
}