package com.acuteterror233.mite.component;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class At_DataComponentTypes {
    public static final ComponentType<Float> INTERACTION_DISTANCE = register("interaction_distance", builder -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));


    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.<T>builder()).build());
    }
}
