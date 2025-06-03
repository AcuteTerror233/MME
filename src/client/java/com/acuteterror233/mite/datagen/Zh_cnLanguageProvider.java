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
        translationBuilder.add("itemGroup.at_mite.item_group","Minecraft实在是太简单了!");
        translationBuilder.add(Items.ADAMANTIUM_HELMET,"艾德曼头盔");
        translationBuilder.add(Items.ADAMANTIUM_CHESTPLATE,"艾德曼胸甲");
        translationBuilder.add(Items.ADAMANTIUM_LEGGINGS,"艾德曼护腿");
        translationBuilder.add(Items.ADAMANTIUM_BOOTS,"艾德曼靴子");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_HELMET,"艾德曼锁链头盔");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_CHESTPLATE,"艾德曼锁链胸甲");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_LEGGINGS,"艾德曼锁链护腿");
        translationBuilder.add(Items.ADAMANTIUM_CHAINMAIL_BOOTS,"艾德曼锁链靴子");
        translationBuilder.add(Items.ANCIENT_METAL_HELMET,"远古金属头盔");
        translationBuilder.add(Items.ANCIENT_METAL_CHESTPLATE,"远古金属胸甲");
        translationBuilder.add(Items.ANCIENT_METAL_LEGGINGS,"远古金属护腿");
        translationBuilder.add(Items.ANCIENT_METAL_BOOTS,"远古金属靴子");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_HELMET,"远古金属锁链头盔");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,"远古金属锁链胸甲");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_LEGGINGS,"远古金属锁链护腿");
        translationBuilder.add(Items.ANCIENT_METAL_CHAINMAIL_BOOTS,"远古金属锁链靴子");
        translationBuilder.add(Items.COPPER_HELMET,"铜头盔");
        translationBuilder.add(Items.COPPER_CHESTPLATE,"铜胸甲");
        translationBuilder.add(Items.COPPER_LEGGINGS,"铜护腿");
        translationBuilder.add(Items.COPPER_BOOTS,"铜靴子");
        translationBuilder.add(Items.COPPER_CHAINMAIL_HELMET,"铜锁链头盔");
        translationBuilder.add(Items.COPPER_CHAINMAIL_CHESTPLATE,"铜锁链胸甲");
        translationBuilder.add(Items.COPPER_CHAINMAIL_LEGGINGS,"铜锁链护腿");
        translationBuilder.add(Items.COPPER_CHAINMAIL_BOOTS,"铜锁链靴子");
    }

}
