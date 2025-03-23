package org.eu.hanana.reimu.mc.spm.mixin;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.entity.item.ItemEntity;
import org.eu.hanana.reimu.mc.spm.StopPickMod;
import org.eu.hanana.reimu.mc.spm.compat.tlm.TlmCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityMaid.class)
public class MixinEntityMaid {
    @Inject(method = {"pickupItem"},cancellable = true,at=@At("HEAD"))
    public void pickupItem(ItemEntity entityItem, boolean simulate, CallbackInfoReturnable<Boolean> cir){
        ((TlmCompat) StopPickMod.COMPAT_MANAGER.get(TlmCompat.LOCATION)).onMaidPicking((EntityMaid)((Object) this),entityItem,simulate,cir);
    }
}
