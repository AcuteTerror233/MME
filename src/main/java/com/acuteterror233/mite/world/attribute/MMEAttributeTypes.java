package com.acuteterror233.mite.world.attribute;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.AttributeType;

public interface MMEAttributeTypes {
    AttributeType<SpecialMoonPhase> SPECIAL_MOON_PHASE = register("special_moon_phase", AttributeType.ofNotInterpolated(SpecialMoonPhase.CODEC));

    static void init() {

    }

    static <Value> AttributeType<Value> register(final String name, final AttributeType<Value> type) {
        Registry.register(BuiltInRegistries.ATTRIBUTE_TYPE, Identifier.fromNamespaceAndPath(MME.MOD_ID, name), type);
        return type;
    }
}
