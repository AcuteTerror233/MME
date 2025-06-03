package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.MiteItems;
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
        translationBuilder.add(MiteItems.ADAMANTIUM_HELMET,"艾德曼头盔");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHESTPLATE,"艾德曼胸甲");
        translationBuilder.add(MiteItems.ADAMANTIUM_LEGGINGS,"艾德曼护腿");
        translationBuilder.add(MiteItems.ADAMANTIUM_BOOTS,"艾德曼靴子");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_HELMET,"艾德曼锁链头盔");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE,"艾德曼锁链胸甲");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_LEGGINGS,"艾德曼锁链护腿");
        translationBuilder.add(MiteItems.ADAMANTIUM_CHAINMAIL_BOOTS,"艾德曼锁链靴子");
        translationBuilder.add(MiteItems.ANCIENT_METAL_HELMET,"远古金属头盔");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHESTPLATE,"远古金属胸甲");
        translationBuilder.add(MiteItems.ANCIENT_METAL_LEGGINGS,"远古金属护腿");
        translationBuilder.add(MiteItems.ANCIENT_METAL_BOOTS,"远古金属靴子");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_HELMET,"远古金属锁链头盔");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE,"远古金属锁链胸甲");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS,"远古金属锁链护腿");
        translationBuilder.add(MiteItems.ANCIENT_METAL_CHAINMAIL_BOOTS,"远古金属锁链靴子");
        translationBuilder.add(MiteItems.COPPER_HELMET,"铜头盔");
        translationBuilder.add(MiteItems.COPPER_CHESTPLATE,"铜胸甲");
        translationBuilder.add(MiteItems.COPPER_LEGGINGS,"铜护腿");
        translationBuilder.add(MiteItems.COPPER_BOOTS,"铜靴子");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_HELMET,"铜锁链头盔");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_CHESTPLATE,"铜锁链胸甲");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_LEGGINGS,"铜锁链护腿");
        translationBuilder.add(MiteItems.COPPER_CHAINMAIL_BOOTS,"铜锁链靴子");
        translationBuilder.add(MiteItems.MITHRIL_HELMET,"秘银头盔");
        translationBuilder.add(MiteItems.MITHRIL_CHESTPLATE,"秘银胸甲");
        translationBuilder.add(MiteItems.MITHRIL_LEGGINGS,"秘银护腿");
        translationBuilder.add(MiteItems.MITHRIL_BOOTS,"秘银靴子");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_HELMET,"秘银锁链头盔");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_CHESTPLATE,"秘银锁链胸甲");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_LEGGINGS,"秘银锁链护腿");
        translationBuilder.add(MiteItems.MITHRIL_CHAINMAIL_BOOTS,"秘银锁链靴子");
        translationBuilder.add(MiteItems.RUSTED_IRON_HELMET, "锈铁头盔");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHESTPLATE, "锈铁胸甲");
        translationBuilder.add(MiteItems.RUSTED_IRON_LEGGINGS, "锈铁护腿");
        translationBuilder.add(MiteItems.RUSTED_IRON_BOOTS, "锈铁靴子");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_HELMET, "锈铁锁链头盔");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE, "锈铁锁链胸甲");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_LEGGINGS, "锈铁锁链护腿");
        translationBuilder.add(MiteItems.RUSTED_IRON_CHAINMAIL_BOOTS, "锈铁锁链靴子");
        translationBuilder.add(MiteItems.SILVER_HELMET, "银头盔");
        translationBuilder.add(MiteItems.SILVER_CHESTPLATE, "银胸甲");
        translationBuilder.add(MiteItems.SILVER_LEGGINGS, "银护腿");
        translationBuilder.add(MiteItems.SILVER_BOOTS, "银靴子");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_HELMET, "银锁链头盔");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_CHESTPLATE, "银锁链胸甲");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_LEGGINGS, "银锁链护腿");
        translationBuilder.add(MiteItems.SILVER_CHAINMAIL_BOOTS, "银锁链靴子");
        translationBuilder.add(MiteItems.BANANA, "香蕉");
        translationBuilder.add(MiteItems.BLUEBERRIE, "蓝莓");
        translationBuilder.add(MiteItems.CHEESE, "奶酪");
        translationBuilder.add(MiteItems.CHOCOLATE, "巧克力");
        translationBuilder.add(MiteItems.DOUGH, "面团");
        translationBuilder.add(MiteItems.ONION, "洋葱");
        translationBuilder.add(MiteItems.LEMON, "柠檬");
        translationBuilder.add(MiteItems.ORANGE, "橘子");
        translationBuilder.add(MiteItems.WORM_COOKED, "烤熟的虫子");
        translationBuilder.add(MiteItems.WORM_RAW, "生的虫子");
    }

}
