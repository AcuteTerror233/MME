package com.acuteterror233.mite.component;

import com.acuteterror233.mite.Mme;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class AtDataComponentTypes {
    public static final ComponentType<Integer> CRAFTING_TIME = register("crafting_time", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<Integer> REQUIRED_COMBUSTION_GRADE = register("required_combustion_grade", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<Integer> EXPERIENCE = register("experience", builder -> builder.codec(Codecs.NON_NEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT));
    public static final ComponentType<Float> ORIGINAL_ARMOR = register("original_armor", builder -> builder.codec(Codecs.POSITIVE_FLOAT).packetCodec(PacketCodecs.FLOAT));
    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Mme.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
