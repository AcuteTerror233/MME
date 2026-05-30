package com.acuteterror233.mite.atinterface;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;

/**
 * 物品模型生成器扩展接口。
 * 为数据生成中的物品模型提供自定义生成方法。
 */
public interface ItemModelGeneratorsExtension {
    void MME$registerBucket(Item item, ResourceLocation identifier, Item item1);
    void MME$registerFishingRod(Item item, ResourceLocation cast);
    void MME$registerIronFishingRod(Item item, ResourceLocation cast);
    void MME$registerChainmailTrimmableItem(Item item, Item basePlateModel, ResourceKey<EquipmentAsset> resourceKey, ResourceLocation resourceLocation, String Slot);
}
