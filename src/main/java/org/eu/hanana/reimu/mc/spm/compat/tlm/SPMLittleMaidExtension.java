package org.eu.hanana.reimu.mc.spm.compat.tlm;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.item.bauble.BaubleManager;
import net.neoforged.api.distmarker.OnlyIn;
import org.eu.hanana.reimu.mc.spm.StopPickMod;

@LittleMaidExtension
public class SPMLittleMaidExtension implements ILittleMaid {
    public SPMLittleMaidExtension(){
        ((TlmCompat) StopPickMod.COMPAT_MANAGER.get(TlmCompat.LOCATION)).spmLittleMaidExtension=this;
    }

    @Override
    public void bindMaidBauble(BaubleManager manager) {
        ILittleMaid.super.bindMaidBauble(manager);
        BaubleRegister.init(manager);
    }
}
