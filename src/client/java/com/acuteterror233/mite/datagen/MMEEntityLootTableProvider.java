package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.item.enchantment.MMEEnchantments;
import com.acuteterror233.mite.world.entity.MMEEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricEntityLootSubProvider;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.entity.EntityEquipmentPredicate;
import net.minecraft.advancements.predicates.entity.EntityFlagsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProvider;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * MME entity loot table data provider.
 * Generates loot table JSON for MME custom entities (ghouls, spiders, creepers, etc.).
 */
public class MMEEntityLootTableProvider extends FabricEntityLootSubProvider {
    public MMEEntityLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        LootTable.Builder zombieLootTable = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT))
                                .add(LootItem.lootTableItem(Items.CARROT))
                                .add(LootItem.lootTableItem(Items.POTATO).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(enchantments, 0.025F, 0.01F))
                );
        this.add(MMEEntityTypes.GHOUL, zombieLootTable);
        this.add(MMEEntityTypes.SHADOW, zombieLootTable);
        this.add(MMEEntityTypes.WIGHT, zombieLootTable);
        this.add(MMEEntityTypes.INVISIBLE_STALKER, zombieLootTable);

        LootTable.Builder spiderLootTable = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.STRING)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                )
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(Items.SPIDER_EYE)
                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                )
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(enchantments, 0.33333334F, 0.0F))
                );
        this.add(MMEEntityTypes.DEMON_SPIDER, spiderLootTable);
        this.add(MMEEntityTypes.PHASE_SPIDER, spiderLootTable);

        LootTable.Builder creeperLootTable = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.GUNPOWDER)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                )
                );
        this.add(MMEEntityTypes.INFERNAL_CREEPER, creeperLootTable);

        this.add(MMEEntityTypes.FIRE_ELEMENTAL, LootTable.lootTable());

        this.add(MMEEntityTypes.VAMPIRE_BAT, LootTable.lootTable());
        this.add(MMEEntityTypes.NIGHTWING, LootTable.lootTable());
        this.add(MMEEntityTypes.GIANT_VAMPIRE_BAT, LootTable.lootTable());

        add(EntityTypes.COW,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.LEATHER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.BEEF)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
        );
        add(EntityTypes.CHICKEN,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.FEATHER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.CHICKEN)
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
        );
        add(EntityTypes.HOGLIN,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.PORKCHOP)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.LEATHER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
        );
        add(EntityTypes.MOOSHROOM,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.LEATHER)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.BEEF)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
                                        )
                        )
        );
        add(EntityTypes.PIG,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.PORKCHOP)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
        );
        add(EntityTypes.SHEEP,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.MUTTON)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(EntityLootSubProvider.createSheepDispatchPool(BuiltInLootTables.SHEEP.map(lootTables::getOrThrow)))

        );
        add(EntityTypes.RABBIT,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.RABBIT_HIDE)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(Items.RABBIT)
                                                        .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                                        .apply(SmeltItemFunction.smelted().when(shouldSmeltLoot(enchantments)))
                                                        .apply(butcheringMultiplier(enchantments, ContextFloatProviders.between(0, 1)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ContextIntProviders.exactly(1))
                                        .add(LootItem.lootTableItem(Items.RABBIT_FOOT))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(randomChanceAndButcheringBoost(enchantments, 0.1F, 0.03F))
                        )
        );
    }


    public static LootItemCondition.Builder randomChanceAndButcheringBoost(HolderGetter<Enchantment> registryLookup, float f, float g) {
        return () -> new LootItemRandomChanceWithEnchantedBonusCondition(f, new LevelBasedValue.Linear(f + g, g), registryLookup.getOrThrow(MMEEnchantments.BUTCHERING));
    }
    public static EnchantedCountIncreaseFunction.Builder butcheringMultiplier(HolderGetter<Enchantment> registryLookup, Holder<ContextFloatProvider> numberProvider) {
        return new EnchantedCountIncreaseFunction.Builder(registryLookup.getOrThrow(MMEEnchantments.BUTCHERING), numberProvider);
    }
    public static AnyOfCondition.Builder shouldSmeltLoot(HolderGetter<Enchantment> registryLookup) {
        return AnyOfCondition.anyOf(
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
                ),
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.DIRECT_ATTACKER,
                        EntityPredicate.Builder.entity()
                                .equipment(
                                        EntityEquipmentPredicate.Builder.equipment()
                                                .mainhand(
                                                        ItemPredicate.Builder.item()
                                                                .withComponents(
                                                                        DataComponentMatchers.Builder.components()
                                                                                .partial(
                                                                                        DataComponentPredicates.ENCHANTMENTS,
                                                                                        EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(registryLookup.getOrThrow(EnchantmentTags.SMELTS_LOOT), MinMaxBounds.Ints.ANY)))
                                                                                )
                                                                                .build()
                                                                )
                                                )
                                )
                )
        );
    }
}

