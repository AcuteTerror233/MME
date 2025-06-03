package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.MiteItems;
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
        itemModelGenerator.register(MiteItems.ADAMANTIUM_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ADAMANTIUM_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ANCIENT_METAL_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.COPPER_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.MITHRIL_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_HELMET,  Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.RUSTED_IRON_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_CHAINMAIL_HELMET, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_CHAINMAIL_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_CHAINMAIL_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.SILVER_CHAINMAIL_BOOTS, Models.GENERATED);
        itemModelGenerator.register(MiteItems.BANANA, Models.GENERATED);
        itemModelGenerator.register(MiteItems.BLUEBERRIE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.CHOCOLATE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.DOUGH, Models.GENERATED);
        itemModelGenerator.register(MiteItems.LEMON, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ONION, Models.GENERATED);
        itemModelGenerator.register(MiteItems.ORANGE, Models.GENERATED);
        itemModelGenerator.register(MiteItems.WORM_COOKED, Models.GENERATED);
        itemModelGenerator.register(MiteItems.WORM_RAW, Models.GENERATED);
    }
}
