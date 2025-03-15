package org.eu.hanana.reimu.mc.spm.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.eu.hanana.reimu.mc.spm.item.component.ComponentRegisterHandler;

import static org.eu.hanana.reimu.mc.spm.StopPickMod.MODID;

public record SetFilterModeData(String mode) implements CustomPacketPayload {
    
    public static final CustomPacketPayload.Type<SetFilterModeData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "setfiltermodedata"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, SetFilterModeData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SetFilterModeData::mode,
            SetFilterModeData::new
    );
    
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handler(SetFilterModeData payload, IPayloadContext iPayloadContext) {
        var data= iPayloadContext.player().getMainHandItem().get(ComponentRegisterHandler.ITEM_FILTER).clone();
        data.mode= payload.mode;
        iPayloadContext.player().getMainHandItem().set(ComponentRegisterHandler.ITEM_FILTER,data);
    }
}