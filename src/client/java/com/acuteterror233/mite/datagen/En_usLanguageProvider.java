package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.MiteItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class En_usLanguageProvider extends FabricLanguageProvider {

    public En_usLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {translationBuilder.add("itemGroup.at_mite.item_group","Minecraft is too easy!");
        translationBuilder.add(MiteItems.ADAMANTIUM_HELMET,"Adamantium Helmet");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHESTPLATE,"Adamantium Chestplate");
        translationBuilder.add(MiteItems.ADAMANTIUM_LEGGINGS,"Adamantium Leggings");
        translationBuilder.add(MiteItems.ADAMANTIUM_BOOTS,"Adamantium Boots");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_HELMET,"Adamantium Chainmail Helmet");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE,"Adamantium Chainmail Chestplate");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_LEGGINGS,"Adamantium Chainmail Leggings");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_BOOTS,"Adamantium Chainmail Boots");
        translationBuilder.add(MiteItems.ANCIENT_METAL_HELMET,"Ancient Metal Helmet");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHESTPLATE,"Ancient Metal Chestplate");
        translationBuilder.add(MiteItems.ANCIENT_METAL_LEGGINGS,"Ancient Metal Leggings");
        translationBuilder.add(MiteItems.ANCIENT_METAL_BOOTS,"Ancient Metal Boots");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_HELMET,"Ancient Metal Chainmail Helmet");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,"Ancient Metal Chainmail Chestplate");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS,"Ancient Metal Chainmail Leggings");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_BOOTS,"Ancient Metal Chainmail Boots");
        translationBuilder.add(MiteItems.COPPER_HELMET,"Copper Helmet");
        translationBuilder.add(MiteItems.COPPER_CHESTPLATE,"Copper Chestplate");
        translationBuilder.add(MiteItems.COPPER_LEGGINGS,"Copper Leggings");
        translationBuilder.add(MiteItems.COPPER_BOOTS,"Copper Boots");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_HELMET,"Copper Chainmail Helmet");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_CHESTPLATE,"Copper Chainmail Chestplate");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_LEGGINGS,"Copper Chainmail Leggings");
        translationBuilder.add(MiteItems.MITHRIL_HELMET, "Mithril Helmet");
        translationBuilder.add(MiteItems.MITHRIL_CHESTPLATE, "Mithril Chestplate");
        translationBuilder.add(MiteItems.MITHRIL_LEGGINGS, "Mithril Leggings");
        translationBuilder.add(MiteItems.MITHRIL_BOOTS, "Mithril Boots");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_HELMET, "Mithril Chainmail Helmet");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_CHESTPLATE, "Mithril Chainmail Chestplate");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_LEGGINGS, "Mithril Chainmail Leggings");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_BOOTS, "Mithril Chainmail Boots");
        translationBuilder.add(MiteItems.RUSTED_IRON_HELMET, "Rusted Iron Helmet");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHESTPLATE, "Rusted Iron Chestplate");
        translationBuilder.add(MiteItems.RUSTED_IRON_LEGGINGS, "Rusted Iron Leggings");
        translationBuilder.add(MiteItems.RUSTED_IRON_BOOTS, "Rusted Iron Boots");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_HELMET, "Rusted Iron Chainmail Helmet");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE, "Rusted Iron Chainmail Chestplate");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_LEGGINGS, "Rusted Iron Chainmail Leggings");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_BOOTS, "Rusted Iron Chainmail Boots");
        translationBuilder.add(MiteItems.SILVER_HELMET, "Silver Helmet");
        translationBuilder.add(MiteItems.SILVER_CHESTPLATE, "Silver Chestplate");
        translationBuilder.add(MiteItems.SILVER_LEGGINGS, "Silver Leggings");
        translationBuilder.add(MiteItems.SILVER_BOOTS, "Silver Boots");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_HELMET, "Silver Chainmail Helmet");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_CHESTPLATE, "Silver Chainmail Chestplate");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_LEGGINGS, "Silver Chainmail Leggings");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_BOOTS, "Silver Chainmail Boots");
        translationBuilder.add(MiteItems.BANANA, "Banana");
        translationBuilder.add(MiteItems.BLUEBERRIE, "Blueberry");
        translationBuilder.add(MiteItems.CHEESE, "Cheese");
        translationBuilder.add(MiteItems.CHOCOLATE, "Chocolate");
        translationBuilder.add(MiteItems.DOUGH, "Dough");
        translationBuilder.add(MiteItems.ONION, "Onion");
        translationBuilder.add(MiteItems.LEMON, "Lemon");
        translationBuilder.add(MiteItems.ORANGE, "Orange");
        translationBuilder.add(MiteItems.WORM_COOKED, "Worm Cooked");
        translationBuilder.add(MiteItems.WORM_RAW, "Worm Raw");
    }
}
