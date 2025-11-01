package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.At_mite;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class AtBiomeKeys {
    public static final RegistryKey<Biome> UNDERGROUND = keyOf("underground");
    public static final RegistryKey<Biome> DRIPSTONE_CAVES = keyOf("dripstone_caves");
    public static final RegistryKey<Biome> LUSH_CAVES = keyOf("lush_caves");
    public static final RegistryKey<Biome> DEEP_DARK = keyOf("deep_dark");
    public static RegistryKey<Biome> keyOf(String id){
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(At_mite.MOD_ID,id));
    }
}
