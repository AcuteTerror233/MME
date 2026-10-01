package com.acuteterror233.mite.world.attribute;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttribute;

public interface MMEEnvironmentAttributes {
    EnvironmentAttribute<SpecialMoonPhase> SPECIAL_MOON_PHASE = register(
            "visual/special_moon_phase", EnvironmentAttribute.builder(MMEAttributeTypes.SPECIAL_MOON_PHASE).defaultValue(SpecialMoonPhase.NORMAL_MOON).syncable()
    );
    static void init(){

    }

    private static <Value> EnvironmentAttribute<Value> register(final String id, final EnvironmentAttribute.Builder<Value> attributeBuilder) {
        EnvironmentAttribute<Value> attribute = attributeBuilder.build();
        Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), attribute);
        return attribute;
    }
}
