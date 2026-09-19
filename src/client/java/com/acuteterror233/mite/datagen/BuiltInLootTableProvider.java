package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.block.Rune;
import com.acuteterror233.mite.item.MMEItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.fabricmc.fabric.impl.datagen.loot.FabricLootTableContext;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.VillagerTrades;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BuiltInLootTableProvider extends SimpleFabricLootTableSubProvider {
    protected final HolderLookup.Provider holderLookup;
    protected final FabricLootTableContext context;
    protected final HolderGetter<Enchantment> enchantments;
    protected final HolderGetter<Item> items;
    protected final HolderGetter<Block> blocks;
    protected final HolderGetter<LootItemCondition> predicates;
    public BuiltInLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, LootContextParamSets.CHEST);
        HolderLookup.Provider holderLookup = registriesFuture.join();
        FabricLootTableContext context = new FabricLootTableContext(holderLookup);
        this.holderLookup = holderLookup;
        this.context = context;
        this.enchantments = context.lookup(Registries.ENCHANTMENT);
        this.items = context.lookup(Registries.ITEM);
        this.blocks = context.lookup(Registries.BLOCK);
        this.predicates = context.lookup(Registries.PREDICATE);
    }
    private final Map<ResourceKey<LootTable>, LootTable.Builder> map = new HashMap<>();

    public void generate(){
        add(BuiltInLootTables.SPAWN_BONUS_CHEST,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(3))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_NUGGET).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.END_CITY_TREASURE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 3))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 10))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_AXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_HATCHET)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_DAGGER)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_PICKAXE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_WAR_HAMMER)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SHOVEL)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_HOE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_MATTOCK)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SCYTHE)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ADAMANTIUM_SHEARS)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(14))
                                        .add(LootItem.lootTableItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(
                BuiltInLootTables.SIMPLE_DUNGEON,
                 LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
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
                                        .setRolls(ContextIntProviders.between(1, 4))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BUCKET).setWeight(20))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_BUCKET).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.BUCKET).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(3))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                        )
        );
        add(
                BuiltInLootTables.VILLAGE_WEAPONSMITH,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.COPPER_AXE).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_SWORD).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_SPEAR).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_CHESTPLATE).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_HELMET).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_LEGGINGS).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_BOOTS).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Blocks.OAK_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(
                BuiltInLootTables.VILLAGE_TOOLSMITH,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.COPPER_PICKAXE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_AXE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_PICKAXE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.9F, 0.99F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.TORCH).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
        );
        add(BuiltInLootTables.VILLAGE_ARMORER,
                	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_HELMET).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_CHESTPLATE).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_LEGGINGS).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_CHAINMAIL_BOOTS).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_HELMET).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_CHESTPLATE).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_LEGGINGS).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_BOOTS).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        )
        );
        add(BuiltInLootTables.VILLAGE_CARTOGRAPHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.MAP).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_MASON,
                			LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.FLOWER_POT).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.STONE_BRICKS).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.DYE.yellow()).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        )
        );
        add(BuiltInLootTables.VILLAGE_SHEPHERD,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .setRolls(ContextIntProviders.between(1, 3))
                                                .add(LootItem.lootTableItem(Blocks.WOOL.white()).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                                .add(LootItem.lootTableItem(Blocks.WOOL.black()).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                                .add(LootItem.lootTableItem(Blocks.WOOL.gray()).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                                .add(LootItem.lootTableItem(Blocks.WOOL.brown()).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                                .add(LootItem.lootTableItem(Blocks.WOOL.lightGray()).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                                .add(LootItem.lootTableItem(MMEItems.COPPER_SHEARS).setWeight(1).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                                .add(LootItem.lootTableItem(Items.WHEAT).setWeight(1))
                                )
        );
        add(BuiltInLootTables.VILLAGE_BUTCHER,
                	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.PORKCHOP).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(6))
                                        .add(LootItem.lootTableItem(Items.BEEF).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.MUTTON).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.CHICKEN).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.VILLAGE_FLETCHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.EGG).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.FLINT_SHARD).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.STICK).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.VILLAGE_FISHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.COD).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.VILLAGE_TANNERY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_TEMPLE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                        )
        );
        add(BuiltInLootTables.VILLAGE_DESERT_HOUSE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.DYE.green()).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.CACTUS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.DEAD_BUSH).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_PLAINS_HOUSE,
                	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.DANDELION).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.POPPY).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.OAK_SAPLING).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_TAIGA_HOUSE,
                	LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 5))
                                        .add(LootItem.lootTableItem(Items.FERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.LARGE_FERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_PIE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.SPRUCE_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.SPRUCE_SIGN).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_SNOWY_HOUSE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 5))
                                        .add(LootItem.lootTableItem(Blocks.BLUE_ICE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.SNOW_BLOCK).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SOUP).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.SNOWBALL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 7))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.SHORT_GRASS).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.TALL_GRASS).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Blocks.ACACIA_SAPLING).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(1))
                                        .add(LootItem.lootTableItem(Blocks.TORCH).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                        )
        );
        add(BuiltInLootTables.ABANDONED_MINESHAFT,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(30))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(2).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Blocks.RAIL).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Blocks.POWERED_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Blocks.DETECTOR_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Blocks.ACTIVATOR_RAIL).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Blocks.TORCH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                        )
        );
        add(BuiltInLootTables.NETHER_BRIDGE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 4))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(MMEItems.GOLDEN_CHAINMAIL_HELMET).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.GOLDEN_CHAINMAIL_CHESTPLATE).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.NETHER_WART).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(3))
                                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(14))
                                        .add(LootItem.lootTableItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.STRONGHOLD_LIBRARY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 6))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                                        .add(LootItem.lootTableItem(Items.MAP))
                                        .add(LootItem.lootTableItem(Items.COMPASS))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.exactly(25))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.STRONGHOLD_CROSSING,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.COPPER_PICKAXE).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.exactly(25))))
                        )
        );
        add(BuiltInLootTables.STRONGHOLD_CORRIDOR,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 3))
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(5).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(5).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(5).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(5).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(5).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 0.8F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.SADDLE))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.exactly(25))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.DESERT_PYRAMID,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
                                        .add(EmptyLootItem.emptyItem().setWeight(15))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(4))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Blocks.SAND).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(6))
                                        .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.JUNGLE_TEMPLE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Blocks.BAMBOO).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 6))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(16).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                                        .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.exactly(25))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(2))
                                        .add(LootItem.lootTableItem(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(30).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                        )
        );
        add(BuiltInLootTables.IGLOO_CHEST,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                        )
                        
        );
        add(BuiltInLootTables.WOODLAND_MANSION,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.LEAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BUCKET))
                                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.RESIN_CLUMP).setWeight(50).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(1))
                                        .add(LootItem.lootTableItem(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 8))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .setWeight(5)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap(holderLookup.getOrThrow(StructureTags.ON_TREASURE_MAPS))
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        )
        );
        add(BuiltInLootTables.UNDERWATER_RUIN_BIG,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_FISHING_ROD).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F))))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .setWeight(5)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap(holderLookup.getOrThrow(StructureTags.ON_TREASURE_MAPS))
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        )
        );
        add(BuiltInLootTables.BURIED_TREASURE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA).setWeight(2))
                                .add(EmptyLootItem.emptyItem().setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(5))
                                        .add(LootItem.lootTableItem(Blocks.TNT).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND))
                                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.COPPER_AXE).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.7F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_PICKAXE).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F,0.9F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.COOKED_COD).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.COOKED_SALMON).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.POTION)).apply(SetPotionFunction.setPotion(Potions.WATER_BREATHING))
                        )
        );
        add(BuiltInLootTables.SHIPWRECK_MAP,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.MAP)
                                                        .apply(
                                                                ExplorationMapFunction.makeExplorationMap(holderLookup.getOrThrow(StructureTags.ON_TREASURE_MAPS))
                                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                                        .setZoom((byte)1)
                                                                        .setSkipKnownStructures(false)
                                                        )
                                                        .apply(SetNameFunction.setName(Component.translatable("filled_map.buried_treasure"), SetNameFunction.Target.ITEM_NAME))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.COMPASS))
                                        .add(LootItem.lootTableItem(Items.MAP))
                                        .add(LootItem.lootTableItem(Items.CLOCK))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.SHIPWRECK_SUPPLY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(3, 6))
                                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(8))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(Items.MOSS_BLOCK).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(7))
                                        .add(
                                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                                        .setWeight(10)
                                                        .apply(
                                                                SetStewEffectFunction.stewEffect()
                                                                        .withEffect(MobEffects.NIGHT_VISION, ContextIntProviders.between(7, 10))
                                                                        .withEffect(MobEffects.JUMP_BOOST, ContextIntProviders.between(7, 10))
                                                                        .withEffect(MobEffects.WEAKNESS, ContextIntProviders.between(6, 8))
                                                                        .withEffect(MobEffects.BLINDNESS, ContextIntProviders.between(5, 7))
                                                                        .withEffect(MobEffects.POISON, ContextIntProviders.between(10, 20))
                                                                        .withEffect(MobEffects.SATURATION, ContextIntProviders.between(7, 10))
                                                        )
                                        )
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 24))))
                                        .add(LootItem.lootTableItem(Blocks.PUMPKIN).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Blocks.BAMBOO).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Blocks.TNT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.SHIPWRECK_TREASURE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 2))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(90).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).setWeight(30).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(5))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(50).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(20).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.PILLAGER_OUTPOST,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(0, 1)).add(LootItem.lootTableItem(Items.CROSSBOW)))
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Blocks.DARK_OAK_LOG).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(7))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.TRIPWIRE_HOOK).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.GOAT_HORN))
                                        .apply(SetInstrumentFunction.setInstrumentOptions(holderLookup.lookupOrThrow(Registries.INSTRUMENT).getOrThrow(InstrumentTags.REGULAR_GOAT_HORNS)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(3))
                                        .add(LootItem.lootTableItem(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                        )
        );
        add(BuiltInLootTables.BASTION_TREASURE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Blocks.ANCIENT_DEBRIS).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(8).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Blocks.ANCIENT_DEBRIS).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HATCHET)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_DAGGER)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_WAR_HAMMER)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HOE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_MATTOCK)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SCYTHE)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHEARS)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_SWORD).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_HELMET).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_BOOTS).setWeight(2))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_LEGGINGS).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(12, 25))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Blocks.IRON_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 5))))
                                        .add(LootItem.lootTableItem(Items.QUARTZ).apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 18))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 15))))
                                        .add(LootItem.lootTableItem(Items.MAGMA_CREAM).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)))
                        
        );
        add(BuiltInLootTables.BASTION_OTHER,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .setWeight(6)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL).setWeight(6)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .setWeight(6)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.1F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(12).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(10, 22))))
                                        .add(LootItem.lootTableItem(Items.PIGLIN_BANNER_PATTERN).setWeight(9).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_PIGSTEP).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(12).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 10))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(9).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(new EnchantRandomlyFunction.Builder().withEnchantment(enchantments.getOrThrow(Enchantments.SOUL_SPEED))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.ANCIENT_METAL_SWORD)
                                                        .setWeight(2)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.1F, 0.9F)))
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.IRON_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(new EnchantRandomlyFunction.Builder().withEnchantment(enchantments.getOrThrow(Enchantments.SOUL_SPEED)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.CROSSBOW).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 3))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Blocks.IRON_CHAIN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 10))))
                                        .add(LootItem.lootTableItem(Items.MAGMA_CREAM).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(Blocks.BONE_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 6))))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 17))))
                                        .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.BASTION_BRIDGE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem())
                                        .add(LootItem.lootTableItem(Blocks.LODESTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.1F, 0.5F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(10, 28))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 12))))
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_SWORD)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_HELMET)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_LEGGINGS)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_BATTLE_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_HATCHET)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_DAGGER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_WAR_HAMMER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_MATTOCK)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_SCYTHE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_SHEARS)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 17))))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.BASTION_HOGLIN_STABLE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_SHOVEL)
                                                        .setWeight(10)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_DAGGER)
                                                        .setWeight(10)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .setWeight(12)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.95F)))
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(8).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(12).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(12).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Blocks.GOLD_BLOCK).setWeight(16).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 17))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 3))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.GOLDEN_BATTLE_AXE)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.8F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                        )
                                        .add(LootItem.lootTableItem(Blocks.CRYING_OBSIDIAN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Blocks.GLOWSTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))))
                                        .add(LootItem.lootTableItem(Blocks.GILDED_BLACKSTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Blocks.SOUL_SAND).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_NYLIUM).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.STRING).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 8))))
                                        .add(LootItem.lootTableItem(Items.PORKCHOP).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_FUNGUS).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                                        .add(LootItem.lootTableItem(Blocks.CRIMSON_ROOTS).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(11))
                                        .add(LootItem.lootTableItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(9))
                                        .add(LootItem.lootTableItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.ANCIENT_CITY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(5, 10))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_OTHERSIDE).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.SCULK_CATALYST).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_HOE)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 1)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(30, 50)))
                                        )
                                        .add(LootItem.lootTableItem(Items.LEAD).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .setWeight(2)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(30, 50)))
                                        )
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(3).apply(new EnchantRandomlyFunction.Builder().withEnchantment(enchantments.getOrThrow(Enchantments.SWIFT_SNEAK))))
                                        .add(LootItem.lootTableItem(Items.SCULK).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 10))))
                                        .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.CANDLE).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_LEGGINGS)
                                                        .setWeight(3)
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.6F, 0.9F)))
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 39)))
                                        )
                                        .add(LootItem.lootTableItem(Items.ECHO_SHARD).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.DISC_FRAGMENT_5).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .setWeight(5)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
                                                        .apply(SetPotionFunction.setPotion(Potions.STRONG_REGENERATION))
                                        )
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)))
                                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 10))))
                                        .add(LootItem.lootTableItem(Items.SOUL_TORCH).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.COAL).setWeight(7).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(75))
                                        .add(LootItem.lootTableItem(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 5))
                                        .add(
                                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                                        .setWeight(1)
                                                        .apply(
                                                                SetStewEffectFunction.stewEffect()
                                                                        .withEffect(MobEffects.NIGHT_VISION, ContextIntProviders.between(7, 10))
                                                                        .withEffect(MobEffects.BLINDNESS, ContextIntProviders.between(5, 7))
                                                        )
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                                        .add(LootItem.lootTableItem(Items.PACKED_ICE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                                        .add(LootItem.lootTableItem(Items.SNOWBALL).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                        )
        );
        add(BuiltInLootTables.RUINED_PORTAL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(40).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(30).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(40).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.9F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15))
                                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(3).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments)).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.8F, 0.9F))))
                                        .add(LootItem.lootTableItem(Items.GLISTERING_MELON_SLICE).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.LIGHT_WEIGHTED_PRESSURE_PLATE).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.CLOCK).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.BELL).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(EmptyLootItem.emptyItem().setWeight(5))
                                        .add(LootItem.lootTableItem(Items.LODESTONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_COMMON,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 5))))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))))
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8)))
                                                        .apply(SetPotionFunction.setPotion(Potions.POISON))
                                        )
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.WIND_CHARGE).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3))))
                                        .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.YLEM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.SANCT)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.NUL)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.QUAS)).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
                                                        .setWeight(2)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetOminousBottleAmplifierFunction.setAmplifier(ContextIntProviders.between(0, 1)))
                                        )
                                        .add(LootItem.lootTableItem(Items.WIND_CHARGE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 12))))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_RARE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.SHIELD).setWeight(3).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F, 1))))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(5, 20)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_AXE).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 30)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.IRON_BATTLE_AXE).setWeight(2).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 30)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_CHESTPLATE)
                                                        .setWeight(2)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 30)))
                                        )
                                        .add(LootItem.lootTableItem(MMEItems.MITHRIL_INGOT).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.ANCIENT_METAL_INGOT).setWeight(4).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3))))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.POR)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.AN)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.NOX)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.FLAM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.VAS)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.DES)).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                        enchantments.getOrThrow(Enchantments.SHARPNESS),
                                                                                        enchantments.getOrThrow(Enchantments.BANE_OF_ARTHROPODS),
                                                                                        enchantments.getOrThrow(Enchantments.EFFICIENCY),
                                                                                        enchantments.getOrThrow(Enchantments.FORTUNE),
                                                                                        enchantments.getOrThrow(Enchantments.SILK_TOUCH),
                                                                                        enchantments.getOrThrow(Enchantments.FEATHER_FALLING)
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
                                                                                        enchantments.getOrThrow(Enchantments.RIPTIDE),
                                                                                        enchantments.getOrThrow(Enchantments.LOYALTY),
                                                                                        enchantments.getOrThrow(Enchantments.CHANNELING),
                                                                                        enchantments.getOrThrow(Enchantments.IMPALING),
                                                                                        enchantments.getOrThrow(Enchantments.MENDING)
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_UNIQUE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(4))
                                        .add(LootItem.lootTableItem(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.GUSTER_BANNER_PATTERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_PRECIPICE).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TRIDENT).setWeight(1))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.ORT)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.TYM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.CORP)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.LOR)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.MANI)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.MITHRIL_RUNESTONES.get(Rune.JUX)).setWeight(3))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(EmptyLootItem.emptyItem().setWeight(99))
                                        .add(LootItem.lootTableItem(MMEItems.ADAMANTIUM_SWORD).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(40, 50))))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_COMMON,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD_BLOCK).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(4))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.YLEM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.SANCT)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.NUL)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.QUAS)).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(5, 20)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HELMET)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                        enchantments.getOrThrow(Enchantments.KNOCKBACK),
                                                                                        enchantments.getOrThrow(Enchantments.PUNCH),
                                                                                        enchantments.getOrThrow(Enchantments.SMITE),
                                                                                        enchantments.getOrThrow(Enchantments.LOOTING),
                                                                                        enchantments.getOrThrow(Enchantments.MULTISHOT)
                                                                                )
                                                                        )
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder().withOneOf(HolderSet.direct(enchantments.getOrThrow(Enchantments.BREACH), enchantments.getOrThrow(Enchantments.DENSITY)))
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(new SetEnchantmentsFunction.Builder().withEnchantment(enchantments.getOrThrow(Enchantments.WIND_BURST), ContextIntProviders.exactly(1)))
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(1))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 5))))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(4))
                                        .add(
                                                LootItem.lootTableItem(Items.CROSSBOW)
                                                        .setWeight(4)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(10, 20)))
                                        )
                                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(3))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_CHESTPLATE)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_HELMET)
                                                        .setWeight(3)
                                                        .apply(EnchantWithLevelsFunction.enchantWithLevels(enchantments, ContextIntProviders.between(20, 25)))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(
                                                                                HolderSet.direct(
                                                                                        enchantments.getOrThrow(Enchantments.KNOCKBACK),
                                                                                        enchantments.getOrThrow(Enchantments.PUNCH),
                                                                                        enchantments.getOrThrow(Enchantments.SMITE),
                                                                                        enchantments.getOrThrow(Enchantments.LOOTING),
                                                                                        enchantments.getOrThrow(Enchantments.MULTISHOT)
                                                                                )
                                                                        )
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(
                                                                new EnchantRandomlyFunction.Builder()
                                                                        .withOneOf(HolderSet.direct(enchantments.getOrThrow(Enchantments.BREACH), enchantments.getOrThrow(Enchantments.DENSITY)))
                                                        )
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.BOOK)
                                                        .setWeight(2)
                                                        .apply(new SetEnchantmentsFunction.Builder().withEnchantment(enchantments.getOrThrow(Enchantments.WIND_BURST), ContextIntProviders.exactly(1)))
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.POR)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.AN)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.NOX)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.FLAM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.VAS)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.DES)).setWeight(3))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.ORT)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.TYM)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.CORP)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.LOR)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.MANI)).setWeight(3))
                                        .add(LootItem.lootTableItem(MMEBlocks.ADAMANTIUM_RUNESTONES.get(Rune.JUX)).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.FLOW_BANNER_PATTERN).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.HEAVY_CORE).setWeight(1))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 3))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 14))).setWeight(2))
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 8)))
                                                        .apply(SetPotionFunction.setPotion(Potions.POISON))
                                                        .setWeight(1)
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 8)))
                                                        .apply(SetPotionFunction.setPotion(Potions.SLOWNESS))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 10))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.ACACIA_PLANKS).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.MOSS_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.BONE_MEAL).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.TUFF).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 10))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2)))
                                                        .apply(SetPotionFunction.setPotion(Potions.REGENERATION))
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.POTION)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2)))
                                                        .apply(SetPotionFunction.setPotion(Potions.STRENGTH))
                                        )
                                        .add(LootItem.lootTableItem(Items.MILK_BUCKET).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(
                                                LootItem.lootTableItem(Items.IRON_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.HONEYCOMB).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.COPPER_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.8F)))
                                                        .setWeight(2)
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.COPPER_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.8F)))
                                                        .setWeight(2)
                                        )
                                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BAMBOO_HANGING_SIGN).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BAMBOO_PLANKS).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.SCAFFOLDING).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 10))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TORCH).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 6))).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.TUFF).apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 20))).setWeight(3))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))).setWeight(5))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.7F)))
                                                        .setWeight(5)
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.7F)))
                                                        .setWeight(5)
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.CAKE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 20))).setWeight(20))
                                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(20))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 2))
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.9F)))
                                                        .apply(EnchantRandomlyFunction.randomApplicableEnchantment(enchantments))
                                                        .setWeight(1)
                                        )
                                        .add(
                                                LootItem.lootTableItem(MMEItems.MITHRIL_BATTLE_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F, 0.8F)))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.COMPASS)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.8F)))
                                                        .setWeight(1)
                                        )
                                        .add(LootItem.lootTableItem(Items.BUCKET).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(1))
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_AXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.8F)))
                                                        .setWeight(4)
                                        )
                                        .add(
                                                LootItem.lootTableItem(Items.GOLDEN_PICKAXE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15F, 0.8F)))
                                                        .setWeight(4)
                                        )
                                        .add(LootItem.lootTableItem(Items.BAMBOO_PLANKS).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 15))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.BAKED_POTATO).apply(SetItemCountFunction.setCount(ContextIntProviders.between(6, 10))).setWeight(10))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(1, 3))
                                        .add(LootItem.lootTableItem(Items.TRIAL_KEY).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.HONEYCOMB).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(5, 10))).setWeight(10))
                        )
        );
        add(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))).setWeight(125))
                                        .add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(3))
                                        .add(LootItem.lootTableItem(Items.ARROW).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8))).setWeight(100))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))).setWeight(30))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))).setWeight(50))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_INGOT).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))).setWeight(50))
                                        .add(LootItem.lootTableItem(Items.TRIAL_KEY).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).setWeight(5))
                                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CREATOR_MUSIC_BOX).setWeight(1))
                                        .add(LootItem.lootTableItem(Items.PUMPKIN_PIE).setWeight(100).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                                        .add(LootItem.lootTableItem(Items.COOKED_COD).setWeight(100).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                                        .add(LootItem.lootTableItem(Items.DRIED_KELP).setWeight(100).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 8))))
                        )
        );
        add(BuiltInLootTables.ABANDONED_CAMP_BARREL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(2, 4))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.BOWL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.RABBIT_HIDE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.STRING).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.DYED_CANDLE.white()).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.CUSHION.white()).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.STRAW_BED).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(MMEItems.FLINT_AXE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.FLINT_HATCHET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.4F, 0.7F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_FISHING_ROD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.5F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_FISHING_ROD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3f, 0.5F))))
                        )
        );
        add(BuiltInLootTables.ABANDONED_CAMP_COMMON_CHEST,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(4, 6))
                                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4))))
                                        .add(LootItem.lootTableItem(Items.MAP).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.BONE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.COMPASS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.MAP).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4))))
                                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.LEAD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.BUNDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.RABBIT_HIDE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4))))
                                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.DYED_CANDLE.white()).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.BOW).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_BUCKET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_BUCKET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.COPPER_AXE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_BOOTS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_CHESTPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_LEGGINGS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_SPEAR).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_SWORD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.COPPER_HOE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_DAGGER).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_AXE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_BOOTS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_CHESTPLATE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_LEGGINGS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_SPEAR).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_SWORD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_HOE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_DAGGER).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.5F,0.9F))))
                                        .add(LootItem.lootTableItem(Items.SPYGLASS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(MMEItems.COPPER_SHEARS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F,0.6F))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_HELMET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.3F,0.6F))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_BAMBOO_JUNGLE_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.bamboo_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.BAMBOO_JUNGLE)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_CHERRY_GROVE_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.cherry_grove_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.CHERRY_GROVE)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_BIRCH_FOREST_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.birch_forest_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.BIRCH_FOREST)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_DAPPLED_FOREST_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.dappled_forest_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DAPPLED_FOREST)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_FLOWER_FOREST_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.flower_forest_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.FLOWER_FOREST)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_PALE_GARDEN_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.pale_garden_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.PALE_GARDEN)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_SWAMP_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.swamp_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.SWAMP)))
                                        .add(explorationMapItemExcludingBiome(holderLookup, StructureTags.ON_ABANDONED_CAMP_WINDSWEPT_FOREST_MAPS, MapDecorationTypes.ABANDONED_CAMP, Items.ABANDONED_CAMP_MAP, "filled_map.windswept_forest_camp_map", holderLookup.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.WINDSWEPT_FOREST)))
                        )
        );
        add(BuiltInLootTables.ABANDONED_CAMP_SECRET_CHEST,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(2))
                                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                                        .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetPotionFunction.setPotion(Potions.HEALING)))
                                        .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetPotionFunction.setPotion(Potions.LEAPING)))
                                        .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetPotionFunction.setPotion(Potions.NIGHT_VISION)))
                                        .add(LootItem.lootTableItem(Items.POTION).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))).apply(SetPotionFunction.setPotion(Potions.SWIFTNESS)))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(4, 6))
                                        .add(LootItem.lootTableItem(Items.MAP).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(MMEItems.SILVER_NUGGET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.between(0, 1))
                                        .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                                        .add(LootItem.lootTableItem(Items.IRON_SPEAR).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))))
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_ANCIENT_CITY_MAPS, MapDecorationTypes.ANCIENT_CITY, Items.BURIED_ANCIENT_CITY_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_BURIED_TRIAL_CHAMBERS_MAPS, MapDecorationTypes.TRIAL_CHAMBERS, Items.BURIED_TRIAL_CHAMBERS_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_MINESHAFT_MAPS, MapDecorationTypes.MINESHAFT, Items.BURIED_MINESHAFT_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_DESERT_PYRAMID_MAPS, MapDecorationTypes.DESERT_PYRAMID, Items.DESERT_PYRAMID_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_JUNGLE_PYRAMID_MAPS, MapDecorationTypes.JUNGLE_TEMPLE, Items.JUNGLE_PYRAMID_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_OCEAN_RUIN_WARM_MAPS, MapDecorationTypes.OCEAN_RUIN_WARM, Items.WARM_OCEAN_RUINS_MAP))
                                        .add(explorationMapItem(holderLookup, StructureTags.ON_WOODLAND_MANSION_MAPS, MapDecorationTypes.WOODLAND_MANSION, Items.WOODLAND_MANSION_MAP))
                        )
        );
    }

    @Override
    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        generate();
        map.forEach(biConsumer);
    }

    @Override
    public void run() {
        this.generate();
        if (!this.map.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
        }
        map.forEach((key, builder) -> {
            if (builder == null) {
                throw new IllegalStateException(String.format(Locale.ROOT, "Missing loot table(s) '%s'", key.identifier()));
            }
            this.context.accept(key, builder);
        });

    }

    public void add(ResourceKey<LootTable> table, LootTable.Builder builder) {
        this.map.put(table, builder);
    }

    private static UniformContainerBase.Builder<?> explorationMapItemExcludingBiome(HolderLookup.Provider provider, TagKey<Structure> structure, Holder<MapDecorationType> decoration, Item item, String name, Holder<Biome> biome) {
        return explorationMapItem(provider, structure, decoration, item, name)
                .when(LocationCheck.checkLocation(
                        LocationPredicate.Builder.location().setBiomes(HolderSet.direct(biome))
                ).invert());
    }

    private static UniformContainerBase.Builder<?> explorationMapItem(HolderLookup.Provider provider, TagKey<Structure> structure, Holder<MapDecorationType> decoration, Item item) {
        return explorationMapItem(provider, structure, decoration, item, null);
    }


    private static UniformContainerBase.Builder<?> explorationMapItem(HolderLookup.Provider provider, TagKey<Structure> structure, Holder<MapDecorationType> decoration, Item item, String name) {
        UniformContainerBase.Builder<?> builder = LootItem.lootTableItem(item).setWeight(1);
        if (name != null) {
            builder = builder.apply(SetNameFunction.setName(Component.translatable(name), SetNameFunction.Target.ITEM_NAME));
        }
        return builder
                .apply(ExplorationMapFunction.makeExplorationMap(provider.getOrThrow(structure))
                        .setMapDecoration(decoration)
                        .setSkipKnownStructures(true))
                .apply(discardIfNotValidMap());
    }

    private static FilteredFunction.Builder discardIfNotValidMap() {
        return FilteredFunction.filtered(VillagerTrades.anyValidMap().build())
                .onFail(DiscardItem.discardItem().build());
    }
}
