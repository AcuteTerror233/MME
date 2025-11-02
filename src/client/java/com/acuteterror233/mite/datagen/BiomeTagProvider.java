package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.world.biome.AtBiomeKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeTagProvider extends FabricTagProvider<Biome>{
    public BiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BiomeTags.MINESHAFT_HAS_STRUCTURE)
                .add(AtBiomeKeys.DEEP_DARK)
                .add(AtBiomeKeys.DRIPSTONE_CAVES)
                .add(AtBiomeKeys.LUSH_CAVES)
                .add(AtBiomeKeys.UNDERGROUND);
    }
}
