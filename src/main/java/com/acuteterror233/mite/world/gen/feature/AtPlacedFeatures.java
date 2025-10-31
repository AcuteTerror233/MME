package com.acuteterror233.mite.world.gen.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.*;

public class AtPlacedFeatures {

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        UndergroundOrePlacedFeatures.bootstrap(featureRegisterable);
        UndergroundPlacedFeatures.bootstrap(featureRegisterable);
    }
}
