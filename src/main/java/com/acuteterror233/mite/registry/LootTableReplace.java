package com.acuteterror233.mite.registry;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.MMEItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.critereon.DataComponentMatchers;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class LootTableReplace {
    public static Map<ResourceKey<LootTable>, Function<HolderLookup.Provider, LootTable>> LOOT_TABLES = new HashMap<>();
    public static void init(){
        LOOT_TABLES.put(Blocks.STONE.getLootTable().get(),
                provider -> createSilkTouchWithPickaxesWithExplosiveItemTable(provider, Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRAVEL).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.SPAWN_BONUS_CHEST,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(3.0F))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_NUGGET).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_NUGGET).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.END_CITY_TREASURE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_AXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_HATCHET)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_DAGGER)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_PICKAXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_WAR_HAMMER)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SHOVEL)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_HOE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_MATTOCK)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SCYTHE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SHEARS)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(14))
                                        .add(LootItem.lootTableItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(
                BuiltInLootTables.SIMPLE_DUNGEON,
                provider ->  LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 4.0F))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BUCKET).setWeight(20))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_BUCKET).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.BUCKET).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(3.0F))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        ).build()
        );
        LOOT_TABLES.put(
                BuiltInLootTables.VILLAGE_WEAPONSMITH,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_AXE).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_SWORD).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHESTPLATE).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_HELMET).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_LEGGINGS).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BOOTS).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Blocks.OAK_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(3).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(
                BuiltInLootTables.VILLAGE_TOOLSMITH,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_PICKAXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.TORCH).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_ARMORER,
                provider -> 	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_HELMET).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_CHESTPLATE).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_LEGGINGS).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_BOOTS).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_HELMET).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHESTPLATE).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_LEGGINGS).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BOOTS).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_CARTOGRAPHER,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.MAP).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_MASON,
                provider -> 			LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.STONE_BRICKS).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.YELLOW_DYE).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_SHEPHERD,
                provider ->
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                                .add(LootItem.lootTableItem(Blocks.WHITE_WOOL).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                                .add(LootItem.lootTableItem(Blocks.BLACK_WOOL).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                                .add(LootItem.lootTableItem(Blocks.GRAY_WOOL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                                .add(LootItem.lootTableItem(Blocks.BROWN_WOOL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                                .add(LootItem.lootTableItem(Blocks.LIGHT_GRAY_WOOL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                                .add(LootItem.lootTableItem(MMEItems.COPPER_SHEARS).setWeight(1).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                                .add(LootItem.lootTableItem(Items.WHEAT).setWeight(1))
                                ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_BUTCHER,
                provider -> 	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.PORKCHOP).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(6))
                                        .add(LootItem.lootTableItem(Items.BEEF).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.MUTTON).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.CHICKEN).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_FLETCHER,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.EGG).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.FLINT_SHARD).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_FISHER,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.COD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_TANNERY,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_TEMPLE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_DESERT_HOUSE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.GREEN_DYE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.CACTUS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.DEAD_BUSH).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
                provider -> 	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.DANDELION).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.POPPY).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.OAK_SAPLING).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
                provider -> 	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 5.0F))
                                        .add(LootItem.lootTableItem(Items.FERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.LARGE_FERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_PIE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.SPRUCE_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.SPRUCE_SIGN).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 5.0F))
                                        .add(LootItem.lootTableItem(Blocks.BLUE_ICE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.SNOW_BLOCK).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.SNOWBALL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.SHORT_GRASS).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.TALL_GRASS).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.ACACIA_SAPLING).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.TORCH).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.ABANDONED_MINESHAFT,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(30))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(2).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(LootItem.lootTableItem(Blocks.RAIL).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Blocks.POWERED_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Blocks.DETECTOR_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Blocks.ACTIVATOR_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Blocks.TORCH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.NETHER_BRIDGE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.GOLDEN_CHAINMAIL_HELMET).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.GOLDEN_CHAINMAIL_CHESTPLATE).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.NETHER_WART).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(3))
                                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(14))
                                        .add(LootItem.lootTableItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.STRONGHOLD_LIBRARY,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 6.0F))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.MAP))
                                        .add(LootItem.lootTableItem(Items.COMPASS))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, ConstantValue.exactly(25.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.STRONGHOLD_CROSSING,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, ConstantValue.exactly(25.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.STRONGHOLD_CORRIDOR,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(5).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.SADDLE))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, ConstantValue.exactly(25.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.DESERT_PYRAMID,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(25).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
                                        .add(EmptyLootItem.emptyItem().setWeight(15))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(4.0F))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Blocks.SAND).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(6))
                                        .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.JUNGLE_TEMPLE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Blocks.BAMBOO).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(16).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, ConstantValue.exactly(25.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                                        .add(LootItem.lootTableItem(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.IGLOO_CHEST,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                        )
                        .build()
        );
        LOOT_TABLES.put(BuiltInLootTables.WOODLAND_MANSION,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.LEAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BUCKET))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.RESIN_CLUMP).setWeight(50).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(1))
                                        .add(LootItem.lootTableItem(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 8.0F))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.STONE_AXE).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .setWeight(5)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap()
                                                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.UNDERWATER_RUIN_BIG,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F))))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .setWeight(5)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap()
                                                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.BURIED_TREASURE,
                provider -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA).setWeight(2))
                                .add(EmptyLootItem.emptyItem().setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Blocks.TNT).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND))
                                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_AXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_PICKAXE).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F,0.9F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(LootItem.lootTableItem(Items.COOKED_COD).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.COOKED_SALMON).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(Items.POTION)).apply(SetPotionFunction.setPotion(Potions.WATER_BREATHING))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.SHIPWRECK_MAP,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap()
                                                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(LootItem.lootTableItem(Items.COMPASS))
                                        .add(LootItem.lootTableItem(Items.MAP))
                                        .add(LootItem.lootTableItem(Items.CLOCK))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.SHIPWRECK_SUPPLY,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(3.0F, 6.0F))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.MOSS_BLOCK).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(7))
                                        .add(
                                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                                        .setWeight(10)
                                                        .apply(
                                                                SetStewEffectFunction.stewEffect()
                                                                        .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                                                        .withEffect(MobEffects.JUMP_BOOST, UniformGenerator.between(7.0F, 10.0F))
                                                                        .withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
                                                                        .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                                                        .withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
                                                                        .withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))
                                                        )
                                        )
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 24.0F))))
                                        .add(LootItem.lootTableItem(Blocks.PUMPKIN).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Blocks.BAMBOO).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.TNT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.SHIPWRECK_TREASURE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(90).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(50).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.PILLAGER_OUTPOST,
                provider -> LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 1.0F)).add(LootItem.lootTableItem(Items.CROSSBOW)))
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(Blocks.DARK_OAK_LOG).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(7))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.TRIPWIRE_HOOK).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(LootItem.lootTableItem(Items.GOAT_HORN))
                                        .apply(SetInstrumentFunction.setInstrumentOptions(InstrumentTags.REGULAR_GOAT_HORNS))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(3))
                                        .add(LootItem.lootTableItem(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.BASTION_TREASURE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Blocks.ANCIENT_DEBRIS).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(8).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Blocks.ANCIENT_DEBRIS).setWeight(4).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HATCHET)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_DAGGER)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_WAR_HAMMER)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HOE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_MATTOCK)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SCYTHE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHEARS)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_SWORD).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_HELMET).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_BOOTS).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_LEGGINGS).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(12.0F, 25.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Blocks.IRON_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.QUARTZ).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 18.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F))))
                                        .add(LootItem.lootTableItem(Items.MAGMA_CREAM).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)))
                        .build()
        );
        LOOT_TABLES.put(BuiltInLootTables.BASTION_OTHER,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .setWeight(6)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL).setWeight(6)
                                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .setWeight(6)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(12).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(4).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 22.0F))))
                                        .add(LootItem.lootTableItem(Items.PIGLIN_BANNER_PATTERN).setWeight(9).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_PIGSTEP).setWeight(5).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(12).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 10.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(9).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(new EnchantRandomlyFunction.Builder().withEnchantment(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SOUL_SPEED))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(2.0F))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ANCIENT_METAL_SWORD)
                                                        .setWeight(2)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.9F)))
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.IRON_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(new EnchantRandomlyFunction.Builder().withEnchantment(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SOUL_SPEED)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.CROSSBOW).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CHAIN).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                                        .add(LootItem.lootTableItem(Items.MAGMA_CREAM).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Blocks.BONE_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 17.0F))))
                                        .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.BASTION_BRIDGE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem())
                                        .add(LootItem.lootTableItem(Blocks.LODESTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.5F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(10.0F, 28.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_SWORD)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_HELMET)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_BATTLE_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_HATCHET)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_DAGGER)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_WAR_HAMMER)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_MATTOCK)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_SCYTHE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_SHEARS)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 17.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.BASTION_HOGLIN_STABLE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL)
                                                        .setWeight(10)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_DAGGER)
                                                        .setWeight(10)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .setWeight(12)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.95F)))
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(8).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(12).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(5).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(12).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).setWeight(16).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 17.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(10).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_BATTLE_AXE)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GLOWSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.SOUL_SAND).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_NYLIUM).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.PORKCHOP).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_FUNGUS).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_ROOTS).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.ANCIENT_CITY,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(5.0F, 10.0F))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.SCULK_CATALYST).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_HOE)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 1.0F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(30.0F, 50.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.LEAD).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .setWeight(2)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(30.0F, 50.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(3).apply(new EnchantRandomlyFunction.Builder().withEnchantment(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SWIFT_SNEAK))))
                                        .add(LootItem.lootTableItem(Items.SCULK).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 10.0F))))
                                        .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.CANDLE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.6F, 0.9F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 39.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.ECHO_SHARD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.DISC_FRAGMENT_5).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .setWeight(5)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.STRONG_REGENERATION))
                                        )
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                                        .add(LootItem.lootTableItem(Items.SOUL_TORCH).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(75))
                                        .add(LootItem.lootTableItem(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 5.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                                        .setWeight(1)
                                                        .apply(
                                                                SetStewEffectFunction.stewEffect()
                                                                        .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                                                        .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                                        )
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                                        .add(LootItem.lootTableItem(Items.PACKED_ICE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                                        .add(LootItem.lootTableItem(Items.SNOWBALL).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.RUINED_PORTAL,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_NUGGET).setWeight(30).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(40).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.9F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider)).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GLISTERING_MELON_SLICE).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.LIGHT_WEIGHTED_PRESSURE_PLATE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.CLOCK).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.BELL).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.LODESTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_COMMON,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.POISON))
                                        )
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.WIND_CHARGE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_YLEM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_SANCT_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_NUL_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_QUAS_RUNESTORE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetOminousBottleAmplifierFunction.setAmplifier(UniformGenerator.between(0.0F, 1.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.WIND_CHARGE).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_RARE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                                        .add(LootItem.lootTableItem(Items.SHIELD).setWeight(3).apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1.0F))))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(5.0F, 20.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_AXE).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 30.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.IRON_BATTLE_AXE).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 30.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                                        .setWeight(2)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 30.0F)))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_POR_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_AN_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_NOX_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_FLAM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_VAS_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_DES_RUNESTORE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BANE_OF_ARTHROPODS),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.EFFICIENCY),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FEATHER_FALLING)
                                                                                )
                                                                        )
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.RIPTIDE),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOYALTY),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.CHANNELING),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.IMPALING),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MENDING)
                                                                                )
                                                                        )
                                                        )
                                        )
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_UNIQUE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.GUSTER_BANNER_PATTERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_PRECIPICE).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TRIDENT).setWeight(1))             
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_ORT_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_TYM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_CORP_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_LOR_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_MANI_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_JUX_RUNESTORE).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                                        .add(EmptyLootItem.emptyItem().setWeight(99))
                                        .add(LootItem.lootTableItem(MMEItems.ADAMANTIUM_SWORD).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(40.0F, 50.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.EMERALD_BLOCK).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(4))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_YLEM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_SANCT_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_NUL_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_QUAS_RUNESTORE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(5.0F, 20.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HELMET)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PUNCH),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SMITE),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),
                                                                                         provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MULTISHOT)
                                                                                )
                                                                        )
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder().withOneOf(HolderSet.direct(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BREACH), provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.DENSITY)))
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(new SetEnchantmentsFunction.Builder().withEnchantment(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.WIND_BURST), ConstantValue.exactly(1.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F))))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(4))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .setWeight(4)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(10.0F, 20.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HELMET)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(20.0F, 25.0F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PUNCH),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SMITE),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),
                                                                                        provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.MULTISHOT)
                                                                                )
                                                                        )
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(HolderSet.direct(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BREACH), provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.DENSITY)))
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(new SetEnchantmentsFunction.Builder().withEnchantment(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.WIND_BURST), ConstantValue.exactly(1.0F)))
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_POR_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_AN_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_NOX_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_FLAM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_VAS_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_DES_RUNESTORE).setWeight(3))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_ORT_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_TYM_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_CORP_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_LOR_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_MANI_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_JUX_RUNESTORE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.FLOW_BANNER_PATTERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.HEAVY_CORE).setWeight(1))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 14.0F))).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.POISON))
                                                        .setWeight(1)
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.SLOWNESS))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.ACACIA_PLANKS).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.MOSS_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BONE_MEAL).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.TUFF).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.REGENERATION))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))
                                                        .apply(SetPotionFunction.setPotion(Potions.STRENGTH))
                                        )
                                        .add(LootItem.lootTableItem(Items.MILK_BUCKET).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.HONEYCOMB).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.COPPER_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
                                                        .setWeight(2)
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.COPPER_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
                                                        .setWeight(2)
                                        )
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BAMBOO_HANGING_SIGN).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BAMBOO_PLANKS).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.SCAFFOLDING).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TUFF).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 20.0F))).setWeight(3))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(5))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.7F)))
                                                        .setWeight(5)
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.7F)))
                                                        .setWeight(5)
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.CAKE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 20.0F))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(20))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.4F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(provider))
                                                        .setWeight(1)
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.3F, 0.8F)))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.COMPASS)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.BUCKET).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
                                                        .setWeight(4)
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                                        .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15F, 0.8F)))
                                                        .setWeight(4)
                                        )
                                        .add(LootItem.lootTableItem(Items.BAMBOO_PLANKS).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 15.0F))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 10.0F))).setWeight(10))
                        ).build()
        );
        LOOT_TABLES.put(BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE,
                provider -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                        .add(LootItem.lootTableItem(Items.TRIAL_KEY).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.HONEYCOMB).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 10.0F))).setWeight(10))
                        ).build()
        );

        List<Block> Log = List.of(
                Blocks.OAK_LOG,
                Blocks.SPRUCE_LOG,
                Blocks.BIRCH_LOG,
                Blocks.JUNGLE_LOG,
                Blocks.ACACIA_LOG,
                Blocks.DARK_OAK_LOG,
                Blocks.MANGROVE_LOG,
                Blocks.CHERRY_LOG,
                Blocks.PALE_OAK_LOG,
                Blocks.CRIMSON_STEM,
                Blocks.WARPED_STEM,
                Blocks.OAK_WOOD,
                Blocks.SPRUCE_WOOD,
                Blocks.BIRCH_WOOD,
                Blocks.JUNGLE_WOOD,
                Blocks.ACACIA_WOOD,
                Blocks.DARK_OAK_WOOD,
                Blocks.MANGROVE_WOOD,
                Blocks.CHERRY_WOOD,
                Blocks.PALE_OAK_WOOD,
                Blocks.CRIMSON_HYPHAE,
                Blocks.WARPED_HYPHAE,

                Blocks.STRIPPED_OAK_LOG,
                Blocks.STRIPPED_SPRUCE_LOG,
                Blocks.STRIPPED_BIRCH_LOG,
                Blocks.STRIPPED_JUNGLE_LOG,
                Blocks.STRIPPED_ACACIA_LOG,
                Blocks.STRIPPED_DARK_OAK_LOG,
                Blocks.STRIPPED_MANGROVE_LOG,
                Blocks.STRIPPED_CHERRY_LOG,
                Blocks.STRIPPED_PALE_OAK_LOG,
                Blocks.STRIPPED_CRIMSON_STEM,
                Blocks.STRIPPED_WARPED_STEM,
                Blocks.STRIPPED_OAK_WOOD,
                Blocks.STRIPPED_SPRUCE_WOOD,
                Blocks.STRIPPED_BIRCH_WOOD,
                Blocks.STRIPPED_JUNGLE_WOOD,
                Blocks.STRIPPED_ACACIA_WOOD,
                Blocks.STRIPPED_DARK_OAK_WOOD,
                Blocks.STRIPPED_MANGROVE_WOOD,
                Blocks.STRIPPED_CHERRY_WOOD,
                Blocks.STRIPPED_PALE_OAK_WOOD,
                Blocks.STRIPPED_CRIMSON_HYPHAE,
                Blocks.STRIPPED_WARPED_HYPHAE
        );
        Log.forEach(block -> LOOT_TABLES.put(block.getLootTable().get(), provider -> LootTableReplace.createLogItemTable(provider, block, Items.STICK, 2, 4).build()));

        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            Function<HolderLookup.Provider, LootTable> function = LOOT_TABLES.get(key);
            return function != null ? function.apply(registries) : null;
        });
    }
    public static LootTable.Builder createLogItemTable(
            HolderLookup.Provider provider,
            ItemLike silkTouchAndDigItem,
            ItemLike blastingItem,
            int min,
            int max
    ){
      return createSilkTouchWithTagWithExplosiveItemTable(provider, silkTouchAndDigItem, ConstantValue.exactly(1.0F), silkTouchAndDigItem, ConstantValue.exactly(1.0F), ItemTags.AXES, blastingItem, UniformGenerator.between(min, max));
    }
    public static LootTable.Builder createSilkTouchWithPickaxesWithExplosiveItemTable(HolderLookup.Provider provider, ItemLike silkTouchItem, ItemLike digItem, ItemLike blastingItem){
        return createSilkTouchWithTagWithExplosiveItemTable(provider, silkTouchItem, ConstantValue.exactly(1.0F), digItem, ConstantValue.exactly(1.0F), ItemTags.PICKAXES, blastingItem, ConstantValue.exactly(1.0F));
    }
    public static LootTable.Builder createSilkTouchWithTagWithExplosiveItemTable(HolderLookup.Provider provider,
                                                                                 ItemLike silkTouchItem,
                                                                                 NumberProvider count1,
                                                                                 ItemLike digItem,
                                                                                 NumberProvider count2,
                                                                                 TagKey<Item> tag,
                                                                                 ItemLike blastingItem,
                                                                                 NumberProvider count3) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(silkTouchItem)
                                        .apply(SetItemCountFunction.setCount(count1))
                                        .when(MatchTool.toolMatches(
                                                ItemPredicate.Builder.item()
                                                        .withComponents(
                                                                DataComponentMatchers.Builder.components()
                                                                        .partial(
                                                                                DataComponentPredicates.ENCHANTMENTS,
                                                                                EnchantmentsPredicate.enchantments(
                                                                                        List.of(
                                                                                                new EnchantmentPredicate(provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))
                                                                                        )
                                                                                )
                                                                        ).build()
                                                        )
                                                )
                                        )
                                        .otherwise(
                                                LootItem.lootTableItem(digItem)
                                                        .apply(SetItemCountFunction.setCount(count2))
                                                        .when(MatchTool.toolMatches(
                                                                ItemPredicate.Builder.item()
                                                                        .of(provider.lookupOrThrow(Registries.ITEM), tag)
                                                                )
                                                        )
                                        ).otherwise(
                                                LootItem.lootTableItem(blastingItem)
                                                        .apply(SetItemCountFunction.setCount(count3))
                                                        .when(ExplosionCondition.survivesExplosion())
                                        )
                                )
                );
    }
}
