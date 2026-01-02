package com.acuteterror233.mite.datagen;

import com.acuteterror233.mite.Mme;
import com.acuteterror233.mite.block.AtBlocks;
import com.acuteterror233.mite.item.AtItems;
import com.acuteterror233.mite.registry.tag.AtTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class AtRecipeGenerator extends FabricRecipeProvider {
    public AtRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtItems.RAW_ADAMANTIUM), RecipeCategory.MISC, AtItems.ADAMANTIUM_INGOT, 5F, 200)
                    .criterion("has_raw_adamantium", this.conditionsFromItem(AtItems.RAW_ADAMANTIUM))
                    .offerTo(this.exporter, "adamantium_ingot_from_raw_adamantium");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtItems.RAW_MITHRIL), RecipeCategory.MISC, AtItems.MITHRIL_INGOT, 3F, 200)
                        .criterion("has_raw_mithril", this.conditionsFromItem(AtItems.RAW_MITHRIL))
                        .offerTo(this.exporter, "mithril_ingot_from_raw_mithril");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtItems.RAW_SILVER), RecipeCategory.MISC, AtItems.SILVER_INGOT, 1F, 200)
                        .criterion("has_raw_silver", this.conditionsFromItem(AtItems.RAW_SILVER))
                        .offerTo(this.exporter, "silver_ingot_from_raw_silver");

                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.ADAMANTIUM_ORE), RecipeCategory.MISC, AtItems.ADAMANTIUM_INGOT, 5F, 200)
                        .criterion("has_adamantium_ore", this.conditionsFromItem(AtBlocks.ADAMANTIUM_ORE))
                        .offerTo(this.exporter, "adamantium_ingot_from_adamantium_ore");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.MITHRIL_ORE), RecipeCategory.MISC, AtItems.MITHRIL_INGOT, 3F, 200)
                        .criterion("has_mithril_ore", this.conditionsFromItem(AtBlocks.MITHRIL_ORE))
                        .offerTo(this.exporter, "mithril_ingot_from_mithril_ore");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.SILVER_ORE), RecipeCategory.MISC, AtItems.SILVER_INGOT, 1F, 200)
                        .criterion("has_silver_ore", this.conditionsFromItem(AtBlocks.SILVER_ORE))
                        .offerTo(this.exporter, "silver_ingot_from_silver_ore");

                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.DEEPSLATE_ADAMANTIUM_ORE), RecipeCategory.MISC, AtItems.ADAMANTIUM_INGOT, 5F, 200)
                        .criterion("has_deepslate_adamantium_ore", this.conditionsFromItem(AtBlocks.DEEPSLATE_ADAMANTIUM_ORE))
                        .offerTo(this.exporter, "adamantium_ingot_from_deepslate_adamantium_ore");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.DEEPSLATE_MITHRIL_ORE), RecipeCategory.MISC, AtItems.MITHRIL_INGOT, 3F, 200)
                        .criterion("has_deepslate_mithril_ore", this.conditionsFromItem(AtBlocks.DEEPSLATE_MITHRIL_ORE))
                        .offerTo(this.exporter, "mithril_ingot_from_deepslate_mithril_ore");
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(AtBlocks.DEEPSLATE_SILVER_ORE), RecipeCategory.MISC, AtItems.SILVER_INGOT, 1F, 200)
                        .criterion("has_deepslate_silver_ore", this.conditionsFromItem(AtBlocks.DEEPSLATE_SILVER_ORE))
                        .offerTo(this.exporter, "silver_ingot_from_deepslate_silver_ore");

                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.ADAMANTIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, AtBlocks.ADAMANTIUM_BLOCK, "adamantium_block_from_adamantium_ingot", null , "adamantium_ingot_from_adamantium_block",null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.ANCIENT_METAL_INGOT, RecipeCategory.BUILDING_BLOCKS, AtBlocks.ANCIENT_METAL_BLOCK, "ancient_metal_block_from_ancient_metal_ingot", null , "ancient_metal_ingot_from_ancient_metal_block", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.MITHRIL_INGOT, RecipeCategory.BUILDING_BLOCKS, AtBlocks.MITHRIL_BLOCK, "mithril_block_from_mithril_ingot", null , "mithril_ingot_from_mithril_block", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.SILVER_INGOT, RecipeCategory.BUILDING_BLOCKS, AtBlocks.SILVER_BLOCK, "silver_block_from_silver_ingot", null , "silver_ingot_from_silver_block", null);

                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.ADAMANTIUM_NUGGET, RecipeCategory.MISC, AtItems.ADAMANTIUM_INGOT, "adamantium_ingot_from_adamantium_nugget", null,  "adamantium_nugget_from_adamantium_ingot", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.ANCIENT_METAL_NUGGET, RecipeCategory.MISC, AtItems.ANCIENT_METAL_INGOT, "ancient_metal_ingot_from_ancient_metal_nugget", null,  "ancient_metal_nugget_from_ancient_metal_ingot", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.MITHRIL_NUGGET, RecipeCategory.MISC, AtItems.MITHRIL_INGOT, "mithril_ingot_from_mithril_nugget", null,  "mithril_nugget_from_mithril_ingot", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.SILVER_NUGGET, RecipeCategory.MISC, AtItems.SILVER_INGOT, "silver_ingot_from_silver_nugget", null,  "silver_nugget_from_silver_ingot", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.COPPER_NUGGET, RecipeCategory.MISC, Items.COPPER_INGOT, "copper_ingot_from_copper_nugget", null,  "copper_nugget_from_copper_ingot", null);

                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.DIAMOND_SHARD, RecipeCategory.MISC, Items.DIAMOND, "diamond_from_diamond_shard", null,  "diamond_shard_from_diamond", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.EMERALD_SHARD, RecipeCategory.MISC, Items.EMERALD, "emerald_from_emerald_shard", null,  "emerald_shard_from_emerald", null);
                offerReversibleCompactingRecipes(RecipeCategory.MISC, AtItems.OBSIDIAN_SHARD, RecipeCategory.MISC, Blocks.OBSIDIAN, "obsidian_from_obsidian_shard", null,  "obsidian_shard_from_obsidian", null);
                offer2x2CompactingRecipe(RecipeCategory.MISC, Items.FLINT, AtItems.FLINT_SHARD);
                offer2x2CompactingRecipe(RecipeCategory.MISC, Items.QUARTZ, AtItems.QUARTZ_SHARD);

                offerCraftingTableRecipes(AtBlocks.ADAMANTIUM_CRAFTING_TABLE, AtItems.ADAMANTIUM_INGOT);
                offerCraftingTableRecipes(AtBlocks.ANCIENT_METAL_CRAFTING_TABLE, AtItems.ANCIENT_METAL_INGOT);
                offerCraftingTableRecipes(AtBlocks.MITHRIL_CRAFTING_TABLE, AtItems.MITHRIL_INGOT);
                offerCraftingTableRecipes(AtBlocks.SILVER_CRAFTING_TABLE, AtItems.SILVER_INGOT);
                offerCraftingTableRecipes(AtBlocks.COPPER_CRAFTING_TABLE, Items.COPPER_INGOT);
                offerCraftingTableRecipes(AtBlocks.IRON_CRAFTING_TABLE, Items.IRON_INGOT);
                offerCraftingTableRecipes(AtBlocks.GOLD_CRAFTING_TABLE, Items.GOLD_INGOT);
                createShapeless(RecipeCategory.DECORATIONS, AtBlocks.FLINT_CRAFTING_TABLE)
                        .input(AtItems.FLINT_KNIFE)
                        .input(ItemTags.OAK_LOGS)
                        .criterion(hasItem(AtItems.FLINT_KNIFE), this.conditionsFromItem(AtItems.FLINT_KNIFE))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.DECORATIONS, AtBlocks.OBSIDIAN_CRAFTING_TABLE)
                        .input(AtItems.OBSIDIAN_KNIFE)
                        .input(ItemTags.OAK_LOGS)
                        .criterion(hasItem(AtItems.OBSIDIAN_KNIFE), this.conditionsFromItem(AtItems.OBSIDIAN_KNIFE))
                        .offerTo(this.exporter);

                offerAnvilRecipes(AtBlocks.ADAMANTIUM_ANVIL, AtItems.ADAMANTIUM_INGOT, AtBlocks.ADAMANTIUM_BLOCK);
                offerAnvilRecipes(AtBlocks.ANCIENT_METAL_ANVIL, AtItems.ANCIENT_METAL_INGOT, AtBlocks.ANCIENT_METAL_BLOCK);
                offerAnvilRecipes(AtBlocks.MITHRIL_ANVIL, AtItems.MITHRIL_INGOT, AtBlocks.MITHRIL_BLOCK);
                offerAnvilRecipes(AtBlocks.SILVER_ANVIL, AtItems.SILVER_INGOT, AtBlocks.SILVER_BLOCK);
                offerAnvilRecipes(AtBlocks.COPPER_ANVIL, Items.COPPER_INGOT, Blocks.COPPER_BLOCK);
                offerAnvilRecipes(AtBlocks.GOLDEN_ANVIL, Items.GOLD_INGOT, Blocks.GOLD_BLOCK);

                offerBarsRecipes(AtBlocks.ADAMANTIUM_BARS, AtItems.ADAMANTIUM_INGOT);
                offerBarsRecipes(AtBlocks.ANCIENT_METAL_BARS, AtItems.ANCIENT_METAL_INGOT);
                offerBarsRecipes(AtBlocks.MITHRIL_BARS, AtItems.MITHRIL_INGOT);
                offerBarsRecipes(AtBlocks.SILVER_BARS, AtItems.SILVER_INGOT);
                offerBarsRecipes(AtBlocks.COPPER_BARS, Items.COPPER_INGOT);
                offerBarsRecipes(AtBlocks.GOLDEN_BARS, Items.GOLD_INGOT);

                offerDoorRecipes(AtBlocks.ADAMANTIUM_DOOR, AtItems.ADAMANTIUM_INGOT);
                offerDoorRecipes(AtBlocks.ANCIENT_METAL_DOOR, AtItems.ANCIENT_METAL_INGOT);
                offerDoorRecipes(AtBlocks.MITHRIL_DOOR, AtItems.MITHRIL_INGOT);
                offerDoorRecipes(AtBlocks.SILVER_DOOR, AtItems.SILVER_INGOT);
                offerDoorRecipes(AtBlocks.GOLDEN_DOOR, Items.GOLD_INGOT);

                offerFishingRodRecipes(AtItems.ADAMANTIUM_FISHING_ROD, AtItems.ADAMANTIUM_NUGGET);
                offerFishingRodRecipes(AtItems.ANCIENT_METAL_FISHING_ROD, AtItems.ANCIENT_METAL_NUGGET);
                offerFishingRodRecipes(AtItems.MITHRIL_FISHING_ROD, AtItems.MITHRIL_NUGGET);
                offerFishingRodRecipes(AtItems.SILVER_FISHING_ROD, AtItems.SILVER_NUGGET);
                offerFishingRodRecipes(AtItems.COPPER_FISHING_ROD, AtItems.COPPER_NUGGET);
                offerFishingRodRecipes(AtItems.IRON_FISHING_ROD, Items.IRON_NUGGET);
                offerFishingRodRecipes(AtItems.GOLDEN_FISHING_ROD, Items.GOLD_NUGGET);
                offerFishingRodRecipes(AtItems.OBSIDIAN_FISHING_ROD, AtItems.OBSIDIAN_SHARD);
                offerFishingRodRecipes(AtItems.FLINT_FISHING_ROD, Items.FLINT);
                
                offerArmorRecipes(AtItems.ADAMANTIUM_INGOT, AtItems.ADAMANTIUM_HELMET, AtItems.ADAMANTIUM_CHESTPLATE, AtItems.ADAMANTIUM_LEGGINGS, AtItems.ADAMANTIUM_BOOTS);
                offerArmorRecipes(AtItems.ANCIENT_METAL_INGOT, AtItems.ANCIENT_METAL_HELMET, AtItems.ANCIENT_METAL_CHESTPLATE, AtItems.ANCIENT_METAL_LEGGINGS, AtItems.ANCIENT_METAL_BOOTS);
                offerArmorRecipes(AtItems.MITHRIL_INGOT, AtItems.MITHRIL_HELMET, AtItems.MITHRIL_CHESTPLATE, AtItems.MITHRIL_LEGGINGS, AtItems.MITHRIL_BOOTS);
                offerArmorRecipes(AtItems.SILVER_INGOT, AtItems.SILVER_HELMET, AtItems.SILVER_CHESTPLATE, AtItems.SILVER_LEGGINGS, AtItems.SILVER_BOOTS);
                offerArmorRecipes(Items.COPPER_INGOT, AtItems.COPPER_HELMET, AtItems.COPPER_CHESTPLATE, AtItems.COPPER_LEGGINGS, AtItems.COPPER_BOOTS);

                offerArmorRecipes(AtItems.ADAMANTIUM_CHAINS, AtItems.ADAMANTIUM_CHAINMAIL_HELMET, AtItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE, AtItems.ADAMANTIUM_CHAINMAIL_LEGGINGS, AtItems.ADAMANTIUM_CHAINMAIL_BOOTS);
                offerArmorRecipes(AtItems.ANCIENT_METAL_CHAINS, AtItems.ANCIENT_METAL_CHAINMAIL_HELMET, AtItems.ANCIENT_METAL_CHAINMAIL_CHESTPLATE, AtItems.ANCIENT_METAL_CHAINMAIL_LEGGINGS, AtItems.ANCIENT_METAL_CHAINMAIL_BOOTS);
                offerArmorRecipes(AtItems.MITHRIL_CHAINS, AtItems.MITHRIL_CHAINMAIL_HELMET, AtItems.MITHRIL_CHAINMAIL_CHESTPLATE, AtItems.MITHRIL_CHAINMAIL_LEGGINGS, AtItems.MITHRIL_CHAINMAIL_BOOTS);
                offerArmorRecipes(AtItems.SILVER_CHAINS, AtItems.SILVER_CHAINMAIL_HELMET, AtItems.SILVER_CHAINMAIL_CHESTPLATE, AtItems.SILVER_CHAINMAIL_LEGGINGS, AtItems.SILVER_CHAINMAIL_BOOTS);
                offerArmorRecipes(AtItems.COPPER_CHAINS, AtItems.COPPER_CHAINMAIL_HELMET, AtItems.COPPER_CHAINMAIL_CHESTPLATE, AtItems.COPPER_CHAINMAIL_LEGGINGS, AtItems.COPPER_CHAINMAIL_BOOTS);

                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_HELMET, RecipeCategory.COMBAT, Items.NETHERITE_HELMET);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_CHESTPLATE, RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_LEGGINGS, RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_BOOTS, RecipeCategory.COMBAT, Items.NETHERITE_BOOTS);

                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_CHAINMAIL_HELMET, RecipeCategory.COMBAT, AtItems.NETHERITE_CHAINMAIL_HELMET);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_CHAINMAIL_CHESTPLATE, RecipeCategory.COMBAT, AtItems.NETHERITE_CHAINMAIL_CHESTPLATE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_CHAINMAIL_LEGGINGS, RecipeCategory.COMBAT, AtItems.NETHERITE_CHAINMAIL_LEGGINGS);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_CHAINMAIL_BOOTS, RecipeCategory.COMBAT, AtItems.NETHERITE_CHAINMAIL_BOOTS);

                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_SWORD, RecipeCategory.COMBAT, Items.NETHERITE_SWORD);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_DAGGER, RecipeCategory.COMBAT, AtItems.NETHERITE_DAGGER);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_PICKAXE, RecipeCategory.TOOLS, Items.NETHERITE_PICKAXE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_WAR_HAMMER, RecipeCategory.COMBAT, AtItems.NETHERITE_WAR_HAMMER);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_AXE, RecipeCategory.TOOLS, Items.NETHERITE_AXE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_HATCHET, RecipeCategory.TOOLS, AtItems.NETHERITE_HATCHET);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_BATTLE_AXE, RecipeCategory.COMBAT, AtItems.NETHERITE_BATTLE_AXE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_SHOVEL, RecipeCategory.TOOLS, Items.NETHERITE_SHOVEL);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_HOE, RecipeCategory.TOOLS, Items.NETHERITE_HOE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_MATTOCK, RecipeCategory.TOOLS, AtItems.NETHERITE_MATTOCK);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_SCYTHE, RecipeCategory.TOOLS, AtItems.NETHERITE_SCYTHE);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_SHEARS, RecipeCategory.TOOLS, AtItems.NETHERITE_SHEARS);
                offerNetheriteUpgradeRecipe(AtItems.ADAMANTIUM_BUCKET, RecipeCategory.TOOLS, AtItems.NETHERITE_BUCKET);
                
                offerToolRecipes(AtItems.ADAMANTIUM_INGOT,
                        AtItems.ADAMANTIUM_SWORD,
                        AtItems.ADAMANTIUM_DAGGER,
                        AtItems.ADAMANTIUM_PICKAXE,
                        AtItems.ADAMANTIUM_WAR_HAMMER,
                        AtItems.ADAMANTIUM_AXE,
                        AtItems.ADAMANTIUM_HATCHET,
                        AtItems.ADAMANTIUM_BATTLE_AXE,
                        AtItems.ADAMANTIUM_SHOVEL,
                        AtItems.ADAMANTIUM_HOE,
                        AtItems.ADAMANTIUM_MATTOCK,
                        AtItems.ADAMANTIUM_SCYTHE,
                        AtItems.ADAMANTIUM_SHEARS
                );
                offerToolRecipes(AtItems.ANCIENT_METAL_INGOT,
                        AtItems.ANCIENT_METAL_SWORD,
                        AtItems.ANCIENT_METAL_DAGGER,
                        AtItems.ANCIENT_METAL_PICKAXE,
                        AtItems.ANCIENT_METAL_WAR_HAMMER,
                        AtItems.ANCIENT_METAL_AXE,
                        AtItems.ANCIENT_METAL_HATCHET,
                        AtItems.ANCIENT_METAL_BATTLE_AXE,
                        AtItems.ANCIENT_METAL_SHOVEL,
                        AtItems.ANCIENT_METAL_HOE,
                        AtItems.ANCIENT_METAL_MATTOCK,
                        AtItems.ANCIENT_METAL_SCYTHE,
                        AtItems.ANCIENT_METAL_SHEARS
                );
                offerToolRecipes(AtItems.MITHRIL_INGOT,
                        AtItems.MITHRIL_SWORD,
                        AtItems.MITHRIL_DAGGER,
                        AtItems.MITHRIL_PICKAXE,
                        AtItems.MITHRIL_WAR_HAMMER,
                        AtItems.MITHRIL_AXE,
                        AtItems.MITHRIL_HATCHET,
                        AtItems.MITHRIL_BATTLE_AXE,
                        AtItems.MITHRIL_SHOVEL,
                        AtItems.MITHRIL_HOE,
                        AtItems.MITHRIL_MATTOCK,
                        AtItems.MITHRIL_SCYTHE,
                        AtItems.MITHRIL_SHEARS
                );
                offerToolRecipes(AtItems.SILVER_INGOT,
                        AtItems.SILVER_SWORD,
                        AtItems.SILVER_DAGGER,
                        AtItems.SILVER_PICKAXE,
                        AtItems.SILVER_WAR_HAMMER,
                        AtItems.SILVER_AXE,
                        AtItems.SILVER_HATCHET,
                        AtItems.SILVER_BATTLE_AXE,
                        AtItems.SILVER_SHOVEL,
                        AtItems.SILVER_HOE,
                        AtItems.SILVER_MATTOCK,
                        AtItems.SILVER_SCYTHE,
                        AtItems.SILVER_SHEARS
                );
                offerToolRecipes(Items.COPPER_INGOT,
                        AtItems.COPPER_SWORD,
                        AtItems.COPPER_DAGGER,
                        AtItems.COPPER_PICKAXE,
                        AtItems.COPPER_WAR_HAMMER,
                        AtItems.COPPER_AXE,
                        AtItems.COPPER_HATCHET,
                        AtItems.COPPER_BATTLE_AXE,
                        AtItems.COPPER_SHOVEL,
                        AtItems.COPPER_HOE,
                        AtItems.COPPER_MATTOCK,
                        AtItems.COPPER_SCYTHE,
                        AtItems.COPPER_SHEARS
                );
                offerBattleaxeRecipes(Items.IRON_INGOT, AtItems.IRON_BATTLE_AXE);
                offerDaggerRecipes(Items.IRON_INGOT, AtItems.IRON_DAGGER);
                offerHatchetRecipes(Items.IRON_INGOT, AtItems.IRON_HATCHET);
                offerMattockRecipes(Items.IRON_INGOT, AtItems.IRON_MATTOCK);
                offerScytheRecipes(Items.IRON_INGOT, AtItems.IRON_SCYTHE);
                offerWarhammerRecipes(Items.IRON_INGOT, AtItems.IRON_WAR_HAMMER);

                offerBattleaxeRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_BATTLE_AXE);
                offerDaggerRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_DAGGER);
                offerHatchetRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_HATCHET);
                offerMattockRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_MATTOCK);
                offerScytheRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_SCYTHE);
                offerWarhammerRecipes(Items.GOLD_INGOT, AtItems.GOLDEN_WAR_HAMMER);

                offerAxeRecipesInString(Items.FLINT, AtItems.FLINT_AXE);
                offerHatchetRecipesInString(Items.FLINT, AtItems.FLINT_HATCHET);
                offerDaggerRecipesIsString(Items.FLINT, AtItems.FLINT_KNIFE);
                offerShovelRecipesIsString(Items.FLINT, AtItems.FLINT_SHOVEL);

                offerAxeRecipesInString(AtItems.OBSIDIAN_SHARD, AtItems.OBSIDIAN_AXE);
                offerHatchetRecipesInString(AtItems.OBSIDIAN_SHARD, AtItems.OBSIDIAN_HATCHET);
                offerDaggerRecipesIsString(AtItems.OBSIDIAN_SHARD, AtItems.OBSIDIAN_KNIFE);
                offerShovelRecipesIsString(AtItems.OBSIDIAN_SHARD, AtItems.OBSIDIAN_SHOVEL);

                createShaped(RecipeCategory.COMBAT, AtItems.WOODEN_CLUB)
                        .input('a', ItemTags.PLANKS)
                        .input('b', Items.STICK)
                        .pattern("a")
                        .pattern("a")
                        .pattern("b")
                        .criterion(hasItem(Items.STICK), this.conditionsFromItem(Items.STICK))
                        .offerTo(this.exporter);
                createShaped(RecipeCategory.COMBAT, AtItems.WOODEN_CUDGEL)
                        .input('a', ItemTags.PLANKS)
                        .input('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .criterion(hasItem(Items.STICK), this.conditionsFromItem(Items.STICK))
                        .offerTo(this.exporter);

                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_bowl_milk");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 2)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .input(AtItems.BOWL_MILK)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_bowl_milk_2");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 2)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_2");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 3)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_3");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 4)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_4");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 5)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_5");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 6)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_6");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 7)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_7");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 8)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_8");
                createShapeless(RecipeCategory.FOOD, AtItems.CHEESE, 9)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .input(AtTags.MILK_BUCKET)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter, "cheese_form_milk_bucket_9");
                createShapeless(RecipeCategory.FOOD, AtItems.CHOCOLATE)
                        .input(Items.SUGAR)
                        .input(Items.COCOA_BEANS)
                        .criterion(hasItem(Items.SUGAR), this.conditionsFromItem(Items.SUGAR))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.DOUGH)
                        .input(AtItems.FLOUR)
                        .input(AtItems.BOWL_WATER)
                        .criterion(hasItem(AtItems.FLOUR), this.conditionsFromItem(AtItems.FLOUR))
                        .offerTo(this.exporter, "dough_form_bowl_water");
                createShapeless(RecipeCategory.FOOD, AtItems.DOUGH, 4)
                        .input(AtItems.FLOUR)
                        .input(AtItems.FLOUR)
                        .input(AtItems.FLOUR)
                        .input(AtItems.FLOUR)
                        .input(AtTags.WATER_BUCKET)
                        .criterion(hasItem(AtItems.FLOUR), this.conditionsFromItem(AtItems.FLOUR))
                        .offerTo(this.exporter, "dough_form_water_bucket");
                createShapeless(RecipeCategory.FOOD, AtItems.PUMPKIN_SOUP)
                        .input(Items.PUMPKIN)
                        .input(AtItems.BOWL_WATER)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.SORBET)
                        .input(Items.SUGAR)
                        .input(Items.BOWL)
                        .input(Items.SNOWBALL)
                        .input(AtItems.ORANGE)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.VEGETABLE_SOUP)
                        .input(Items.CARROT)
                        .input(Items.POTATO)
                        .input(Items.BOWL)
                        .input(AtItems.ONION)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.BEEF_STEW)
                        .input(Items.COOKED_BEEF)
                        .input(Items.POTATO)
                        .input(Items.BOWL)
                        .input(Items.BROWN_MUSHROOM)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.BOWL_MILK, 4)
                        .input(AtTags.MILK_BUCKET)
                        .input(Items.BOWL)
                        .input(Items.BOWL)
                        .input(Items.BOWL)
                        .input(Items.BOWL)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.BOWL_SALAD)
                        .input(Items.BOWL)
                        .input(Items.DANDELION)
                        .input(Items.DANDELION)
                        .input(Items.DANDELION)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.BOWL_WATER, 3)
                        .input(AtTags.WATER_BUCKET)
                        .input(Items.BOWL)
                        .input(Items.BOWL)
                        .input(Items.BOWL)
                        .criterion(hasItem(Items.BOWL), this.conditionsFromItem(Items.BOWL))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.CEREAL)
                        .input(AtItems.BOWL_MILK)
                        .input(Items.WHEAT)
                        .input(AtItems.FLOUR)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.CHICKEN_SOUP)
                        .input(Items.COOKED_CHICKEN)
                        .input(AtItems.BOWL_WATER)
                        .input(AtItems.ONION)
                        .input(Items.CARROT)
                        .criterion(hasItem(Items.COOKED_CHICKEN), this.conditionsFromItem(Items.COOKED_CHICKEN))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.CREAM_OF_MUSHROOM_SOUP)
                        .input(AtItems.BOWL_MILK)
                        .input(Items.BROWN_MUSHROOM)
                        .input(Items.BROWN_MUSHROOM)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.CREAM_OF_VEGETABLE_SOUP)
                        .input(AtItems.BOWL_MILK)
                        .input(Items.CARROT)
                        .input(Items.POTATO)
                        .input(AtItems.ONION)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.ICE_CREAM)
                        .input(AtItems.BOWL_MILK)
                        .input(Items.SUGAR)
                        .input(Items.COCOA_BEANS)
                        .input(Items.SNOWBALL)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.MASHED_POTATO)
                        .input(Items.BAKED_POTATO)
                        .input(AtItems.CHEESE)
                        .input(AtItems.BOWL_MILK)
                        .criterion(hasItem(AtItems.BOWL_MILK), this.conditionsFromItem(AtItems.BOWL_MILK))
                        .offerTo(this.exporter);
                createShapeless(RecipeCategory.FOOD, AtItems.PORRIDGE)
                        .input(AtItems.BOWL_WATER)
                        .input(AtItems.BLUEBERRIE)
                        .input(Items.SUGAR)
                        .input(Items.WHEAT_SEEDS)
                        .criterion(hasItem(AtItems.BOWL_WATER), this.conditionsFromItem(AtItems.BOWL_WATER))
                        .offerTo(this.exporter);

                offerArrowRecipes(AtItems.ADAMANTIUM_NUGGET, AtItems.ADAMANTIUM_ARROW);
                offerArrowRecipes(AtItems.ANCIENT_METAL_NUGGET, AtItems.ANCIENT_METAL_ARROW);
                offerArrowRecipes(AtItems.FLINT_SHARD, AtItems.FLINT_ARROW);
                offerArrowRecipes(Items.IRON_NUGGET, AtItems.IRON_ARROW);
                offerArrowRecipes(Items.GOLD_NUGGET, AtItems.GOLDEN_ARROW);
                offerArrowRecipes(AtItems.MITHRIL_NUGGET, AtItems.MITHRIL_ARROW);
                offerArrowRecipes(AtItems.OBSIDIAN_SHARD, AtItems.OBSIDIAN_ARROW);
                offerArrowRecipes(AtItems.SILVER_NUGGET, AtItems.SILVER_ARROW);
                offerArrowRecipes(AtItems.COPPER_NUGGET, AtItems.COPPER_ARROW);

                offerChainsRecipes(AtItems.ADAMANTIUM_NUGGET, AtItems.ADAMANTIUM_CHAINS);
                offerChainsRecipes(AtItems.MITHRIL_NUGGET, AtItems.MITHRIL_CHAINS);
                offerChainsRecipes(AtItems.ANCIENT_METAL_NUGGET, AtItems.ANCIENT_METAL_CHAINS);
                offerChainsRecipes(Items.IRON_NUGGET, AtItems.IRON_CHAINS);
                offerChainsRecipes(Items.GOLD_NUGGET, AtItems.GOLDEN_CHAINS);
                offerChainsRecipes(AtItems.SILVER_NUGGET, AtItems.SILVER_CHAINS);
                offerChainsRecipes(AtItems.COPPER_NUGGET, AtItems.COPPER_CHAINS);

                offerBucketRecipes(AtItems.ADAMANTIUM_INGOT, AtItems.ADAMANTIUM_BUCKET);
                offerBucketRecipes(Items.GOLD_INGOT, AtItems.GOLD_BUCKET);
                offerBucketRecipes(AtItems.MITHRIL_INGOT, AtItems.MITHRIL_BUCKET);
                offerBucketRecipes(AtItems.SILVER_INGOT, AtItems.SILVER_BUCKET);
                offerBucketRecipes(AtItems.ANCIENT_METAL_INGOT, AtItems.ANCIENT_METAL_BUCKET);
                offerBucketRecipes(Items.COPPER_INGOT, AtItems.COPPER_BUCKET);
                offerBucketRecipes(Items.NETHERITE_INGOT, AtItems.NETHERITE_BUCKET);
                createShapeless(RecipeCategory.MISC, AtItems.SINEW).input(Items.LEATHER).criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER)).offerTo(this.exporter);
            }

            private void offerBucketRecipes(Item ingot, Item bucket) {
                createShaped(RecipeCategory.MISC, bucket)
                        .input('a', ingot)
                        .pattern("a a")
                        .pattern(" a ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerChainsRecipes(Item nugget, Item chains) {
                createShaped(RecipeCategory.MISC, chains)
                        .input('a', nugget)
                        .pattern(" a ")
                        .pattern("a a")
                        .pattern(" a ")
                        .criterion(hasItem(nugget), this.conditionsFromItem(nugget))
                        .offerTo(this.exporter);
            }

            private void offerArrowRecipes(Item nugget, Item arrow) {
                createShaped(RecipeCategory.COMBAT, arrow)
                        .input('a', nugget)
                        .input('b', Items.STICK)
                        .input('c', Items.FEATHER)
                        .pattern("a")
                        .pattern("b")
                        .pattern("c")
                        .criterion(hasItem(nugget), this.conditionsFromItem(nugget))
                        .offerTo(this.exporter);
            }

            private void offerCraftingTableRecipes(Block craftingTable, Item ingot){
                createShaped(RecipeCategory.DECORATIONS, craftingTable)
                        .input('a', ingot)
                        .input('b', Items.LEATHER)
                        .input('c', Items.STICK)
                        .input('d', ItemTags.PLANKS)
                        .pattern("ab")
                        .pattern("cd")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerAnvilRecipes(Block anvil, Item ingot, Block block) {
                createShaped(RecipeCategory.DECORATIONS, anvil)
                        .input('a', ingot)
                        .input('b', block)
                        .pattern("bbb")
                        .pattern(" a ")
                        .pattern("aaa")
                        .criterion(hasItem(block), this.conditionsFromItem(block))
                        .offerTo(this.exporter);
            }

            private void offerBarsRecipes(Block bars, Item ingot) {
                createShaped(RecipeCategory.DECORATIONS, bars)
                        .input('a', ingot)
                        .pattern("aaa")
                        .pattern("aaa")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerDoorRecipes(Block door, Item ingot) {
                createShaped(RecipeCategory.REDSTONE, door)
                        .input('a', ingot)
                        .pattern("aa")
                        .pattern("aa")
                        .pattern("aa");
            }
            private void offerFishingRodRecipes(Item rod, Item nugget) {
                createShaped(RecipeCategory.TOOLS, rod)
                        .input('a', nugget)
                        .input('b', Items.STRING)
                        .input('c', Items.STICK)
                        .pattern("  c")
                        .pattern(" cb")
                        .pattern("cab")
                        .criterion(hasItem(nugget), this.conditionsFromItem(nugget))
                        .offerTo(this.exporter);

            }
            private void offerArmorRecipes(Item ingot, Item helmet, Item chestplate, Item leggings, Item boots) {
                offerHelmetRecipes(ingot,  helmet);
                offerChestplateRecipes(ingot,  chestplate);
                offerLeggingsRecipes(ingot,  leggings);
                offerBootsRecipes(ingot,  boots);
            }
            private void offerHelmetRecipes(Item ingot, Item helmet) {
                createShaped(RecipeCategory.COMBAT, helmet)
                        .input('a', ingot)
                        .pattern("aaa")
                        .pattern("a a")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerChestplateRecipes(Item ingot, Item chestplate) {
                createShaped(RecipeCategory.COMBAT, chestplate)
                        .input('a', ingot)
                        .pattern("a a")
                        .pattern("aaa")
                        .pattern("aaa")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerLeggingsRecipes(Item ingot, Item leggings) {
                createShaped(RecipeCategory.COMBAT, leggings)
                        .input('a', ingot)
                        .pattern("aaa")
                        .pattern("a a")
                        .pattern("a a")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerBootsRecipes(Item ingot, Item boots) {
                createShaped(RecipeCategory.COMBAT, boots)
                        .input('a', ingot)
                        .pattern("a a")
                        .pattern("a a")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerToolRecipes(Item ingot,
                                          Item sword,
                                          Item dagger,
                                          Item pickaxe,
                                          Item warhammer,
                                          Item axe,
                                          Item hatchet,
                                          Item battleaxe,
                                          Item shovel,
                                          Item hoe,
                                          Item mattock,
                                          Item scythe,
                                          Item shears) {
                offerSwordRecipes(ingot, sword, dagger);
                offerPickaxeRecipes(ingot, pickaxe, warhammer);
                offerAxeRecipes(ingot, axe, hatchet, battleaxe);
                offerShovelRecipes(ingot, shovel);
                offerHoeRecipes(ingot, hoe, mattock);
                offerScytheRecipes(ingot, scythe);
                offerShearsRecipes(ingot, shears);
            }

            private void offerSwordRecipes(Item ingot, Item sword, Item dagger) {
                offerSwordRecipes(ingot, sword);
                offerDaggerRecipes(ingot, dagger);
            }

            private void offerSwordRecipes(Item ingot, Item sword) {
                createShaped(RecipeCategory.COMBAT, sword)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("a")
                        .pattern("a")
                        .pattern("b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerDaggerRecipes(Item ingot, Item dagger) {
                createShaped(RecipeCategory.COMBAT, dagger)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerDaggerRecipesIsString(Item ingot, Item dagger) {
                createShaped(RecipeCategory.COMBAT, dagger)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .input('c', AtTags.STRING)
                        .pattern(" a")
                        .pattern("cb")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerPickaxeRecipes(Item ingot, Item pickaxe, Item warhammer) {
                offerPickaxeRecipes(ingot, pickaxe);
                offerWarhammerRecipes(ingot, warhammer);
            }

            private void offerPickaxeRecipes(Item ingot, Item pickaxe) {
                createShaped(RecipeCategory.TOOLS, pickaxe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("aaa")
                        .pattern(" b ")
                        .pattern(" b ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerWarhammerRecipes(Item ingot, Item warhammer) {
                createShaped(RecipeCategory.TOOLS, warhammer)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("aaa")
                        .pattern("aba")
                        .pattern(" b ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerAxeRecipes(Item ingot, Item axe, Item hatchet, Item battleaxe) {
                offerAxeRecipes(ingot, axe);
                offerHatchetRecipes(ingot, hatchet);
                offerBattleaxeRecipes(ingot, battleaxe);
            }

            private void offerAxeRecipes(Item ingot, Item axe) {
                createShaped(RecipeCategory.TOOLS, axe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("aa")
                        .pattern("ab")
                        .pattern(" b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerAxeRecipesInString(Item ingot, Item axe) {
                createShaped(RecipeCategory.TOOLS, axe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .input('c', AtTags.STRING)
                        .pattern("aa")
                        .pattern("ab")
                        .pattern("cb")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerHatchetRecipes(Item ingot, Item hatchet) {
                createShaped(RecipeCategory.TOOLS, hatchet)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("ab")
                        .pattern(" b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerHatchetRecipesInString(Item ingot, Item hatchet) {
                createShaped(RecipeCategory.TOOLS, hatchet)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .input('c', AtTags.STRING)
                        .pattern("ab")
                        .pattern("cb")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerBattleaxeRecipes(Item ingot, Item battleaxe) {
                createShaped(RecipeCategory.COMBAT, battleaxe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("a a")
                        .pattern("aba")
                        .pattern(" b ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerShovelRecipes(Item ingot, Item shovel) {
                createShaped(RecipeCategory.TOOLS, shovel)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("a")
                        .pattern("b")
                        .pattern("b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
            private void offerShovelRecipesIsString(Item ingot, Item shovel) {
                createShaped(RecipeCategory.TOOLS, shovel)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .input('c', AtTags.STRING)
                        .pattern(" a")
                        .pattern("cb")
                        .pattern(" b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerHoeRecipes(Item ingot, Item hoe, Item mattock) {
                offerHoeRecipes(ingot, hoe);
                offerMattockRecipes(ingot, mattock);
            }

            private void offerHoeRecipes(Item ingot, Item hoe) {
                createShaped(RecipeCategory.TOOLS, hoe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("aa")
                        .pattern(" b")
                        .pattern(" b")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerMattockRecipes(Item ingot, Item mattock) {
                createShaped(RecipeCategory.TOOLS, mattock)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("aaa")
                        .pattern("ab ")
                        .pattern(" b ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerScytheRecipes(Item ingot, Item scythe) {
                createShaped(RecipeCategory.TOOLS, scythe)
                        .input('a', ingot)
                        .input('b', Items.STICK)
                        .pattern("ba ")
                        .pattern("b a")
                        .pattern("b  ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }

            private void offerShearsRecipes(Item ingot, Item shears) {
                createShaped(RecipeCategory.TOOLS, shears)
                        .input('a', ingot)
                        .pattern(" a")
                        .pattern("a ")
                        .criterion(hasItem(ingot), this.conditionsFromItem(ingot))
                        .offerTo(this.exporter);
            }
        };
    }



    @Override
    public String getName() {
        return Mme.MOD_ID;
    }
}
