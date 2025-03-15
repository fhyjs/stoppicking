package org.eu.hanana.reimu.mc.spm.item.component;

import com.google.gson.Gson;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilterData {
    public final String mode;
    public final int size;
    public final List<ResourceLocation> itemNames;
    public static final Codec<FilterData> CODEC = RecordCodecBuilder.create(fitterDataInstance -> {
        return fitterDataInstance
                .group(
                        Codec.STRING.fieldOf("mode").forGetter(FilterData::getMode),
                        Codec.INT.fieldOf("size").forGetter(FilterData::getSize),
                        Codec.list(ResourceLocation.CODEC).fieldOf("items").forGetter(FilterData::getItemNames)
                )
                .apply(fitterDataInstance, FilterData::new);
    });

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            if (other instanceof FilterData filterData) {
                return filterData.mode.equals(this.mode)&& filterData.itemNames.equals(this.itemNames);
            }else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        return 31 * mode.hashCode() + itemNames.hashCode();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
