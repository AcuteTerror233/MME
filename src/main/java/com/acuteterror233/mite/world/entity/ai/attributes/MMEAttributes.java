package com.acuteterror233.mite.world.entity.ai.attributes;

import com.acuteterror233.mite.MME;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class MMEAttributes {

    private static Holder<Attribute> register(String id, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, id), attribute);
    }

    public static void bootstrap() {

    }
}
