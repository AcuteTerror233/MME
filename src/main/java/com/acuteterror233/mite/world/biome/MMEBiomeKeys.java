package com.acuteterror233.mite.world.biome;

import com.acuteterror233.mite.MME;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

/**
 * MME biome resource keys, all in the {@code mme} namespace; the four underground biomes are
 * registered by {@link MMEBiomes}.
 */
public class MMEBiomeKeys {
    /** Catch-all underground cave biome of the underground dimension. */
    public static final ResourceKey<Biome> UNDERGROUND = key("underground");
    /** Underground variant of the dripstone caves biome. */
    public static final ResourceKey<Biome> DRIPSTONE_CAVES = key("dripstone_caves");
    /** Underground variant of the lush caves biome. */
    public static final ResourceKey<Biome> LUSH_CAVES = key("lush_caves");
    /** Underground variant of the deep dark biome. */
    public static final ResourceKey<Biome> DEEP_DARK = key("deep_dark");
    /**
     * Creates a biome resource key under the MME namespace.
     *
     * @param id path of the biome id
     * @return the resource key for {@code mme:<id>} in {@code minecraft:biome}
     */
    public static ResourceKey<Biome> key(String id){
        return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MME.MOD_ID,id));
    }
}
