package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.world.biome.MMEBiomeKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class MMEBiomeTagProvider extends FabricTagProvider<Biome>{
    public MMEBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        tag(BiomeTags.HAS_MINESHAFT)
                .add(MMEBiomeKeys.DEEP_DARK)
                .add(MMEBiomeKeys.DRIPSTONE_CAVES)
                .add(MMEBiomeKeys.LUSH_CAVES)
                .add(MMEBiomeKeys.UNDERGROUND);
        tag(BiomeTags.HAS_RUINED_PORTAL_MOUNTAIN)
                .add(MMEBiomeKeys.DEEP_DARK)
                .add(MMEBiomeKeys.DRIPSTONE_CAVES)
                .add(MMEBiomeKeys.LUSH_CAVES)
                .add(MMEBiomeKeys.UNDERGROUND);
    }
}
