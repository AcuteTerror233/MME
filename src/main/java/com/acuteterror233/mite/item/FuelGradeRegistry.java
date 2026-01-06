package com.acuteterror233.mite.item;

import com.acuteterror233.mite.registry.tag.MMETags;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.Collections;
import java.util.SequencedSet;

public class FuelGradeRegistry {
    private final Object2IntSortedMap<Item> fuelGrades;

    FuelGradeRegistry(Object2IntSortedMap<Item> fuelGrades) {
        this.fuelGrades = fuelGrades;
    }

    public boolean hasFuelGrade(ItemStack item) {
        return this.fuelGrades.containsKey(item.getItem());
    }

    public SequencedSet<Item> getFuelGradeItems() {
        return Collections.unmodifiableSequencedSet(this.fuelGrades.keySet());
    }

    public int getFuelGrade(ItemStack item) {
        return item.isEmpty() ? 0 : this.fuelGrades.getInt(item.getItem());
    }

    public static FuelGradeRegistry createDefault(HolderLookup.Provider registries, FeatureFlagSet enabledFeatures) {
        return new FuelGradeRegistry.Builder(registries, enabledFeatures)
                .add(Items.BLAZE_ROD, 4)
                .add(MMETags.LAVA_BUCKET, 3)
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
                .add(MMEItems.WOODEN_CLUB, 1)
                .add(MMEItems.WOODEN_CUDGEL, 1)
                .add(Items.TORCH, 1)
                .add(Items.SOUL_TORCH, 1)
                .remove(ItemTags.NON_FLAMMABLE_WOOD)
                .build();
    }

    public static class Builder {
        private final HolderLookup<Item> itemLookup;
        private final FeatureFlagSet enabledFeatures;
        private final Object2IntSortedMap<Item> fuelGrades = new Object2IntLinkedOpenHashMap<>();

        public Builder(HolderLookup.Provider registries, FeatureFlagSet enabledFeatures) {
            this.itemLookup = registries.lookupOrThrow(Registries.ITEM);
            this.enabledFeatures = enabledFeatures;
        }

        public FuelGradeRegistry build() {
            return new FuelGradeRegistry(this.fuelGrades);
        }

        public FuelGradeRegistry.Builder remove(TagKey<Item> tag) {
            this.fuelGrades.keySet().removeIf(item -> item.builtInRegistryHolder().is(tag));
            return this;
        }

        public FuelGradeRegistry.Builder add(TagKey<Item> tag, int grade) {
            this.itemLookup.get(tag).ifPresent(tagx -> {
                for (Holder<Item> registryEntry : tagx) {
                    this.add(grade, registryEntry.value());
                }
            });
            return this;
        }

        public FuelGradeRegistry.Builder add(ItemLike item, int grade) {
            Item item2 = item.asItem();
            this.add(grade, item2);
            return this;
        }

        private void add(int grade, Item item) {
            if (item.isEnabled(this.enabledFeatures)) {
                this.fuelGrades.put(item, grade);
            }
        }
    }
}
