package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class Zh_cnLanguageProvider extends FabricLanguageProvider {

    public Zh_cnLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "zh_cn", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(Items.ADAMANTIUM_BOOTS,"艾德曼靴子");
        translationBuilder.add(Items.ADAMANTIUM_CHESTPLATE,"艾德曼胸甲");
        translationBuilder.add(Items.ADAMANTIUM_HELMET,"艾德曼头盔");
        translationBuilder.add(Items.ADAMANTIUM_LEGGINGS,"艾德曼护腿");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_BOOTS,"艾德曼链甲靴子");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_CHESTPLATE,"艾德曼链甲胸甲");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_HELMET,"艾德曼链甲头盔");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_LEGGINGS,"艾德曼链甲护腿");
        translationBuilder.add("itemGroup.at_mite.item_group","at_mite");
        translationBuilder.add(Items.ANCIENT_METAL_BOOTS,"远古金属靴子");
        translationBuilder.add(Items.ANCIENT_METAL_CHESTPLATE,"远古金属胸甲");
        translationBuilder.add(Items.ANCIENT_METAL_HELMET,"远古金属头盔");
        translationBuilder.add(Items.ANCIENT_METAL_LEGGINGS,"远古金属护腿");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_BOOTS,"锁链远古金属靴子");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,"锁链远古金属链甲胸甲");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_HELMET,"锁链远古金属链甲头盔");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_LEGGINGS,"锁链远古金属链甲护腿");
    }

}
