package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class MMEBiomeKeys {
    public static final ResourceKey<Biome> UNDERGROUND = key("underground");
    public static final ResourceKey<Biome> DRIPSTONE_CAVES = key("dripstone_caves");
    public static final ResourceKey<Biome> LUSH_CAVES = key("lush_caves");
    public static final ResourceKey<Biome> DEEP_DARK = key("deep_dark");
    public static ResourceKey<Biome> key(String id){
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MME.MOD_ID,id));
    }
}
