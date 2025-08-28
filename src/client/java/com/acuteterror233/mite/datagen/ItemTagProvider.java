package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.AtItems;
import com.acuteterror233.mite.registry.tag.AtTags;
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
                .add(AtItems.ADAMANTIUM_HELMET)
                .add(AtItems.ADAMANTIUM_CHAINMAIL_HELMET)
                .add(AtItems.ANCIENT_METAL_HELMET)
                .add(AtItems.ANCIENT_METAL_CHAINMAIL_HELMET)
                .add(AtItems.COPPER_HELMET)
                .add(AtItems.COPPER_CHAINMAIL_HELMET)
                .add(AtItems.MITHRIL_HELMET)
                .add(AtItems.MITHRIL_CHAINMAIL_HELMET)
                .add(AtItems.RUSTED_IRON_HELMET)
                .add(AtItems.RUSTED_IRON_CHAINMAIL_HELMET)
                .add(AtItems.SILVER_HELMET)
                .add(AtItems.SILVER_CHAINMAIL_HELMET);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                .add(AtItems.ADAMANTIUM_CHESTPLATE)
                .add(AtItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE)
                .add(AtItems.ANCIENT_METAL_CHESTPLATE)
                .add(AtItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE)
                .add(AtItems.COPPER_CHESTPLATE)
                .add(AtItems.COPPER_CHAINMAIL_CHESTPLATE)
                .add(AtItems.MITHRIL_CHESTPLATE)
                .add(AtItems.MITHRIL_CHAINMAIL_CHESTPLATE)
                .add(AtItems.RUSTED_IRON_CHESTPLATE)
                .add(AtItems.RUSTED_IRON_CHAINMAIL_CHESTPLATE)
                .add(AtItems.SILVER_CHESTPLATE)
                .add(AtItems.SILVER_CHAINMAIL_CHESTPLATE);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                .add(AtItems.ADAMANTIUM_LEGGINGS)
                .add(AtItems.ADAMANTIUM_CHAINMAIL_LEGGINGS)
                .add(AtItems.ANCIENT_METAL_LEGGINGS)
                .add(AtItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS)
                .add(AtItems.COPPER_LEGGINGS)
                .add(AtItems.COPPER_CHAINMAIL_LEGGINGS)
                .add(AtItems.MITHRIL_LEGGINGS)
                .add(AtItems.MITHRIL_CHAINMAIL_LEGGINGS)
                .add(AtItems.RUSTED_IRON_LEGGINGS)
                .add(AtItems.RUSTED_IRON_CHAINMAIL_LEGGINGS)
                .add(AtItems.SILVER_LEGGINGS)
                .add(AtItems.SILVER_CHAINMAIL_LEGGINGS);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                .add(AtItems.ADAMANTIUM_BOOTS)
                .add(AtItems.ADAMANTIUM_CHAINMAIL_BOOTS)
                .add(AtItems.ANCIENT_METAL_BOOTS)
                .add(AtItems.ANCIENT_METAL_CHAINMAIL_BOOTS)
                .add(AtItems.COPPER_BOOTS)
                .add(AtItems.COPPER_CHAINMAIL_BOOTS)
                .add(AtItems.MITHRIL_BOOTS)
                .add(AtItems.MITHRIL_CHAINMAIL_BOOTS)
                .add(AtItems.RUSTED_IRON_BOOTS)
                .add(AtItems.RUSTED_IRON_CHAINMAIL_BOOTS)
                .add(AtItems.SILVER_BOOTS)
                .add(AtItems.SILVER_CHAINMAIL_BOOTS);
        getOrCreateTagBuilder(AtTags.WATER_BUCKET)
                .add(AtItems.WATER_ADAMANTIUM_BUCKET)
                .add(AtItems.WATER_ANCIENT_METAL_BUCKET)
                .add(AtItems.WATER_COPPER_BUCKET)
                .add(AtItems.WATER_GOLD_BUCKET)
                .add(AtItems.WATER_MITHRIL_BUCKET)
                .add(AtItems.WATER_SILVER_BUCKET)
                .add(AtItems.WATER_NETHERITE_BUCKET)
                .add(Items.WATER_BUCKET);
        getOrCreateTagBuilder(AtTags.FISHING_RODS)
                .add(AtItems.ADAMANTIUM_FISHING_ROD)
                .add(AtItems.ANCIENT_METAL_FISHING_ROD)
                .add(AtItems.COPPER_FISHING_ROD)
                .add(AtItems.MITHRIL_FISHING_ROD)
                .add(AtItems.GOLD_FISHING_ROD)
                .add(AtItems.IRON_FISHING_ROD)
                .add(AtItems.SILVER_FISHING_ROD)
                .add(AtItems.FLINT_FISHING_ROD)
                .add(AtItems.OBSIDIAN_FISHING_ROD)
                .add(Items.FISHING_ROD);
    }
}
