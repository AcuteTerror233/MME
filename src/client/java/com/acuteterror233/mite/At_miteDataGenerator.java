package com.acuteterror233.mite;

import com.acuteterror233.mite.datagen.*;
import com.acuteterror233.mite.world.biome.AtBuiltinBiomes;
import com.acuteterror233.mite.world.gen.dimension.AtDimensionTypeRegistrar;
import com.acuteterror233.mite.world.gen.feature.AtConfiguredFeatures;
import com.acuteterror233.mite.world.gen.feature.AtPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class At_miteDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(Zh_cnLanguageProvider::new);
		pack.addProvider(En_usLanguageProvider::new);
		pack.addProvider(AtRecipeGenerator::new);
		pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(AtDynamicRegistry::new);
        pack.addProvider(BiomeTagProvider::new);
	}
    @Override
    public void buildRegistry(RegistryBuilder registryBuilder){
        registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, AtDimensionTypeRegistrar::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, AtBuiltinBiomes::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, AtPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, AtConfiguredFeatures::bootstrap);
    }
}
