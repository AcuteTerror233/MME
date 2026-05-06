package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.MME;
import com.acuteterror233.mite.block.MMEBlocks;
import com.acuteterror233.mite.item.MMEItems;
import com.acuteterror233.mite.registry.tag.MMEEntityTypeTags;
import com.acuteterror233.mite.registry.tag.MMEItemTags;
import com.acuteterror233.mite.world.gen.dimension.MMEDimensionTypeRegistrar;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MMEAdvancementProvider extends FabricAdvancementProvider {
    public MMEAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        HolderGetter<Item> itemGetter = registryLookup.lookupOrThrow(Registries.ITEM);
        HolderGetter<EntityType<?>> entityTypeGetter = registryLookup.lookupOrThrow(Registries.ENTITY_TYPE);
        AdvancementHolder storyRoot = Advancement.Builder.advancement()
                .display(
                        Blocks.GRASS_BLOCK,
                        Component.translatable("advancements.story.root.title"),
                        Component.translatable("mme.advancements.story.root.description"),
                        ResourceLocation.withDefaultNamespace("gui/advancements/backgrounds/stone"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.GRAVEL))
                .save(consumer, "story/root");
        AdvancementHolder wheat_seeds = Advancement.Builder.advancement()
                .parent(storyRoot)
                .display(
                        Items.WHEAT_SEEDS,
                        Component.translatable("mme.advancements.story.wheat_seeds.title"),
                        Component.translatable("mme.advancements.story.wheat_seeds.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        false,
                        false
                )
                .addCriterion("get_wheat_seeds", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT_SEEDS))
                .save(consumer, "story/wheat_seeds");
        AdvancementHolder egg = Advancement.Builder.advancement()
                .parent(wheat_seeds)
                .display(
                        Items.EGG,
                        Component.translatable("mme.advancements.story.egg.title"),
                        Component.translatable("mme.advancements.story.egg.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        false,
                        false
                )
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("eat_egg", ConsumeItemTrigger.TriggerInstance.usedItem(itemGetter, Items.EGG))
                .addCriterion("eat_brown_egg", ConsumeItemTrigger.TriggerInstance.usedItem(itemGetter, Items.BROWN_EGG))
                .addCriterion("eat_blue_egg", ConsumeItemTrigger.TriggerInstance.usedItem(itemGetter, Items.BLUE_EGG))
                .save(consumer, "story/egg");
        AdvancementHolder clay_furnace = Advancement.Builder.advancement()
                .parent(storyRoot)
                .display(
                        MMEBlocks.CLAY_FURNACE,
                        Component.translatable("mme.advancements.story.clay_furnace.title"),
                        Component.translatable("mme.advancements.story.clay_furnace.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_clay_furnace", InventoryChangeTrigger.TriggerInstance.hasItems(MMEBlocks.CLAY_FURNACE))
                .save(consumer, "story/clay_furnace");
        AdvancementHolder torch = Advancement.Builder.advancement()
                .parent(clay_furnace)
                .display(
                        Items.TORCH,
                        Component.translatable("mme.advancements.story.torch.title"),
                        Component.translatable("mme.advancements.story.torch.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_torch", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TORCH))
                .save(consumer, "story/torch");
        AdvancementHolder flint = Advancement.Builder.advancement()
                .parent(storyRoot)
                .display(
                        Items.FLINT,
                        Component.translatable("mme.advancements.story.flint.title"),
                        Component.translatable("mme.advancements.story.flint.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_flint", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer, "story/flint");
        AdvancementHolder flint_hatchet = Advancement.Builder.advancement()
                .parent(flint)
                .display(
                        MMEItems.FLINT_HATCHET,
                        Component.translatable("mme.advancements.story.flint_hatchet.title"),
                        Component.translatable("mme.advancements.story.flint_hatchet.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_flint_hatchet", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.FLINT_HATCHET))
                .save(consumer, "story/flint_hatchet");
        AdvancementHolder log = Advancement.Builder.advancement()
                .parent(flint_hatchet)
                .display(
                        Blocks.OAK_LOG,
                        Component.translatable("mme.advancements.story.log.title"),
                        Component.translatable("mme.advancements.story.log.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_log", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, ItemTags.LOGS)))
                .save(consumer, "story/log");
        AdvancementHolder crafting_table = Advancement.Builder.advancement()
                .parent(log)
                .display(
                        MMEBlocks.FLINT_CRAFTING_TABLE,
                        Component.translatable("mme.advancements.story.crafting_table.title"),
                        Component.translatable("mme.advancements.story.crafting_table.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.CRAFTING_TABLE)))
                .save(consumer, "story/crafting_table");
        AdvancementHolder leather = Advancement.Builder.advancement()
                .parent(crafting_table)
                .display(
                        Items.LEATHER,
                        Component.translatable("mme.advancements.story.leather.title"),
                        Component.translatable("mme.advancements.story.leather.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_leather", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER))
                .save(consumer, "story/leather");
        AdvancementHolder leatherArmor = Advancement.Builder.advancement()
                .parent(leather)
                .display(
                        Items.LEATHER_CHESTPLATE,
                        Component.translatable("mme.advancements.story.leather_armor.title"),
                        Component.translatable("mme.advancements.story.leather_armor.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_leather_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.LEATHER_ARMOR)))
                .save(consumer, "story/leather_armor");
        AdvancementHolder flint_axe = Advancement.Builder.advancement()
                .parent(crafting_table)
                .display(
                        MMEItems.FLINT_AXE,
                        Component.translatable("mme.advancements.story.flint_axe.title"),
                        Component.translatable("mme.advancements.story.flint_axe.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_flint_axe", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.FLINT_AXE))
                .save(consumer, "story/flint_axe");
        AdvancementHolder flint_shovel = Advancement.Builder.advancement()
                .parent(crafting_table)
                .display(
                        MMEItems.FLINT_SHOVEL,
                        Component.translatable("mme.advancements.story.flint_shovel.title"),
                        Component.translatable("mme.advancements.story.flint_shovel.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_flint_shovel", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.FLINT_SHOVEL))
                .save(consumer, "story/flint_shovel");
        AdvancementHolder cudgel = Advancement.Builder.advancement()
                .parent(crafting_table)
                .display(
                        MMEItems.WOODEN_CLUB,
                        Component.translatable("mme.advancements.story.cudgel.title"),
                        Component.translatable("mme.advancements.story.cudgel.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_wooden_club", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.WOODEN_CLUB))
                .save(consumer, "story/cudgel");
        AdvancementHolder monsterHunter = Advancement.Builder.advancement()
                .parent(cudgel)
                .display(
                        Items.BONE,
                        Component.translatable("mme.advancements.story.monster_hunter.title"),
                        Component.translatable("mme.advancements.story.monster_hunter.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("killed_monster", KilledTrigger.TriggerInstance.playerKilledEntity(
                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeGetter, MMEEntityTypeTags.HOSTILE))
                ))
                .save(consumer, "story/monster_hunter");
        AdvancementHolder copperNugget = Advancement.Builder.advancement()
                .parent(flint_shovel)
                .display(
                        MMEItems.COPPER_NUGGET,
                        Component.translatable("mme.advancements.story.copper_nugget.title"),
                        Component.translatable("mme.advancements.story.copper_nugget.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_copper_nugget", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.COPPER_OR_SILVER_NUGGET)))
                .save(consumer, "story/copper_nugget");
        AdvancementHolder metalCraftingTable = Advancement.Builder.advancement()
                .parent(copperNugget)
                .display(
                        MMEBlocks.COPPER_CRAFTING_TABLE,
                        Component.translatable("mme.advancements.story.metal_crafting_table.title"),
                        Component.translatable("mme.advancements.story.metal_crafting_table.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_metal_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.METAL_CRAFTING_TABLE)))
                .save(consumer, "story/metal_crafting_table");
        AdvancementHolder mineStone = Advancement.Builder.advancement()
                .parent(metalCraftingTable)
                .display(
                        MMEBlocks.SILVER_ORE,
                        Component.translatable("mme.advancements.story.mine_stone.title"),
                        Component.translatable("mme.advancements.story.mine_stone.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_copper_or_silver_ore", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.COPPER_OR_SILVER_ORE)))
                .save(consumer, "story/mine_stone");
        AdvancementHolder betterArmor = Advancement.Builder.advancement()
                .parent(metalCraftingTable)
                .display(
                        MMEItems.COPPER_CHESTPLATE,
                        Component.translatable("mme.advancements.story.better_armor.title"),
                        Component.translatable("mme.advancements.story.better_armor.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_better_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.COPPER_OR_SILVER_ARMOR)))
                .save(consumer, "story/better_armor");
        AdvancementHolder pickaxe = Advancement.Builder.advancement()
                .parent(metalCraftingTable)
                .display(
                        MMEItems.COPPER_PICKAXE,
                        Component.translatable("mme.advancements.story.pickaxe.title"),
                        Component.translatable("mme.advancements.story.pickaxe.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.COPPER_OR_SILVER_PICKAXE)))
                .save(consumer, "story/upgrade_tools");
        AdvancementHolder fishingGo = Advancement.Builder.advancement()
                .parent(metalCraftingTable)
                .display(
                        MMEItems.IRON_FISHING_ROD,
                        Component.translatable("mme.advancements.story.fishing_go.title"),
                        Component.translatable("mme.advancements.story.fishing_go.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_metal_fishing_rod", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.METAL_FISHING_RODS)))
                .save(consumer, "story/fishing_go");
        AdvancementHolder deliciousFish = Advancement.Builder.advancement()
                .parent(fishingGo)
                .display(
                        Items.COOKED_COD,
                        Component.translatable("mme.advancements.story.delicious_fish.title"),
                        Component.translatable("mme.advancements.story.delicious_fish.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_cooked_fish", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.COOKED_FISH)))
                .save(consumer, "story/delicious_fish");
        AdvancementHolder smeltIron = Advancement.Builder.advancement()
                .parent(pickaxe)
                .display(
                        Items.IRON_INGOT,
                        Component.translatable("advancements.story.smelt_iron.title"),
                        Component.translatable("advancements.story.smelt_iron.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(consumer, "story/smelt_iron");
        AdvancementHolder ironPickaxe = Advancement.Builder.advancement()
                .parent(smeltIron)
                .display(
                        Items.IRON_PICKAXE,
                        Component.translatable("advancements.story.iron_tools.title"),
                        Component.translatable("advancements.story.iron_tools.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("iron_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_PICKAXE))
                .save(consumer, "story/iron_tools");
        AdvancementHolder mithril = Advancement.Builder.advancement()
                .parent(ironPickaxe)
                .display(
                        MMEItems.MITHRIL_INGOT,
                        Component.translatable("mme.advancements.story.mithril.title"),
                        Component.translatable("mme.advancements.story.mithril.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_mithril", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.MITHRIL_INGOT))
                .save(consumer, "story/mithril");
        AdvancementHolder mineDiamond = Advancement.Builder.advancement()
                .parent(mithril)
                .display(
                        Items.DIAMOND,
                        Component.translatable("advancements.story.mine_diamond.title"),
                        Component.translatable("advancements.story.mine_diamond.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("diamond", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(consumer, "story/mine_diamond");
        AdvancementHolder lavaBucket = Advancement.Builder.advancement()
                .parent(ironPickaxe)
                .display(
                        Items.LAVA_BUCKET,
                        Component.translatable("advancements.story.lava_bucket.title"),
                        Component.translatable("advancements.story.lava_bucket.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.LAVA_BUCKET)))
                .save(consumer, "story/lava_bucket");
        AdvancementHolder obtainArmor = Advancement.Builder.advancement()
                .parent(smeltIron)
                .display(
                        Items.IRON_CHESTPLATE,
                        Component.translatable("advancements.story.obtain_armor.title"),
                        Component.translatable("advancements.story.obtain_armor.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .requirements(AdvancementRequirements.Strategy.OR)
                .addCriterion("iron_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_HELMET))
                .addCriterion("iron_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_CHESTPLATE))
                .addCriterion("iron_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_LEGGINGS))
                .addCriterion("iron_boots", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BOOTS))
                .save(consumer, "story/obtain_armor");
        AdvancementHolder enchantItem = Advancement.Builder.advancement()
                .parent(mineDiamond)
                .display(
                        Items.ENCHANTED_BOOK,
                        Component.translatable("advancements.story.enchant_item.title"),
                        Component.translatable("advancements.story.enchant_item.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("enchanted_item", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .save(consumer, "story/enchant_item");
        AdvancementHolder formObsidian = Advancement.Builder.advancement()
                .parent(lavaBucket)
                .display(
                        Blocks.OBSIDIAN,
                        Component.translatable("advancements.story.form_obsidian.title"),
                        Component.translatable("advancements.story.form_obsidian.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("obsidian", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.OBSIDIAN))
                .save(consumer, "story/form_obsidian");
        AdvancementHolder deflectArrow = Advancement.Builder.advancement()
                .parent(obtainArmor)
                .display(
                        Items.SHIELD,
                        Component.translatable("advancements.story.deflect_arrow.title"),
                        Component.translatable("advancements.story.deflect_arrow.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "deflected_projectile",
                        EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                                DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))).blocked(true)
                        )
                )
                .save(consumer, "story/deflect_arrow");
        AdvancementHolder shinyGear = Advancement.Builder.advancement()
                .parent(mithril)
                .display(
                        MMEItems.MITHRIL_CHESTPLATE,
                        Component.translatable("mme.advancements.story.shiny_gear.title"),
                        Component.translatable("mme.advancements.story.shiny_gear.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_mithril_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.MITHRIL_ARMOR)))
                .save(consumer, "story/shiny_gear");
        AdvancementHolder enterUnderground = Advancement.Builder.advancement()
                .parent(formObsidian)
                .display(
                        MMEBlocks.MANTLE,
                        Component.translatable("mme.advancements.story.enter_the_underground.title"),
                        Component.translatable("mme.advancements.story.enter_the_underground.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("entered_underground", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY))
                .save(consumer, "story/enter_the_underground");
        AdvancementHolder enterNether = Advancement.Builder.advancement()
                .parent(enterUnderground)
                .display(
                        Items.FLINT_AND_STEEL,
                        Component.translatable("advancements.story.enter_the_nether.title"),
                        Component.translatable("advancements.story.enter_the_nether.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("entered_nether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER))
                .save(consumer, "story/enter_the_nether");
        AdvancementHolder cureZombieVillager = Advancement.Builder.advancement()
                .parent(enterNether)
                .display(
                        Items.GOLDEN_APPLE,
                        Component.translatable("advancements.story.cure_zombie_villager.title"),
                        Component.translatable("advancements.story.cure_zombie_villager.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("cured_zombie", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
                .save(consumer, "story/cure_zombie_villager");
        AdvancementHolder followEnderEye = Advancement.Builder.advancement()
                .parent(enterNether)
                .display(
                        Items.ENDER_EYE,
                        Component.translatable("advancements.story.follow_ender_eye.title"),
                        Component.translatable("advancements.story.follow_ender_eye.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion(
                        "in_stronghold",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inStructure(registryLookup.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.STRONGHOLD))
                        )
                )
                .save(consumer, "story/follow_ender_eye");
        AdvancementHolder enterEnd = Advancement.Builder.advancement()
                .parent(followEnderEye)
                .display(
                        Blocks.END_STONE,
                        Component.translatable("advancements.story.enter_the_end.title"),
                        Component.translatable("advancements.story.enter_the_end.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("entered_end", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.END))
                .save(consumer, "story/enter_the_end");

        AdvancementHolder undergroundRoot = Advancement.Builder.advancement()
                .display(
                        Blocks.DEEPSLATE,
                        Component.translatable("mme.advancements.underground.root.title"),
                        Component.translatable("mme.advancements.underground.root.description"),
                        ResourceLocation.fromNamespaceAndPath(MME.MOD_ID, "gui/advancements/backgrounds/underground"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("root", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY))
                .save(consumer, "underground/root");
        AdvancementHolder findAncientMetal = Advancement.Builder.advancement()
                .parent(undergroundRoot)
                .display(
                        MMEItems.ANCIENT_METAL_INGOT,
                        Component.translatable("mme.advancements.underground.find_ancient_metal.title"),
                        Component.translatable("mme.advancements.underground.find_ancient_metal.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_ancient_metal", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.ANCIENT_METAL_INGOT))
                .save(consumer, "underground/find_ancient_metal");
        AdvancementHolder ancientMetalCraftingTable = Advancement.Builder.advancement()
                .parent(findAncientMetal)
                .display(
                        MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE,
                        Component.translatable("mme.advancements.underground.ancient_metal_crafting_table.title"),
                        Component.translatable("mme.advancements.underground.ancient_metal_crafting_table.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_ancient_metal_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(MMEBlocks.ANCIENT_METAL_CRAFTING_TABLE))
                .save(consumer, "underground/ancient_metal_crafting_table");
        AdvancementHolder mithrilTool = Advancement.Builder.advancement()
                .parent(ancientMetalCraftingTable)
                .display(
                        MMEItems.MITHRIL_PICKAXE,
                        Component.translatable("mme.advancements.underground.mithril_tool.title"),
                        Component.translatable("mme.advancements.underground.mithril_tool.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_mithril_tool", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.MITHRIL_TOOLS)))
                .save(consumer, "underground/mithril_tool");
        AdvancementHolder findAdamantium = Advancement.Builder.advancement()
                .parent(mithrilTool)
                .display(
                        MMEBlocks.ADAMANTIUM_ORE,
                        Component.translatable("mme.advancements.underground.find_adamantium.title"),
                        Component.translatable("mme.advancements.underground.find_adamantium.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_adamantium_ore", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.ADAMANTIUM_ORE)))
                .save(consumer, "underground/find_adamantium");
        AdvancementHolder smeltAdamantium = Advancement.Builder.advancement()
                .parent(findAdamantium)
                .display(
                        MMEItems.ADAMANTIUM_INGOT,
                        Component.translatable("mme.advancements.underground.smelt_adamantium.title"),
                        Component.translatable("mme.advancements.underground.smelt_adamantium.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_adamantium_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(MMEItems.ADAMANTIUM_INGOT))
                .save(consumer, "underground/smelt_adamantium");
        AdvancementHolder adamantiumTool = Advancement.Builder.advancement()
                .parent(smeltAdamantium)
                .display(
                        MMEItems.ADAMANTIUM_PICKAXE,
                        Component.translatable("mme.advancements.underground.adamantium_tool.title"),
                        Component.translatable("mme.advancements.underground.adamantium_tool.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_adamantium_tool", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.ADAMANTIUM_TOOLS)))
                .save(consumer, "underground/adamantium_tool");
        AdvancementHolder adamantiumArmor = Advancement.Builder.advancement()
                .parent(smeltAdamantium)
                .display(
                        MMEItems.ADAMANTIUM_CHESTPLATE,
                        Component.translatable("mme.advancements.underground.adamantium_armor.title"),
                        Component.translatable("mme.advancements.underground.adamantium_armor.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("get_adamantium_armor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemGetter, MMEItemTags.ADAMANTIUM_ARMOR)))
                .save(consumer, "underground/adamantium_armor");
        AdvancementHolder enterAncientCity = Advancement.Builder.advancement()
                .parent(undergroundRoot)
                .display(
                        Items.SCULK_CATALYST,
                        Component.translatable("mme.advancements.underground.enter_ancient_city.title"),
                        Component.translatable("mme.advancements.underground.enter_ancient_city.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("in_ancient_city", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.inStructure(registryLookup.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.ANCIENT_CITY))
                ))
                .save(consumer, "underground/enter_ancient_city");
        AdvancementHolder deepDown = Advancement.Builder.advancement()
                .parent(undergroundRoot)
                .display(
                        MMEBlocks.MANTLE,
                        Component.translatable("mme.advancements.underground.deep_down.title"),
                        Component.translatable("mme.advancements.underground.deep_down.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("deep_down", PlayerTrigger.TriggerInstance.located(
                        LocationPredicate.Builder.location()
                                .setDimension(MMEDimensionTypeRegistrar.UNDERGROUND_LEVEL_KEY)
                                .setY(MinMaxBounds.Doubles.atMost(-60.0))
                ))
                .save(consumer, "underground/deep_down");
        AdvancementHolder mantlePortal = Advancement.Builder.advancement()
                .parent(deepDown)
                .display(
                        Items.FLINT_AND_STEEL,
                        Component.translatable("mme.advancements.underground.mantle_portal.title"),
                        Component.translatable("mme.advancements.underground.mantle_portal.description"),
                        null,
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("entered_nether_from_underground", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER))
                .save(consumer, "underground/mantle_portal");
    }
}
