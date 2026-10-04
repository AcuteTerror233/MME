package com.acuteterror233.mite.world.gen.feature;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * Overworld configured feature registration.
 * Defines new mineral and vegetation features for the overworld (e.g., blueberry bushes).
 */
public class OverworldConfiguredFeatures {
    /** Resource key of the blueberry bush feature. */
    public static final ResourceKey<Feature> BLUE_BERRY_BUSH = createKey("blue_berry_bush");

    /** Registers the blueberry bush as a simple-block feature placed at fully-grown age (AGE = 3). */
    public static void bootstrap(BootstrapContext<Feature> featureRegisterable){
        featureRegisterable.register(
                BLUE_BERRY_BUSH,
                new SimpleBlockFeature(
                        BlockStateProvider.of(MMEBlocks.BLUE_BERRY_BUSH.defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3))
                )
        );
    }
    /** Creates a feature resource key under the MME namespace. */
    public static ResourceKey<Feature> createKey(String string) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(MME.MOD_ID, string));
    }

}
