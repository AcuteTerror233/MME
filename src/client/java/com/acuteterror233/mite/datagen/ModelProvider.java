package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.Items;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;


public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(Items.ADAMANTIUM_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_BOOTS, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.ADAMANTIUM_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.ANCIENT_METAL_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_BOOTS, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(Items.COPPER_CHAINMAIL_BOOTS, Models.GENERATED);
    }
}
