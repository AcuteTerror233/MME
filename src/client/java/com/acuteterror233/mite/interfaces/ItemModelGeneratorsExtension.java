package com.acuteterror233.mite.interfaces;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;

/**
 * Extension interface for item model generators.
 * Provides custom generation methods for item models in data generation.
 */
public interface ItemModelGeneratorsExtension {
    void MME$registerBucket(Item item, Identifier identifier, Item item1);
    void MME$registerFishingRod(Item item, Identifier cast);
    void MME$registerIronFishingRod(Item item, Identifier cast);
    void MME$registerChainmailTrimmableItem(Item item, Item basePlateModel, ResourceKey<EquipmentAsset> resourceKey, Identifier resourceLocation, String Slot);
}
