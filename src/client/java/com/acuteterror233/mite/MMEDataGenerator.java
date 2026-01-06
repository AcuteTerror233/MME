package com.acuteterror233.mite;

import com.acuteterror233.mite.datagen.*;
import com.acuteterror233.mite.world.biome.MMEBiomes;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import com.acuteterror233.mite.world.gen.feature.MMEConfiguredFeatures;
import com.acuteterror233.mite.world.gen.feature.MMEPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class MMEDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(MMEModelProvider::new);
		pack.addProvider(MMEItemTagProvider::new);
        pack.addProvider(MMERecipeGenerator::new);
        pack.addProvider(MMEBlockTagProvider::new);
        pack.addProvider(MMEBlockLootTableProvider::new);
        pack.addProvider(MMEDynamicRegistry::new);
        pack.addProvider(MMEBiomeTagProvider::new);
        pack.addProvider(Zh_cnLanguageProvider::new);
        pack.addProvider(En_usLanguageProvider::new);
	}
    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder){
        registryBuilder.add(Registries.DIMENSION_TYPE, MMEDimensionTypeRegistrar::bootstrap);
        registryBuilder.add(Registries.BIOME, MMEBiomes::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, MMEPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, MMEConfiguredFeatures::bootstrap);
    }
}
