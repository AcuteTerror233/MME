package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.Items;
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
        translationBuilder.add(Items.ADAMANTIUM_HELMET,"Adamantium Helmet");
        translationBuilder.add(Items.ADAMANTIUM_CHESTPLATE,"Adamantium Chestplate");
        translationBuilder.add(Items.ADAMANTIUM_LEGGINGS,"Adamantium Leggings");
        translationBuilder.add(Items.ADAMANTIUM_BOOTS,"Adamantium Boots");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_HELMET,"Adamantium Chainmail Helmet");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_CHESTPLATE,"Adamantium Chainmail Chestplate");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_LEGGINGS,"Adamantium Chainmail Leggings");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_BOOTS,"Adamantium Chainmail Boots");
        translationBuilder.add(Items.ANCIENT_METAL_HELMET,"Ancient Metal Helmet");
        translationBuilder.add(Items.ANCIENT_METAL_CHESTPLATE,"Ancient Metal Chestplate");
        translationBuilder.add(Items.ANCIENT_METAL_LEGGINGS,"Ancient Metal Leggings");
        translationBuilder.add(Items.ANCIENT_METAL_BOOTS,"Ancient Metal Boots");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_HELMET,"Ancient Metal Chainmail Helmet");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,"Ancient Metal Chainmail Chestplate");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_LEGGINGS,"Ancient Metal Chainmail Leggings");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_BOOTS,"Ancient Metal Chainmail Boots");
        translationBuilder.add(Items.COPPER_HELMET,"Copper Helmet");
        translationBuilder.add(Items.COPPER_CHESTPLATE,"Copper Chestplate");
        translationBuilder.add(Items.COPPER_LEGGINGS,"Copper Leggings");
        translationBuilder.add(Items.COPPER_BOOTS,"Copper Boots");
        translationBuilder.add(Items.COPPER_CHAINMAIL_HELMET,"Copper Chainmail Helmet");
        translationBuilder.add(Items.COPPER_CHAINMAIL_CHESTPLATE,"Copper Chainmail Chestplate");
        translationBuilder.add(Items.COPPER_CHAINMAIL_LEGGINGS,"Copper Chainmail Leggings");
    }
}
