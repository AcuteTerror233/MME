package com.acuteterror233.mite.world.attribute;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.world.level.SpecialMoonPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.attribute.EnvironmentAttribute;

/**
 * Registry of MME custom environment attributes (world-level dimension values readable from
 * {@code Level#environmentAttributes()}). Call {@link #init()} to force class-loading/registration.
 */
public interface MMEEnvironmentAttributes {
    /** Current moon of the {@link SpecialMoonPhase} cycle; synced to clients, defaults to {@link SpecialMoonPhase#NORMAL_MOON}. */
    EnvironmentAttribute<SpecialMoonPhase> SPECIAL_MOON_PHASE = register(
            "visual/special_moon_phase", EnvironmentAttribute.builder(MMEAttributeTypes.SPECIAL_MOON_PHASE).defaultValue(SpecialMoonPhase.NORMAL_MOON).syncable()
    );
    /** No-op classloading hook that triggers static registration. */
    static void init(){

    }

    private static <Value> EnvironmentAttribute<Value> register(final String id, final EnvironmentAttribute.Builder<Value> attributeBuilder) {
        EnvironmentAttribute<Value> attribute = attributeBuilder.build();
        Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Identifier.fromNamespaceAndPath(MME.MOD_ID, id), attribute);
        return attribute;
    }
}
