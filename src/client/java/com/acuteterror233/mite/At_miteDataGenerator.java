package com.acuteterror233.mite;

import com.acuteterror233.mite.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class At_miteDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(Zh_cnLanguageProvider::new);
		pack.addProvider(En_usLanguageProvider::new);
		pack.addProvider(At_RecipeGenerator::new);
		pack.addProvider(ItemTagProvider::new);
	}
}
