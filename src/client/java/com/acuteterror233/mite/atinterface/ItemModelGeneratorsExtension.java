package com.acuteterror233.mite.atinterface;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public interface ItemModelGeneratorsExtension {
    void MME$registerBucket(Item item, ResourceLocation identifier, Item item1);
    void MME$registerFishingRod(Item item, ResourceLocation cast);
}
