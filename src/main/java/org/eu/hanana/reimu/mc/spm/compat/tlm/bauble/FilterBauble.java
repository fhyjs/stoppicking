package org.eu.hanana.reimu.mc.spm.compat.tlm.bauble;

import com.github.tartaricacid.touhoulittlemaid.api.bauble.IMaidBauble;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public class FilterBauble implements IMaidBauble {
    @Override
    public void onTick(EntityMaid maid, ItemStack baubleItem) {

    }

    @Override
    public String getChatBubbleId() {
        return "item_filter";
    }
}
