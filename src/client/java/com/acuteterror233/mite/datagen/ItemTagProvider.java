package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.At_mite;
import com.acuteterror233.mite.item.At_Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                .add(At_Items.ADAMANTIUM_HELMET)
                .add(At_Items.ADAMANTIUM_CHAINMAIL_HELMET)
                .add(At_Items.ANCIENT_METAL_HELMET)
                .add(At_Items.ANCIENT_METAL_CHAINMAIL_HELMET)
                .add(At_Items.COPPER_HELMET)
                .add(At_Items.COPPER_CHAINMAIL_HELMET)
                .add(At_Items.MITHRIL_HELMET)
                .add(At_Items.MITHRIL_CHAINMAIL_HELMET)
                .add(At_Items.RUSTED_IRON_HELMET)
                .add(At_Items.RUSTED_IRON_CHAINMAIL_HELMET)
                .add(At_Items.SILVER_HELMET)
                .add(At_Items.SILVER_CHAINMAIL_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(At_Items.ADAMANTIUM_CHESTPLATE)
                .add(At_Items.ADAMANTIUM_CHAINMAIL_CHESTPLATE)
                .add(At_Items.ANCIENT_METAL_CHESTPLATE)
                .add(At_Items.ANCIENT_METAL_CHAINMAIL_CHESTPLATE)
                .add(At_Items.COPPER_CHESTPLATE)
                .add(At_Items.COPPER_CHAINMAIL_CHESTPLATE)
                .add(At_Items.MITHRIL_CHESTPLATE)
                .add(At_Items.MITHRIL_CHAINMAIL_CHESTPLATE)
                .add(At_Items.RUSTED_IRON_CHESTPLATE)
                .add(At_Items.RUSTED_IRON_CHAINMAIL_CHESTPLATE)
                .add(At_Items.SILVER_CHESTPLATE)
                .add(At_Items.SILVER_CHAINMAIL_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(At_Items.ADAMANTIUM_LEGGINGS)
                .add(At_Items.ADAMANTIUM_CHAINMAIL_LEGGINGS)
                .add(At_Items.ANCIENT_METAL_LEGGINGS)
                .add(At_Items.ANCIENT_METAL_CHAINMAIL_LEGGINGS)
                .add(At_Items.COPPER_LEGGINGS)
                .add(At_Items.COPPER_CHAINMAIL_LEGGINGS)
                .add(At_Items.MITHRIL_LEGGINGS)
                .add(At_Items.MITHRIL_CHAINMAIL_LEGGINGS)
                .add(At_Items.RUSTED_IRON_LEGGINGS)
                .add(At_Items.RUSTED_IRON_CHAINMAIL_LEGGINGS)
                .add(At_Items.SILVER_LEGGINGS)
                .add(At_Items.SILVER_CHAINMAIL_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(At_Items.ADAMANTIUM_BOOTS)
                .add(At_Items.ADAMANTIUM_CHAINMAIL_BOOTS)
                .add(At_Items.ANCIENT_METAL_BOOTS)
                .add(At_Items.ANCIENT_METAL_CHAINMAIL_BOOTS)
                .add(At_Items.COPPER_BOOTS)
                .add(At_Items.COPPER_CHAINMAIL_BOOTS)
                .add(At_Items.MITHRIL_BOOTS)
                .add(At_Items.MITHRIL_CHAINMAIL_BOOTS)
                .add(At_Items.RUSTED_IRON_BOOTS)
                .add(At_Items.RUSTED_IRON_CHAINMAIL_BOOTS)
                .add(At_Items.SILVER_BOOTS)
                .add(At_Items.SILVER_CHAINMAIL_BOOTS);
        getOrCreateTagBuilder(At_mite.WATER_BUCKET)
                .add(At_Items.WATER_ADAMANTIUM_BUCKET)
                .add(Items.WATER_BUCKET);
    }
}
