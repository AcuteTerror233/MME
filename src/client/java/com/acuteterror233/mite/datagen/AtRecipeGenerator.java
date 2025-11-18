package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class AtRecipeGenerator extends FabricRecipeProvider {
    public AtRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                offerCompactingRecipe(RecipeCategory.BUILDING_BLOCKS, AtBlocks.ADAMANTIUM_BLOCK, AtItems.ADAMANTIUM_INGOT);
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
