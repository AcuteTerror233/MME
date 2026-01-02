package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.AtTags;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Collections;
import java.util.SequencedSet;

public class FuelGradeRegistry {
    private final Object2IntSortedMap<Item> fuelGrades;

    FuelGradeRegistry(Object2IntSortedMap<Item> fuelGrades) {
        this.fuelGrades = fuelGrades;
    }

    /**
     * 检查物品是否具有燃料等级
     */
    public boolean hasFuelGrade(ItemStack item) {
        return this.fuelGrades.containsKey(item.getItem());
    }

    /**
     * 获取所有具有燃料等级的物品集合
     */
    public SequencedSet<Item> getFuelGradeItems() {
        return Collections.unmodifiableSequencedSet(this.fuelGrades.keySet());
    }

    /**
     * 获取物品的燃料等级
     */
    public int getFuelGrade(ItemStack item) {
        return item.isEmpty() ? 0 : this.fuelGrades.getInt(item.getItem());
    }

    /**
     * 创建默认的燃料等级注册表
     */
    public static FuelGradeRegistry createDefault(RegistryWrapper.WrapperLookup registries, FeatureSet enabledFeatures) {
        return new FuelGradeRegistry.Builder(registries, enabledFeatures)
                .add(Items.BLAZE_ROD, 4)
                .add(AtTags.LAVA_BUCKET, 3)
                .add(Items.COAL, 2)
                .add(Blocks.COAL_BLOCK, 2)
                .add(Items.CHARCOAL, 1)
                .add(ItemTags.LOGS, 1)
                .add(ItemTags.BAMBOO_BLOCKS, 1)
                .add(ItemTags.PLANKS, 1)
                .add(Blocks.BAMBOO_MOSAIC, 1)
                .add(ItemTags.WOODEN_STAIRS, 1)
                .add(Blocks.BAMBOO_MOSAIC_STAIRS, 1)
                .add(ItemTags.WOODEN_SLABS, 1)
                .add(Blocks.BAMBOO_MOSAIC_SLAB, 1)
                .add(ItemTags.WOODEN_TRAPDOORS, 1)
                .add(ItemTags.WOODEN_PRESSURE_PLATES, 1)
                .add(ItemTags.WOODEN_FENCES, 1)
                .add(ItemTags.FENCE_GATES, 1)
                .add(Blocks.NOTE_BLOCK, 1)
                .add(Blocks.BOOKSHELF, 1)
                .add(Blocks.CHISELED_BOOKSHELF, 1)
                .add(Blocks.LECTERN, 1)
                .add(Blocks.JUKEBOX, 1)
                .add(Blocks.CHEST, 1)
                .add(Blocks.TRAPPED_CHEST, 1)
                .add(Blocks.CRAFTING_TABLE, 1)
                .add(Blocks.DAYLIGHT_DETECTOR, 1)
                .add(ItemTags.BANNERS, 1)
                .add(Items.BOW, 1)
                .add(Items.FISHING_ROD, 1)
                .add(Blocks.LADDER, 1)
                .add(ItemTags.SIGNS, 1)
                .add(ItemTags.HANGING_SIGNS, 1)
                .add(Items.WOODEN_SHOVEL, 1)
                .add(Items.WOODEN_SWORD, 1)
                .add(Items.WOODEN_HOE, 1)
                .add(Items.WOODEN_AXE, 1)
                .add(Items.WOODEN_PICKAXE, 1)
                .add(ItemTags.WOODEN_DOORS, 1)
                .add(ItemTags.BOATS, 1)
                .add(ItemTags.WOOL, 1)
                .add(ItemTags.WOODEN_BUTTONS, 1)
                .add(Items.STICK, 1)
                .add(ItemTags.SAPLINGS, 1)
                .add(Items.BOWL, 1)
                .add(ItemTags.WOOL_CARPETS, 1)
                .add(Blocks.DRIED_KELP_BLOCK, 1)
                .add(Items.CROSSBOW, 1)
                .add(Blocks.BAMBOO, 1)
                .add(Blocks.DEAD_BUSH, 1)
                .add(Blocks.SHORT_DRY_GRASS, 1)
                .add(Blocks.TALL_DRY_GRASS, 1)
                .add(Blocks.SCAFFOLDING, 1)
                .add(Blocks.LOOM, 1)
                .add(Blocks.BARREL, 1)
                .add(Blocks.CARTOGRAPHY_TABLE, 1)
                .add(Blocks.FLETCHING_TABLE, 1)
                .add(Blocks.SMITHING_TABLE, 1)
                .add(Blocks.COMPOSTER, 1)
                .add(Blocks.AZALEA, 1)
                .add(Blocks.FLOWERING_AZALEA, 1)
                .add(Blocks.MANGROVE_ROOTS, 1)
                .add(Blocks.LEAF_LITTER, 1)
                .add(AtItems.WOODEN_CLUB, 1)
                .add(AtItems.WOODEN_CUDGEL, 1)
                .add(Items.TORCH, 1)
                .add(Items.SOUL_TORCH, 1)
                .remove(ItemTags.NON_FLAMMABLE_WOOD)
                .build();
    }

    public static class Builder {
        private final RegistryWrapper<Item> itemLookup;
        private final FeatureSet enabledFeatures;
        private final Object2IntSortedMap<Item> fuelGrades = new Object2IntLinkedOpenHashMap<>();

        public Builder(RegistryWrapper.WrapperLookup registries, FeatureSet enabledFeatures) {
            this.itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
            this.enabledFeatures = enabledFeatures;
        }

        public FuelGradeRegistry build() {
            return new FuelGradeRegistry(this.fuelGrades);
        }

        /**
         * 移除指定标签的燃料等级
         */
        public FuelGradeRegistry.Builder remove(TagKey<Item> tag) {
            this.fuelGrades.keySet().removeIf(item -> item.getRegistryEntry().isIn(tag));
            return this;
        }

        /**
         * 为标签中的所有物品添加燃料等级
         */
        public FuelGradeRegistry.Builder add(TagKey<Item> tag, int grade) {
            this.itemLookup.getOptional(tag).ifPresent(tagx -> {
                for (RegistryEntry<Item> registryEntry : tagx) {
                    this.add(grade, registryEntry.value());
                }
            });
            return this;
        }

        /**
         * 为特定物品添加燃料等级
         */
        public FuelGradeRegistry.Builder add(ItemConvertible item, int grade) {
            Item item2 = item.asItem();
            this.add(grade, item2);
            return this;
        }

        /**
         * 内部方法：添加物品和对应的燃料等级
         */
        private void add(int grade, Item item) {
            if (item.isEnabled(this.enabledFeatures)) {
                this.fuelGrades.put(item, grade);
            }
        }
    }
}
